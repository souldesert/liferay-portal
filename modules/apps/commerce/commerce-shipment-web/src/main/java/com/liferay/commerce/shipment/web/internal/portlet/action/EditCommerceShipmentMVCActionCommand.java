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

package com.liferay.commerce.shipment.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.exception.CommerceShipmentItemQuantityException;
import com.liferay.commerce.exception.CommerceShipmentShippingDateException;
import com.liferay.commerce.exception.CommerceShipmentStatusException;
import com.liferay.commerce.exception.NoSuchShipmentException;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.commerce.service.CommerceShipmentService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Calendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_SHIPMENT,
		"mvc.command.name=editCommerceShipment"
	},
	service = MVCActionCommand.class
)
public class EditCommerceShipmentMVCActionCommand extends BaseMVCActionCommand {

	protected CommerceShipment addCommerceShipment(ActionRequest actionRequest)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceShipment.class.getName(), actionRequest);

		long groupId = ParamUtil.getLong(
			actionRequest, "commerceChannelGroupId");
		long commerceAccountId = ParamUtil.getLong(
			actionRequest, "commerceAccountId");
		long commerceAddressId = ParamUtil.getLong(
			actionRequest, "commerceAddressId");
		long commerceShippingMethodId = ParamUtil.getLong(
			actionRequest, "commerceShippingMethodId");
		String commerceShippingOptionName = ParamUtil.getString(
			actionRequest, "commerceShippingOptionName");

		return _commerceShipmentService.addCommerceShipment(
			groupId, commerceAccountId, commerceAddressId,
			commerceShippingMethodId, commerceShippingOptionName,
			serviceContext);
	}

	protected void addCommerceShipmentItems(ActionRequest actionRequest)
		throws PortalException {

		long commerceShipmentId = ParamUtil.getLong(
			actionRequest, "commerceShipmentId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceShipmentItem.class.getName(), actionRequest);

		long[] commerceOrderItemIds = ParamUtil.getLongValues(
			actionRequest, "orderItemId");

		for (long commerceOrderItemId : commerceOrderItemIds) {
			_commerceShipmentItemService.addCommerceShipmentItem(
				commerceShipmentId, commerceOrderItemId, 0, 0, serviceContext);
		}
	}

	protected void deleteCommerceShipments(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceShipmentIds = null;

		long commerceShipmentId = ParamUtil.getLong(
			actionRequest, "commerceShipmentId");

		if (commerceShipmentId > 0) {
			deleteCommerceShipmentIds = new long[] {commerceShipmentId};
		}
		else {
			deleteCommerceShipmentIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCommerceShipmentIds"),
				0L);
		}

		boolean restoreStockQuantity = ParamUtil.getBoolean(
			actionRequest, "restoreStockQuantity");

		for (long deleteCommerceShipmentId : deleteCommerceShipmentIds) {
			_commerceShipmentService.deleteCommerceShipment(
				deleteCommerceShipmentId, restoreStockQuantity);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				addCommerceShipment(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceShipments(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateCommerceShipment(actionRequest);
			}
			else if (cmd.equals("address")) {
				updateAddress(actionRequest);
			}
			else if (cmd.equals("addShipmentItems")) {
				addCommerceShipmentItems(actionRequest);
			}
			else if (cmd.equals("carrierDetails")) {
				updateCarrierDetails(actionRequest);
			}
			else if (cmd.equals("expectedDate")) {
				updateExpectedDate(actionRequest);
			}
			else if (cmd.equals("shippingDate")) {
				updateShippingDate(actionRequest);
			}
			else if (cmd.equals("transition")) {
				updateStatus(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof CommerceShipmentItemQuantityException ||
				exception instanceof CommerceShipmentShippingDateException ||
				exception instanceof CommerceShipmentStatusException ||
				exception instanceof NoSuchShipmentException ||
				exception instanceof PrincipalException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				throw exception;
			}
		}
	}

	protected CommerceShipment updateAddress(ActionRequest actionRequest)
		throws PortalException {

		long commerceShipmentId = ParamUtil.getLong(
			actionRequest, "commerceShipmentId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String street1 = ParamUtil.getString(actionRequest, "street1");
		String street2 = ParamUtil.getString(actionRequest, "street2");
		String street3 = ParamUtil.getString(actionRequest, "street3");
		String city = ParamUtil.getString(actionRequest, "city");
		String zip = ParamUtil.getString(actionRequest, "zip");
		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");
		long commerceRegionId = ParamUtil.getLong(
			actionRequest, "commerceRegionId");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");

		return _commerceShipmentService.updateAddress(
			commerceShipmentId, name, description, street1, street2, street3,
			city, zip, commerceRegionId, commerceCountryId, phoneNumber);
	}

	protected CommerceShipment updateCarrierDetails(ActionRequest actionRequest)
		throws PortalException {

		long commerceShipmentId = ParamUtil.getLong(
			actionRequest, "commerceShipmentId");

		String carrier = ParamUtil.getString(actionRequest, "carrier");
		String trackingNumber = ParamUtil.getString(
			actionRequest, "trackingNumber");

		return _commerceShipmentService.updateCarrierDetails(
			commerceShipmentId, carrier, trackingNumber);
	}

	protected CommerceShipment updateCommerceShipment(
			ActionRequest actionRequest)
		throws Exception {

		long commerceShipmentId = ParamUtil.getLong(
			actionRequest, "commerceShipmentId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String street1 = ParamUtil.getString(actionRequest, "street1");
		String street2 = ParamUtil.getString(actionRequest, "street2");
		String street3 = ParamUtil.getString(actionRequest, "street3");
		String city = ParamUtil.getString(actionRequest, "city");
		String zip = ParamUtil.getString(actionRequest, "zip");
		long commerceRegionId = ParamUtil.getLong(
			actionRequest, "commerceRegionId");
		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");
		String carrier = ParamUtil.getString(actionRequest, "carrier");
		String trackingNumber = ParamUtil.getString(
			actionRequest, "trackingNumber");
		int status = ParamUtil.getInteger(actionRequest, "status");

		int shippingDateMonth = ParamUtil.getInteger(
			actionRequest, "shippingDateMonth");
		int shippingDateDay = ParamUtil.getInteger(
			actionRequest, "shippingDateDay");
		int shippingDateYear = ParamUtil.getInteger(
			actionRequest, "shippingDateYear");
		int shippingDateHour = ParamUtil.getInteger(
			actionRequest, "shippingDateHour");
		int shippingDateMinute = ParamUtil.getInteger(
			actionRequest, "shippingDateMinute");
		int shippingDateAmPm = ParamUtil.getInteger(
			actionRequest, "shippingDateAmPm");

		if (shippingDateAmPm == Calendar.PM) {
			shippingDateHour += 12;
		}

		int expectedDateMonth = ParamUtil.getInteger(
			actionRequest, "expectedDateMonth");
		int expectedDateDay = ParamUtil.getInteger(
			actionRequest, "expectedDateDay");
		int expectedDateYear = ParamUtil.getInteger(
			actionRequest, "expectedDateYear");
		int expectedDateHour = ParamUtil.getInteger(
			actionRequest, "expectedDateHour");
		int expectedDateMinute = ParamUtil.getInteger(
			actionRequest, "expectedDateMinute");
		int expectedDateAmPm = ParamUtil.getInteger(
			actionRequest, "expectedDateAmPm");

		if (expectedDateAmPm == Calendar.PM) {
			expectedDateHour += 12;
		}

		CommerceShipment commerceShipment = null;

		if (commerceShipmentId > 0) {
			commerceShipment = _commerceShipmentService.updateCommerceShipment(
				commerceShipmentId, name, description, street1, street2,
				street3, city, zip, commerceRegionId, commerceCountryId,
				phoneNumber, carrier, trackingNumber, status, shippingDateMonth,
				shippingDateDay, shippingDateYear, shippingDateHour,
				shippingDateMinute, expectedDateMonth, expectedDateDay,
				expectedDateYear, expectedDateHour, expectedDateMinute);
		}
		else {
			long commerceOrderId = ParamUtil.getLong(
				actionRequest, "commerceOrderId");

			if (commerceOrderId > 0) {
				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(
						CommerceShipment.class.getName(), actionRequest);

				commerceShipment = _commerceShipmentService.addCommerceShipment(
					commerceOrderId, serviceContext);
			}
		}

		return commerceShipment;
	}

	protected CommerceShipment updateExpectedDate(ActionRequest actionRequest)
		throws PortalException {

		long commerceShipmentId = ParamUtil.getLong(
			actionRequest, "commerceShipmentId");

		int expectedDateMonth = ParamUtil.getInteger(
			actionRequest, "expectedDateMonth");
		int expectedDateDay = ParamUtil.getInteger(
			actionRequest, "expectedDateDay");
		int expectedDateYear = ParamUtil.getInteger(
			actionRequest, "expectedDateYear");
		int expectedDateHour = ParamUtil.getInteger(
			actionRequest, "expectedDateHour");
		int expectedDateMinute = ParamUtil.getInteger(
			actionRequest, "expectedDateMinute");
		int expectedDateAmPm = ParamUtil.getInteger(
			actionRequest, "expectedDateAmPm");

		if (expectedDateAmPm == Calendar.PM) {
			expectedDateHour += 12;
		}

		return _commerceShipmentService.updateExpectedDate(
			commerceShipmentId, expectedDateMonth, expectedDateDay,
			expectedDateYear, expectedDateHour, expectedDateMinute);
	}

	protected CommerceShipment updateShippingDate(ActionRequest actionRequest)
		throws PortalException {

		long commerceShipmentId = ParamUtil.getLong(
			actionRequest, "commerceShipmentId");

		int shippingDateMonth = ParamUtil.getInteger(
			actionRequest, "shippingDateMonth");
		int shippingDateDay = ParamUtil.getInteger(
			actionRequest, "shippingDateDay");
		int shippingDateYear = ParamUtil.getInteger(
			actionRequest, "shippingDateYear");
		int shippingDateHour = ParamUtil.getInteger(
			actionRequest, "shippingDateHour");
		int shippingDateMinute = ParamUtil.getInteger(
			actionRequest, "shippingDateMinute");
		int shippingDateAmPm = ParamUtil.getInteger(
			actionRequest, "shippingDateAmPm");

		if (shippingDateAmPm == Calendar.PM) {
			shippingDateHour += 12;
		}

		return _commerceShipmentService.updateShippingDate(
			commerceShipmentId, shippingDateMonth, shippingDateDay,
			shippingDateYear, shippingDateHour, shippingDateMinute);
	}

	protected CommerceShipment updateStatus(ActionRequest actionRequest)
		throws PortalException {

		long commerceShipmentId = ParamUtil.getLong(
			actionRequest, "commerceShipmentId");

		int status = ParamUtil.getInteger(actionRequest, "transitionName");

		return _commerceShipmentService.updateStatus(
			commerceShipmentId, status);
	}

	@Reference
	private CommerceShipmentItemService _commerceShipmentItemService;

	@Reference
	private CommerceShipmentService _commerceShipmentService;

}