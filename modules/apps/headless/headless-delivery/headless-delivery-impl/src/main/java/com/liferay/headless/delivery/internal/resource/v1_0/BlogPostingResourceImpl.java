/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.headless.common.spi.resource.SPIRatingResource;
import com.liferay.headless.common.spi.service.context.ServiceContextRequestUtil;
import com.liferay.headless.delivery.dto.v1_0.BlogPosting;
import com.liferay.headless.delivery.dto.v1_0.Image;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.dto.v1_0.TaxonomyCategoryBrief;
import com.liferay.headless.delivery.internal.dto.v1_0.converter.BlogPostingDTOConverter;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.EntityFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.RatingUtil;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.BlogPostingEntityModel;
import com.liferay.headless.delivery.resource.v1_0.BlogPostingResource;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.LocalDateTimeUtil;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;

import java.io.Serializable;

import java.time.LocalDateTime;

import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/blog-posting.properties",
	scope = ServiceScope.PROTOTYPE, service = BlogPostingResource.class
)
public class BlogPostingResourceImpl
	extends BaseBlogPostingResourceImpl implements EntityModelResource {

	@Override
	public void deleteBlogPosting(Long blogPostingId) throws Exception {
		_blogsEntryService.deleteEntry(blogPostingId);
	}

	@Override
	public void deleteBlogPostingMyRating(Long blogPostingId) throws Exception {
		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		spiRatingResource.deleteRating(blogPostingId);
	}

	@Override
	public BlogPosting getBlogPosting(Long blogPostingId) throws Exception {
		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogPostingId);

		return _toBlogPosting(blogsEntry);
	}

	@Override
	public Rating getBlogPostingMyRating(Long blogPostingId) throws Exception {
		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		return spiRatingResource.getRating(blogPostingId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return new BlogPostingEntityModel(
			EntityFieldsUtil.getEntityFields(
				_portal.getClassNameId(BlogsEntry.class.getName()),
				contextCompany.getCompanyId(), _expandoColumnLocalService,
				_expandoTableLocalService));
	}

	@Override
	public Page<BlogPosting> getSiteBlogPostingsPage(
			Long siteId, String search, Aggregation aggregation, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			HashMapBuilder.put(
				"create",
				addAction(
					"ADD_ENTRY", "postSiteBlogPosting", "com.liferay.blogs",
					siteId)
			).put(
				"subscribe",
				addAction(
					"SUBSCRIBE", "putSiteBlogPostingSubscribe",
					"com.liferay.blogs", siteId)
			).put(
				"unsubscribe",
				addAction(
					"SUBSCRIBE", "putSiteBlogPostingUnsubscribe",
					"com.liferay.blogs", siteId)
			).build(),
			booleanQuery -> {
			},
			filter, BlogsEntry.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setAttribute(
					Field.STATUS, WorkflowConstants.STATUS_APPROVED);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {siteId});
			},
			sorts,
			document -> _toBlogPosting(
				_blogsEntryService.getEntry(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public Rating postBlogPostingMyRating(Long blogPostingId, Rating rating)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		return spiRatingResource.addOrUpdateRating(
			rating.getRatingValue(), blogPostingId);
	}

	@Override
	public BlogPosting postSiteBlogPosting(Long siteId, BlogPosting blogPosting)
		throws Exception {

		LocalDateTime localDateTime = LocalDateTimeUtil.toLocalDateTime(
			blogPosting.getDatePublished());
		Image image = blogPosting.getImage();

		return _toBlogPosting(
			_blogsEntryService.addEntry(
				blogPosting.getHeadline(), blogPosting.getAlternativeHeadline(),
				blogPosting.getFriendlyUrlPath(), blogPosting.getDescription(),
				blogPosting.getArticleBody(), localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), true, true,
				new String[0], _getCaption(image), _getImageSelector(image),
				null,
				ServiceContextRequestUtil.createServiceContext(
					blogPosting.getTaxonomyCategoryIds(),
					blogPosting.getKeywords(),
					_getExpandoBridgeAttributes(blogPosting), siteId,
					contextHttpServletRequest,
					blogPosting.getViewableByAsString())));
	}

	@Override
	public BlogPosting putBlogPosting(
			Long blogPostingId, BlogPosting blogPosting)
		throws Exception {

		LocalDateTime localDateTime = LocalDateTimeUtil.toLocalDateTime(
			blogPosting.getDatePublished());
		Image image = blogPosting.getImage();
		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogPostingId);

		return _toBlogPosting(
			_blogsEntryService.updateEntry(
				blogPostingId, blogPosting.getHeadline(),
				blogPosting.getAlternativeHeadline(),
				blogPosting.getFriendlyUrlPath(), blogPosting.getDescription(),
				blogPosting.getArticleBody(), localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), true, true,
				new String[0], _getCaption(image), _getImageSelector(image),
				null,
				ServiceContextRequestUtil.createServiceContext(
					blogPosting.getTaxonomyCategoryIds(),
					blogPosting.getKeywords(),
					_getExpandoBridgeAttributes(blogPosting),
					blogsEntry.getGroupId(), contextHttpServletRequest,
					blogPosting.getViewableByAsString())));
	}

	@Override
	public Rating putBlogPostingMyRating(Long blogPostingId, Rating rating)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		return spiRatingResource.addOrUpdateRating(
			rating.getRatingValue(), blogPostingId);
	}

	@Override
	public void putSiteBlogPostingSubscribe(Long siteId) throws Exception {
		_blogsEntryService.subscribe(siteId);
	}

	@Override
	public void putSiteBlogPostingUnsubscribe(Long siteId) throws Exception {
		_blogsEntryService.unsubscribe(siteId);
	}

	@Override
	protected void preparePatch(
		BlogPosting blogPosting, BlogPosting existingBlogPosting) {

		Image image = blogPosting.getImage();

		if (image != null) {
			existingBlogPosting.setImage(
				new Image() {
					{
						caption = image.getCaption();
						imageId = image.getImageId();
					}
				});
		}

		TaxonomyCategoryBrief[] taxonomyCategoryBriefs =
			blogPosting.getTaxonomyCategoryBriefs();

		if (taxonomyCategoryBriefs != null) {
			blogPosting.setTaxonomyCategoryIds(
				transform(
					taxonomyCategoryBriefs,
					TaxonomyCategoryBrief::getTaxonomyCategoryId,
					Long[].class));
		}
	}

	private String _getCaption(Image image) {
		if (image == null) {
			return null;
		}

		return image.getCaption();
	}

	private Map<String, Serializable> _getExpandoBridgeAttributes(
		BlogPosting blogPosting) {

		return CustomFieldsUtil.toMap(
			BlogsEntry.class.getName(), contextCompany.getCompanyId(),
			blogPosting.getCustomFields(),
			contextAcceptLanguage.getPreferredLocale());
	}

	private ImageSelector _getImageSelector(Image image) {
		if ((image == null) || (image.getImageId() == 0)) {
			return new ImageSelector();
		}

		try {
			FileEntry fileEntry = _dlAppService.getFileEntry(
				image.getImageId());

			return new ImageSelector(
				FileUtil.getBytes(fileEntry.getContentStream()),
				fileEntry.getFileName(), fileEntry.getMimeType(),
				"{\"height\": 0, \"width\": 0, \"x\": 0, \"y\": 0}");
		}
		catch (Exception exception) {
			throw new RuntimeException(
				"Unable to get file entry " + image.getImageId(), exception);
		}
	}

	private SPIRatingResource<Rating> _getSPIRatingResource() {
		return new SPIRatingResource<>(
			BlogsEntry.class.getName(), _ratingsEntryLocalService,
			ratingsEntry -> {
				BlogsEntry blogsEntry = _blogsEntryService.getEntry(
					ratingsEntry.getClassPK());

				return RatingUtil.toRating(
					HashMapBuilder.put(
						"create",
						addAction("VIEW", blogsEntry, "postBlogPostingMyRating")
					).put(
						"delete",
						addAction(
							"VIEW", blogsEntry, "deleteBlogPostingMyRating")
					).put(
						"get",
						addAction("VIEW", blogsEntry, "getBlogPostingMyRating")
					).put(
						"replace",
						addAction("VIEW", blogsEntry, "putBlogPostingMyRating")
					).build(),
					_portal, ratingsEntry, _userLocalService);
			},
			contextUser);
	}

	private BlogPosting _toBlogPosting(BlogsEntry blogsEntry) throws Exception {
		return _blogPostingDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				HashMapBuilder.put(
					"delete",
					addAction("DELETE", blogsEntry, "deleteBlogPosting")
				).put(
					"get", addAction("VIEW", blogsEntry, "getBlogPosting")
				).put(
					"replace", addAction("UPDATE", blogsEntry, "putBlogPosting")
				).put(
					"update",
					addAction("UPDATE", blogsEntry, "patchBlogPosting")
				).build(),
				_dtoConverterRegistry, blogsEntry.getEntryId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private BlogPostingDTOConverter _blogPostingDTOConverter;

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsEntryLocalService _ratingsEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}