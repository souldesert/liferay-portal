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

package com.liferay.commerce.product.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPInstanceService}.
 *
 * @author Marco Leo
 * @see CPInstanceService
 * @generated
 */
public class CPInstanceServiceWrapper
	implements CPInstanceService, ServiceWrapper<CPInstanceService> {

	public CPInstanceServiceWrapper(CPInstanceService cpInstanceService) {
		_cpInstanceService = cpInstanceService;
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance addCPInstance(
			long cpDefinitionId, long groupId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable,
			java.util.Map<Long, java.util.List<Long>>
				cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.addCPInstance(
			cpDefinitionId, groupId, sku, gtin, manufacturerPartNumber,
			purchasable, cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds,
			published, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance addCPInstance(
			long cpDefinitionId, long groupId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable,
			java.util.Map<Long, java.util.List<Long>>
				cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, String unspsc,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.addCPInstance(
			cpDefinitionId, groupId, sku, gtin, manufacturerPartNumber,
			purchasable, cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds,
			published, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, unspsc, serviceContext);
	}

	/**
	 * @param cpDefinitionId
	 * @param groupId
	 * @param sku
	 * @param gtin
	 * @param manufacturerPartNumber
	 * @param purchasable
	 * @param json
	 * @param published
	 * @param displayDateMonth
	 * @param displayDateDay
	 * @param displayDateYear
	 * @param displayDateHour
	 * @param displayDateMinute
	 * @param expirationDateMonth
	 * @param expirationDateDay
	 * @param expirationDateYear
	 * @param expirationDateHour
	 * @param expirationDateMinute
	 * @param neverExpire
	 * @param serviceContext
	 * @return
	 * @throws PortalException
	 * @deprecated As of Athanasius (7.3.x), use {@link #addCPInstance(long,
	 long, String, String, String, boolean, Map, boolean, int,
	 int, int, int, int, int, int, int, int, int, boolean,
	 ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.product.model.CPInstance addCPInstance(
			long cpDefinitionId, long groupId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable, String json,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.addCPInstance(
			cpDefinitionId, groupId, sku, gtin, manufacturerPartNumber,
			purchasable, json, published, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	/**
	 * @param cpDefinitionId
	 * @param groupId
	 * @param sku
	 * @param gtin
	 * @param manufacturerPartNumber
	 * @param purchasable
	 * @param json
	 * @param published
	 * @param displayDateMonth
	 * @param displayDateDay
	 * @param displayDateYear
	 * @param displayDateHour
	 * @param displayDateMinute
	 * @param expirationDateMonth
	 * @param expirationDateDay
	 * @param expirationDateYear
	 * @param expirationDateHour
	 * @param expirationDateMinute
	 * @param neverExpire
	 * @param unspsc
	 * @param serviceContext
	 * @return
	 * @throws PortalException
	 * @deprecated As of Athanasius (7.3.x), use {@link #addCPInstance(long,
	 long, String, String, String, boolean, Map, boolean, int,
	 int, int, int, int, int, int, int, int, int, boolean,
	 String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.product.model.CPInstance addCPInstance(
			long cpDefinitionId, long groupId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable, String json,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, String unspsc,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.addCPInstance(
			cpDefinitionId, groupId, sku, gtin, manufacturerPartNumber,
			purchasable, json, published, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, unspsc,
			serviceContext);
	}

	@Override
	public void buildCPInstances(
			long cpDefinitionId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cpInstanceService.buildCPInstances(cpDefinitionId, serviceContext);
	}

	@Override
	public void deleteCPInstance(long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cpInstanceService.deleteCPInstance(cpInstanceId);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance fetchCPInstance(
			long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.fetchCPInstance(cpInstanceId);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance fetchCProductInstance(
			long cProductId, String cpInstanceUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.fetchCProductInstance(
			cProductId, cpInstanceUuid);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPInstance>
			getCPDefinitionInstances(
				long cpDefinitionId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.product.model.CPInstance>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.getCPDefinitionInstances(
			cpDefinitionId, status, start, end, orderByComparator);
	}

	@Override
	public int getCPDefinitionInstancesCount(long cpDefinitionId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.getCPDefinitionInstancesCount(
			cpDefinitionId, status);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance getCPInstance(
			long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.getCPInstance(cpInstanceId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPInstance>
			getCPInstances(
				long groupId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.product.model.CPInstance>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.getCPInstances(
			groupId, status, start, end, orderByComparator);
	}

	@Override
	public int getCPInstancesCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.getCPInstancesCount(groupId, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpInstanceService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.product.model.CPInstance>
				searchCPDefinitionInstances(
					long companyId, long cpDefinitionId, String keywords,
					int status, int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.searchCPDefinitionInstances(
			companyId, cpDefinitionId, keywords, status, start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.product.model.CPInstance>
				searchCPDefinitionInstances(
					long companyId, long cpDefinitionId, String keywords,
					int status, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.searchCPDefinitionInstances(
			companyId, cpDefinitionId, keywords, status, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.product.model.CPInstance> searchCPInstances(
				long companyId, long groupId, String keywords, int status,
				int start, int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.searchCPInstances(
			companyId, groupId, keywords, status, start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.product.model.CPInstance> searchCPInstances(
				long companyId, String keywords, int status, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.searchCPInstances(
			companyId, keywords, status, start, end, sort);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance updateCPInstance(
			long cpInstanceId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.updateCPInstance(
			cpInstanceId, sku, gtin, manufacturerPartNumber, purchasable,
			published, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance updateCPInstance(
			long cpInstanceId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, String unspsc,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.updateCPInstance(
			cpInstanceId, sku, gtin, manufacturerPartNumber, purchasable,
			published, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, unspsc, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance updatePricingInfo(
			long cpInstanceId, java.math.BigDecimal price,
			java.math.BigDecimal promoPrice, java.math.BigDecimal cost,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.updatePricingInfo(
			cpInstanceId, price, promoPrice, cost, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance updateShippingInfo(
			long cpInstanceId, double width, double height, double depth,
			double weight,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.updateShippingInfo(
			cpInstanceId, width, height, depth, weight, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance updateSubscriptionInfo(
			long cpInstanceId, boolean overrideSubscriptionInfo,
			boolean subscriptionEnabled, int subscriptionLength,
			String subscriptionType,
			com.liferay.portal.kernel.util.UnicodeProperties
				subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles, boolean deliverySubscriptionEnabled,
			int deliverySubscriptionLength, String deliverySubscriptionType,
			com.liferay.portal.kernel.util.UnicodeProperties
				deliverySubscriptionTypeSettingsUnicodeProperties,
			long deliveryMaxSubscriptionCycles)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.updateSubscriptionInfo(
			cpInstanceId, overrideSubscriptionInfo, subscriptionEnabled,
			subscriptionLength, subscriptionType,
			subscriptionTypeSettingsUnicodeProperties, maxSubscriptionCycles,
			deliverySubscriptionEnabled, deliverySubscriptionLength,
			deliverySubscriptionType,
			deliverySubscriptionTypeSettingsUnicodeProperties,
			deliveryMaxSubscriptionCycles);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.product.model.CPInstance updateSubscriptionInfo(
			long cpInstanceId, boolean overrideSubscriptionInfo,
			boolean subscriptionEnabled, int subscriptionLength,
			String subscriptionType,
			com.liferay.portal.kernel.util.UnicodeProperties
				subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.updateSubscriptionInfo(
			cpInstanceId, overrideSubscriptionInfo, subscriptionEnabled,
			subscriptionLength, subscriptionType,
			subscriptionTypeSettingsUnicodeProperties, maxSubscriptionCycles,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance upsertCPInstance(
			long cpDefinitionId, long groupId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable, String json,
			double width, double height, double depth, double weight,
			java.math.BigDecimal price, java.math.BigDecimal promoPrice,
			java.math.BigDecimal cost, boolean published,
			String externalReferenceCode, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.upsertCPInstance(
			cpDefinitionId, groupId, sku, gtin, manufacturerPartNumber,
			purchasable, json, width, height, depth, weight, price, promoPrice,
			cost, published, externalReferenceCode, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance upsertCPInstance(
			long cpDefinitionId, long groupId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable, String json,
			double width, double height, double depth, double weight,
			java.math.BigDecimal price, java.math.BigDecimal promoPrice,
			java.math.BigDecimal cost, boolean published,
			String externalReferenceCode, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String unspsc,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceService.upsertCPInstance(
			cpDefinitionId, groupId, sku, gtin, manufacturerPartNumber,
			purchasable, json, width, height, depth, weight, price, promoPrice,
			cost, published, externalReferenceCode, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, unspsc,
			serviceContext);
	}

	@Override
	public CPInstanceService getWrappedService() {
		return _cpInstanceService;
	}

	@Override
	public void setWrappedService(CPInstanceService cpInstanceService) {
		_cpInstanceService = cpInstanceService;
	}

	private CPInstanceService _cpInstanceService;

}