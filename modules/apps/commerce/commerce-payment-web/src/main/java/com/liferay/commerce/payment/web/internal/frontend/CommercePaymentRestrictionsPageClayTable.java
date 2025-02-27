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

package com.liferay.commerce.payment.web.internal.frontend;

import com.liferay.commerce.frontend.model.RestrictionField;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelService;
import com.liferay.commerce.payment.web.internal.model.PaymentRestriction;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceAddressRestrictionLocalService;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.frontend.taglib.clay.data.set.view.table.selectable.BaseSelectableTableClayDataSetDisplayView;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"clay.data.provider.key=" + CommercePaymentRestrictionsPageClayTable.NAME,
		"clay.data.set.display.name=" + CommercePaymentRestrictionsPageClayTable.NAME
	},
	service = {ClayDataSetDataProvider.class, ClayDataSetDisplayView.class}
)
public class CommercePaymentRestrictionsPageClayTable
	extends BaseSelectableTableClayDataSetDisplayView
	implements ClayDataSetDataProvider<PaymentRestriction> {

	public static final String NAME = "payment-restrictions";

	@Override
	public String getFirstColumnLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "country");
	}

	@Override
	public String getFirstColumnName() {
		return "country";
	}

	@Override
	public List<PaymentRestriction> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<PaymentRestriction> paymentRestrictions = new ArrayList<>();

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		List<CommercePaymentMethodGroupRel> commercePaymentMethodGroupRels =
			_commercePaymentMethodGroupRelService.
				getCommercePaymentMethodGroupRels(
					commerceChannel.getGroupId(), true);

		BaseModelSearchResult<CommerceCountry>
			commerceCountryBaseModelSearchResult =
				_commerceCountryService.searchCommerceCountries(
					_portal.getCompanyId(httpServletRequest), true,
					filter.getKeywords(), pagination.getStartPosition(),
					pagination.getEndPosition(), sort);

		for (CommerceCountry commerceCountry :
				commerceCountryBaseModelSearchResult.getBaseModels()) {

			paymentRestrictions.add(
				new PaymentRestriction(
					commerceCountry.getCommerceCountryId(),
					commerceCountry.getName(themeDisplay.getLanguageId()),
					_getFields(
						commerceCountry.getCommerceCountryId(),
						commercePaymentMethodGroupRels,
						themeDisplay.getLanguageId())));
		}

		return paymentRestrictions;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		BaseModelSearchResult<CommerceCountry>
			commerceCountryBaseModelSearchResult =
				_commerceCountryService.searchCommerceCountries(
					_portal.getCompanyId(httpServletRequest), true,
					filter.getKeywords(), 0, 0, null);

		return commerceCountryBaseModelSearchResult.getLength();
	}

	private List<RestrictionField> _getFields(
		long commerceCountryId,
		List<CommercePaymentMethodGroupRel> commercePaymentMethodGroupRels,
		String languageId) {

		List<RestrictionField> restrictionFields = new ArrayList<>();

		for (CommercePaymentMethodGroupRel commercePaymentMethodGroupRel :
				commercePaymentMethodGroupRels) {

			restrictionFields.add(
				new RestrictionField(
					commercePaymentMethodGroupRel.getName(languageId),
					String.valueOf(
						commercePaymentMethodGroupRel.
							getCommercePaymentMethodGroupRelId()),
					_commerceAddressRestrictionLocalService.
						isCommerceAddressRestricted(
							CommercePaymentMethodGroupRel.class.getName(),
							commercePaymentMethodGroupRel.
								getCommercePaymentMethodGroupRelId(),
							commerceCountryId)));
		}

		return restrictionFields;
	}

	@Reference
	private CommerceAddressRestrictionLocalService
		_commerceAddressRestrictionLocalService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceCountryService _commerceCountryService;

	@Reference
	private CommercePaymentMethodGroupRelService
		_commercePaymentMethodGroupRelService;

	@Reference
	private Portal _portal;

}