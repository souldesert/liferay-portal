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

package com.liferay.layout.seo.web.internal.portlet.action;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.seo.service.LayoutSEOEntryService;
import com.liferay.layout.seo.web.internal.util.LayoutTypeSettingsUtil;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout/edit_seo"
	},
	service = MVCActionCommand.class
)
public class EditSEOMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		long layoutId = ParamUtil.getLong(actionRequest, "layoutId");

		Layout layout = _layoutLocalService.getLayout(
			groupId, privateLayout, layoutId);

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		Map<Locale, String> keywordsMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "keywords");
		Map<Locale, String> robotsMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "robots");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Layout.class.getName(), actionRequest);

		if (layout.isTypeAssetDisplay()) {
			serviceContext.setAttribute(
				"layout.instanceable.allowed", Boolean.TRUE);
		}

		layout = _layoutService.updateLayout(
			groupId, privateLayout, layoutId, layout.getParentLayoutId(),
			layout.getNameMap(), titleMap, descriptionMap, keywordsMap,
			robotsMap, layout.getType(), layout.isHidden(),
			layout.getFriendlyURLMap(), layout.isIconImage(), null, 0, 0,
			serviceContext);

		boolean canonicalURLEnabled = ParamUtil.getBoolean(
			actionRequest, "canonicalURLEnabled");
		Map<Locale, String> canonicalURLMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "canonicalURL");

		_layoutSEOEntryService.updateLayoutSEOEntry(
			groupId, privateLayout, layoutId, canonicalURLEnabled,
			canonicalURLMap, serviceContext);

		UnicodeProperties formTypeSettingsUnicodeProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		Layout draftLayout = layout.fetchDraftLayout();

		if (draftLayout != null) {
			draftLayout = _layoutService.updateLayout(
				groupId, privateLayout, draftLayout.getLayoutId(),
				draftLayout.getParentLayoutId(), draftLayout.getNameMap(),
				titleMap, descriptionMap, keywordsMap, robotsMap,
				draftLayout.getType(), draftLayout.isHidden(),
				draftLayout.getFriendlyURLMap(), draftLayout.isIconImage(),
				null, 0, 0, serviceContext);

			draftLayout = LayoutTypeSettingsUtil.updateTypeSettings(
				draftLayout, _layoutService, formTypeSettingsUnicodeProperties);

			_layoutSEOEntryService.updateLayoutSEOEntry(
				groupId, privateLayout, draftLayout.getLayoutId(),
				canonicalURLEnabled, canonicalURLMap, serviceContext);
		}

		themeDisplay.clearLayoutFriendlyURL(layout);

		layout = LayoutTypeSettingsUtil.updateTypeSettings(
			layout, _layoutService, formTypeSettingsUnicodeProperties);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		EventsProcessorUtil.process(
			PropsKeys.LAYOUT_CONFIGURATION_ACTION_UPDATE,
			layoutTypePortlet.getConfigurationActionUpdate(),
			uploadPortletRequest,
			_portal.getHttpServletResponse(actionResponse));

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNull(redirect)) {
			redirect = _portal.getLayoutFullURL(layout, themeDisplay);
		}

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		MultiSessionMessages.add(
			actionRequest, portletResource + "layoutUpdated", layout);

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSEOEntryService _layoutSEOEntryService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

}