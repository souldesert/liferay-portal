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

package com.liferay.commerce.tax.engine.fixed.web.internal.display.context;

import com.liferay.commerce.constants.CommerceTaxScreenNavigationConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.percentage.PercentageFormatter;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPTaxCategoryService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.comparator.CPTaxCategoryCreateDateComparator;
import com.liferay.commerce.tax.engine.fixed.web.internal.display.context.util.CommerceTaxFixedRateRequestHelper;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.tax.service.CommerceTaxMethodService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.text.NumberFormat;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class BaseCommerceTaxFixedRateDisplayContext {

	public BaseCommerceTaxFixedRateDisplayContext(
		CommerceChannelLocalService commerceChannelLocalService,
		ModelResourcePermission<CommerceChannel>
			commerceChannelModelResourcePermission,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		CommerceTaxMethodService commerceTaxMethodService,
		CPTaxCategoryService cpTaxCategoryService,
		PercentageFormatter percentageFormatter, RenderRequest renderRequest) {

		this.commerceChannelLocalService = commerceChannelLocalService;
		this.commerceChannelModelResourcePermission =
			commerceChannelModelResourcePermission;
		this.commerceCurrencyLocalService = commerceCurrencyLocalService;
		this.commerceTaxMethodService = commerceTaxMethodService;
		this.cpTaxCategoryService = cpTaxCategoryService;
		this.percentageFormatter = percentageFormatter;

		commerceTaxFixedRateRequestHelper =
			new CommerceTaxFixedRateRequestHelper(renderRequest);
	}

	public List<CPTaxCategory> getAvailableCPTaxCategories()
		throws PortalException {

		return cpTaxCategoryService.getCPTaxCategories(
			commerceTaxFixedRateRequestHelper.getCompanyId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new CPTaxCategoryCreateDateComparator());
	}

	public long getCommerceChannelId() throws PortalException {
		CommerceTaxMethod commerceTaxMethod = getCommerceTaxMethod();

		if (commerceTaxMethod != null) {
			CommerceChannel commerceChannel =
				commerceChannelLocalService.getCommerceChannelByGroupId(
					commerceTaxMethod.getGroupId());

			return commerceChannel.getCommerceChannelId();
		}

		return ParamUtil.getLong(
			commerceTaxFixedRateRequestHelper.getRequest(),
			"commerceChannelId");
	}

	public String getCommerceCurrencyCode() throws PortalException {
		CommerceTaxMethod commerceTaxMethod = getCommerceTaxMethod();

		CommerceChannel commerceChannel =
			commerceChannelLocalService.getCommerceChannelByGroupId(
				commerceTaxMethod.getGroupId());

		return commerceChannel.getCommerceCurrencyCode();
	}

	public CommerceTaxMethod getCommerceTaxMethod() throws PortalException {
		if (_commerceTaxMethod != null) {
			return _commerceTaxMethod;
		}

		long commerceTaxMethodId = getCommerceTaxMethodId();

		if (commerceTaxMethodId > 0) {
			_commerceTaxMethod = commerceTaxMethodService.getCommerceTaxMethod(
				commerceTaxMethodId);
		}

		return _commerceTaxMethod;
	}

	public long getCommerceTaxMethodId() throws PortalException {
		return ParamUtil.getLong(
			commerceTaxFixedRateRequestHelper.getRequest(),
			"commerceTaxMethodId");
	}

	public String getLocalizedPercentage(double percentage, Locale locale)
		throws PortalException {

		CommerceChannel commerceChannel =
			commerceChannelLocalService.getCommerceChannel(
				getCommerceChannelId());

		CommerceCurrency commerceCurrency =
			commerceCurrencyLocalService.getCommerceCurrency(
				commerceChannel.getCompanyId(),
				commerceChannel.getCommerceCurrencyCode());

		String localizedPercentage = percentageFormatter.getLocalizedPercentage(
			locale, commerceCurrency.getMaxFractionDigits(),
			commerceCurrency.getMinFractionDigits(),
			new BigDecimal(percentage));

		return StringUtil.removeSubstring(
			localizedPercentage, StringPool.PERCENT);
	}

	public String getLocalizedRate(
			boolean percentage, double rate, Locale locale)
		throws PortalException {

		if (percentage) {
			return getLocalizedPercentage(rate, locale);
		}

		return getLocalizedRate(rate, locale);
	}

	public String getLocalizedRate(double rate, Locale locale)
		throws PortalException {

		NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);

		return numberFormat.format(rate);
	}

	public PortletURL getPortletURL() throws PortalException {
		LiferayPortletResponse liferayPortletResponse =
			commerceTaxFixedRateRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommerceTaxMethod");
		portletURL.setParameter(
			"screenNavigationCategoryKey",
			getSelectedScreenNavigationCategoryKey());

		String redirect = ParamUtil.getString(
			commerceTaxFixedRateRequestHelper.getRequest(), "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		CommerceTaxMethod commerceTaxMethod = getCommerceTaxMethod();

		if (commerceTaxMethod != null) {
			portletURL.setParameter(
				"commerceTaxMethodId",
				String.valueOf(commerceTaxMethod.getCommerceTaxMethodId()));
		}

		String engineKey = ParamUtil.getString(
			commerceTaxFixedRateRequestHelper.getRequest(), "engineKey");

		if (Validator.isNotNull(engineKey)) {
			portletURL.setParameter("engineKey", engineKey);
		}

		String delta = ParamUtil.getString(
			commerceTaxFixedRateRequestHelper.getRequest(), "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		return portletURL;
	}

	public String getScreenNavigationCategoryKey() {
		return CommerceTaxScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_TAX_METHOD_DETAIL;
	}

	public boolean hasUpdateCommerceChannelPermission() throws PortalException {
		CommerceChannel commerceChannel =
			commerceChannelLocalService.getCommerceChannel(
				getCommerceChannelId());

		return commerceChannelModelResourcePermission.contains(
			commerceTaxFixedRateRequestHelper.getPermissionChecker(),
			commerceChannel, ActionKeys.UPDATE);
	}

	protected String getSelectedScreenNavigationCategoryKey() {
		return ParamUtil.getString(
			commerceTaxFixedRateRequestHelper.getRequest(),
			"screenNavigationCategoryKey", getScreenNavigationCategoryKey());
	}

	protected final CommerceChannelLocalService commerceChannelLocalService;
	protected final ModelResourcePermission<CommerceChannel>
		commerceChannelModelResourcePermission;
	protected final CommerceCurrencyLocalService commerceCurrencyLocalService;
	protected final CommerceTaxFixedRateRequestHelper
		commerceTaxFixedRateRequestHelper;
	protected final CommerceTaxMethodService commerceTaxMethodService;
	protected final CPTaxCategoryService cpTaxCategoryService;
	protected final PercentageFormatter percentageFormatter;

	private CommerceTaxMethod _commerceTaxMethod;

}