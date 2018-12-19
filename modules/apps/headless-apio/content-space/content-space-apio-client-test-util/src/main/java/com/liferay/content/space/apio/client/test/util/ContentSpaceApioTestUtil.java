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

package com.liferay.content.space.apio.client.test.util;

import com.liferay.portal.apio.test.util.ApioClientBuilder;

/**
 * @author Cristina González
 */
public class ContentSpaceApioTestUtil {

	public static final String getContentSpaceHref(
		String url, String contentSpaceName) {

		return ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			url
		).follow(
			"_links.content-space.href"
		).then(
		).extract(
		).path(
			"_embedded.ContentSpace.find {it.name == '" + contentSpaceName +
				"'}._links.self.href"
		);
	}

}