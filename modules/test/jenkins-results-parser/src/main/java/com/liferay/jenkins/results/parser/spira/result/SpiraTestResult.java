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

package com.liferay.jenkins.results.parser.spira.result;

import com.liferay.jenkins.results.parser.spira.SpiraTestCaseObject;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseType;

/**
 * @author Michael Hashimoto
 */
public interface SpiraTestResult {

	public String getAxisName();

	public SpiraBuildResult getSpiraBuildResult();

	public SpiraTestCaseObject getSpiraTestCaseObject();

	public SpiraTestCaseType getSpiraTestCaseType();

	public String getTestName();

	public void record();

}