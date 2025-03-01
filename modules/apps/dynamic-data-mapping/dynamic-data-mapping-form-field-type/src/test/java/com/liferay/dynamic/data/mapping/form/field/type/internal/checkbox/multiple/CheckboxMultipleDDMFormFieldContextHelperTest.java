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

package com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox.multiple;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.test.util.DDMFormFieldOptionsTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rafael Praxedes
 */
public class CheckboxMultipleDDMFormFieldContextHelperTest {

	@Test
	public void testGetOptions() {
		List<Object> expectedOptions = new ArrayList<>();

		expectedOptions.add(
			DDMFormFieldOptionsTestUtil.createOption(
				"Label 1", "Reference 1", "value 1"));
		expectedOptions.add(
			DDMFormFieldOptionsTestUtil.createOption(
				"Label 2", "Reference 2", "value 2"));
		expectedOptions.add(
			DDMFormFieldOptionsTestUtil.createOption(
				"Label 3", "Reference 3", "value 3"));

		DDMFormFieldOptions ddmFormFieldOptions =
			DDMFormFieldOptionsTestUtil.createDDMFormFieldOptions();

		List<Object> actualOptions = getActualOptions(
			ddmFormFieldOptions, LocaleUtil.US);

		Assert.assertEquals(expectedOptions, actualOptions);
	}

	protected List<Object> getActualOptions(
		DDMFormFieldOptions ddmFormFieldOptions, Locale locale) {

		CheckboxMultipleDDMFormFieldContextHelper
			checkboxMultipleDDMFormFieldContextHelper =
				new CheckboxMultipleDDMFormFieldContextHelper(
					new JSONFactoryImpl(), ddmFormFieldOptions, locale);

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		return checkboxMultipleDDMFormFieldContextHelper.getOptions(
			ddmFormFieldRenderingContext);
	}

}