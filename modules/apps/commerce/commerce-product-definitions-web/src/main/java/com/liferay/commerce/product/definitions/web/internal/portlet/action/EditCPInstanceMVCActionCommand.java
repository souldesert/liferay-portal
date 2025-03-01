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

package com.liferay.commerce.product.definitions.web.internal.portlet.action;

import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.pricing.configuration.CommercePricingConfiguration;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.pricing.exception.CommerceUndefinedBasePriceListException;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.CPDefinitionIgnoreSKUCombinationsException;
import com.liferay.commerce.product.exception.CPInstanceJsonException;
import com.liferay.commerce.product.exception.CPInstanceSkuException;
import com.liferay.commerce.product.exception.NoSuchSkuContributorCPDefinitionOptionRelException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=editProductInstance"
	},
	service = MVCActionCommand.class
)
public class EditCPInstanceMVCActionCommand extends BaseMVCActionCommand {

	protected void buildCPInstances(ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPInstance.class.getName(), actionRequest);

		_cpInstanceService.buildCPInstances(cpDefinitionId, serviceContext);
	}

	protected void deleteCPInstances(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCPInstanceIds = null;

		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");

		if (cpInstanceId > 0) {
			deleteCPInstanceIds = new long[] {cpInstanceId};
		}
		else {
			deleteCPInstanceIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCPInstanceIds"), 0L);
		}

		for (long deleteCPInstanceId : deleteCPInstanceIds) {
			_cpInstanceService.deleteCPInstance(deleteCPInstanceId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				Callable<CPInstance> cpInstanceCallable =
					new CPInstanceCallable(actionRequest);

				CPInstance cpInstance = TransactionInvokerUtil.invoke(
					_transactionConfig, cpInstanceCallable);

				String redirect = getSaveAndContinueRedirect(
					actionRequest, cpInstance);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.ADD_MULTIPLE)) {
				buildCPInstances(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCPInstances(actionRequest);
			}
			else if (cmd.equals("updateSubscriptionInfo")) {
				updateSubscriptionInfo(actionRequest);
			}
		}
		catch (Throwable throwable) {
			if (throwable instanceof CommerceUndefinedBasePriceListException ||
				throwable instanceof
					CPDefinitionIgnoreSKUCombinationsException ||
				throwable instanceof CPInstanceJsonException ||
				throwable instanceof CPInstanceSkuException ||
				throwable instanceof
					NoSuchSkuContributorCPDefinitionOptionRelException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, throwable.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "editProductInstance");
			}
			else {
				throw new PortletException(throwable);
			}
		}
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, CPInstance cpInstance)
		throws Exception {

		if (cpInstance == null) {
			return ParamUtil.getString(actionRequest, "redirect");
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			actionRequest, themeDisplay.getScopeGroup(),
			CPDefinition.class.getName(), PortletProvider.Action.EDIT);

		portletURL.setParameter("mvcRenderCommandName", "editProductInstance");
		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpInstance.getCPDefinitionId()));
		portletURL.setParameter(
			"cpInstanceId", String.valueOf(cpInstance.getCPInstanceId()));

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	protected CPInstance updateCPInstance(ActionRequest actionRequest)
		throws Exception {

		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		String sku = ParamUtil.getString(actionRequest, "sku");
		String gtin = ParamUtil.getString(actionRequest, "gtin");
		String manufacturerPartNumber = ParamUtil.getString(
			actionRequest, "manufacturerPartNumber");
		boolean purchasable = ParamUtil.getBoolean(
			actionRequest, "purchasable");
		boolean published = ParamUtil.getBoolean(actionRequest, "published");

		int displayDateMonth = ParamUtil.getInteger(
			actionRequest, "displayDateMonth");
		int displayDateDay = ParamUtil.getInteger(
			actionRequest, "displayDateDay");
		int displayDateYear = ParamUtil.getInteger(
			actionRequest, "displayDateYear");
		int displayDateHour = ParamUtil.getInteger(
			actionRequest, "displayDateHour");
		int displayDateMinute = ParamUtil.getInteger(
			actionRequest, "displayDateMinute");
		int displayDateAmPm = ParamUtil.getInteger(
			actionRequest, "displayDateAmPm");

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		int expirationDateMonth = ParamUtil.getInteger(
			actionRequest, "expirationDateMonth");
		int expirationDateDay = ParamUtil.getInteger(
			actionRequest, "expirationDateDay");
		int expirationDateYear = ParamUtil.getInteger(
			actionRequest, "expirationDateYear");
		int expirationDateHour = ParamUtil.getInteger(
			actionRequest, "expirationDateHour");
		int expirationDateMinute = ParamUtil.getInteger(
			actionRequest, "expirationDateMinute");
		int expirationDateAmPm = ParamUtil.getInteger(
			actionRequest, "expirationDateAmPm");

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		boolean neverExpire = ParamUtil.getBoolean(
			actionRequest, "neverExpire");

		String unspsc = ParamUtil.getString(actionRequest, "unspsc");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPInstance.class.getName(), actionRequest);

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		CPInstance cpInstance = null;

		if (cpInstanceId > 0) {
			cpInstance = _cpInstanceService.updateCPInstance(
				cpInstanceId, sku, gtin, manufacturerPartNumber, purchasable,
				published, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, unspsc, serviceContext);
		}
		else {
			String ddmFormValues = ParamUtil.getString(
				actionRequest, "ddmFormValues");

			cpInstance = _cpInstanceService.addCPInstance(
				cpDefinitionId, cpDefinition.getGroupId(), sku, gtin,
				manufacturerPartNumber, purchasable, ddmFormValues, published,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, unspsc, serviceContext);
		}

		// Update pricing info

		BigDecimal price = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "price", BigDecimal.ZERO);
		BigDecimal promoPrice = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "promoPrice", BigDecimal.ZERO);
		BigDecimal cost = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "cost", BigDecimal.ZERO);

		cpInstance = _cpInstanceService.updatePricingInfo(
			cpInstance.getCPInstanceId(), price, promoPrice, cost,
			serviceContext);

		String commercePricingConfigurationKey =
			_getCommercePricingConfigurationKey();

		if (Objects.equals(
				commercePricingConfigurationKey,
				CommercePricingConstants.VERSION_2_0)) {

			_updateCommercePriceEntry(
				cpInstance, CommercePriceListConstants.TYPE_PRICE_LIST, price,
				serviceContext);

			_updateCommercePriceEntry(
				cpInstance, CommercePriceListConstants.TYPE_PROMOTION,
				promoPrice, serviceContext);
		}

		// Update shipping info

		double width = ParamUtil.getDouble(actionRequest, "width");
		double height = ParamUtil.getDouble(actionRequest, "height");
		double depth = ParamUtil.getDouble(actionRequest, "depth");
		double weight = ParamUtil.getDouble(actionRequest, "weight");

		return _cpInstanceService.updateShippingInfo(
			cpInstance.getCPInstanceId(), width, height, depth, weight,
			serviceContext);
	}

	protected void updateSubscriptionInfo(ActionRequest actionRequest)
		throws PortalException {

		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");

		boolean overrideSubscriptionInfo = ParamUtil.getBoolean(
			actionRequest, "overrideSubscriptionInfo");
		boolean subscriptionEnabled = ParamUtil.getBoolean(
			actionRequest, "subscriptionEnabled");
		int subscriptionLength = ParamUtil.getInteger(
			actionRequest, "subscriptionLength");
		String subscriptionType = ParamUtil.getString(
			actionRequest, "subscriptionType");
		UnicodeProperties subscriptionTypeSettingsUnicodeProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "subscriptionTypeSettings--");
		long maxSubscriptionCycles = ParamUtil.getLong(
			actionRequest, "maxSubscriptionCycles");
		boolean deliverySubscriptionEnabled = ParamUtil.getBoolean(
			actionRequest, "deliverySubscriptionEnabled");
		int deliverySubscriptionLength = ParamUtil.getInteger(
			actionRequest, "deliverySubscriptionLength");
		String deliverySubscriptionType = ParamUtil.getString(
			actionRequest, "deliverySubscriptionType");
		UnicodeProperties deliverySubscriptionTypeSettingsUnicodeProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "deliverySubscriptionTypeSettings--");
		long deliveryMaxSubscriptionCycles = ParamUtil.getLong(
			actionRequest, "deliveryMaxSubscriptionCycles");

		_cpInstanceService.updateSubscriptionInfo(
			cpInstanceId, overrideSubscriptionInfo, subscriptionEnabled,
			subscriptionLength, subscriptionType,
			subscriptionTypeSettingsUnicodeProperties, maxSubscriptionCycles,
			deliverySubscriptionEnabled, deliverySubscriptionLength,
			deliverySubscriptionType,
			deliverySubscriptionTypeSettingsUnicodeProperties,
			deliveryMaxSubscriptionCycles);
	}

	private String _getCommercePricingConfigurationKey() throws Exception {
		CommercePricingConfiguration commercePricingConfiguration =
			_configurationProvider.getConfiguration(
				CommercePricingConfiguration.class,
				new SystemSettingsLocator(
					CommercePricingConstants.SERVICE_NAME));

		return commercePricingConfiguration.commercePricingCalculationKey();
	}

	private void _updateCommercePriceEntry(
			CPInstance cpInstance, String type, BigDecimal price,
			ServiceContext serviceContext)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.
				getCatalogBaseCommercePriceListByType(
					cpInstance.getGroupId(), type);

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceList.getCommercePriceListId(),
				cpInstance.getCPInstanceUuid());

		if (commercePriceEntry == null) {
			CPDefinition cpDefinition = cpInstance.getCPDefinition();

			_commercePriceEntryLocalService.addCommercePriceEntry(
				cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
				commercePriceList.getCommercePriceListId(), price, null,
				serviceContext);
		}
		else {
			_commercePriceEntryLocalService.updateCommercePriceEntry(
				commercePriceEntry.getCommercePriceEntryId(), price, null,
				serviceContext);
		}
	}

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPInstanceService _cpInstanceService;

	private class CPInstanceCallable implements Callable<CPInstance> {

		@Override
		public CPInstance call() throws Exception {
			return updateCPInstance(_actionRequest);
		}

		private CPInstanceCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}