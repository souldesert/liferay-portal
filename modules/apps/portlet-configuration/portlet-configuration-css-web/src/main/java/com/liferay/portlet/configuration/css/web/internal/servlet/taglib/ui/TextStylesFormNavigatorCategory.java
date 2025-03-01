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

package com.liferay.portlet.configuration.css.web.internal.servlet.taglib.ui;

import com.liferay.frontend.taglib.form.navigator.FormNavigatorCategory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portlet.configuration.css.web.internal.constants.PortletConfigurationCSSConstants;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "form.navigator.category.order:Integer=50",
	service = FormNavigatorCategory.class
)
public class TextStylesFormNavigatorCategory implements FormNavigatorCategory {

	@Override
	public String getFormNavigatorId() {
		return PortletConfigurationCSSConstants.FORM_NAVIGATOR_ID;
	}

	@Override
	public String getKey() {
		return PortletConfigurationCSSConstants.CATEGORY_KEY_TEXT_STYLES;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "text-styles");
	}

}