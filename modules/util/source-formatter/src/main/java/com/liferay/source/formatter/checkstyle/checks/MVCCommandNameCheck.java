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

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.io.File;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class MVCCommandNameCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if ((detailAST.getParent() != null) ||
			AnnotationUtil.containsAnnotation(detailAST, "Deprecated")) {

			return;
		}

		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String className = nameDetailAST.getText();

		if (!className.endsWith("MVCActionCommand") &&
			!className.endsWith("MVCRenderCommand") &&
			!className.endsWith("MVCResourceCommand")) {

			return;
		}

		String absolutePath = getAbsolutePath();

		int pos = absolutePath.lastIndexOf("-web/");

		if (pos == -1) {
			return;
		}

		DetailAST annotationDetailAST = AnnotationUtil.getAnnotation(
			detailAST, "Component");

		if (annotationDetailAST == null) {
			return;
		}

		DetailAST propertyAnnotationMemberValuePairDetailAST =
			_getAnnotationMemberValuePairDetailAST(
				annotationDetailAST, "property");

		if (propertyAnnotationMemberValuePairDetailAST == null) {
			return;
		}

		DetailAST annotationArrayInitDetailAST =
			propertyAnnotationMemberValuePairDetailAST.findFirstToken(
				TokenTypes.ANNOTATION_ARRAY_INIT);

		if (annotationArrayInitDetailAST == null) {
			return;
		}

		Map<String, Integer> mvcCommandNamesMap = _getMVCCommandNamesMap(
			annotationArrayInitDetailAST);

		if (mvcCommandNamesMap.isEmpty()) {
			return;
		}

		String modulePath = absolutePath.substring(0, pos);

		boolean validateActionName = false;

		if (mvcCommandNamesMap.size() == 1) {
			validateActionName = true;
		}

		for (Map.Entry<String, Integer> entry : mvcCommandNamesMap.entrySet()) {
			String mvcCommandName = entry.getKey();

			if (!_hasValidMVCCommandName(
					mvcCommandName, detailAST, annotationArrayInitDetailAST,
					className, modulePath, validateActionName)) {

				log(entry.getValue(), _MSG_INCORRECT_VALUE, mvcCommandName);
			}
		}
	}

	private DetailAST _getAnnotationMemberValuePairDetailAST(
		DetailAST annotationDetailAST, String key) {

		List<DetailAST> annotationMemberValuePairDetailASTList =
			getAllChildTokens(
				annotationDetailAST, false,
				TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

		for (DetailAST annotationMemberValuePairDetailAST :
				annotationMemberValuePairDetailASTList) {

			DetailAST firstChildDetailAST =
				annotationMemberValuePairDetailAST.getFirstChild();

			if ((firstChildDetailAST.getType() == TokenTypes.IDENT) &&
				Objects.equals(firstChildDetailAST.getText(), key)) {

				return annotationMemberValuePairDetailAST;
			}
		}

		return null;
	}

	private String _getExpectedActionName(String className) {
		String name = StringUtil.removeSubstrings(
			className, "MVCActionCommand", "MVCRenderCommand",
			"MVCResourceCommand");

		name = StringUtil.replace(name, "WeDeploy", "Wedeploy");

		Matcher matcher = _classNamePattern.matcher(className);

		while (matcher.find()) {
			String match = matcher.group(1);

			name = StringUtil.replaceFirst(
				name, match, StringUtil.lowerCase(match), matcher.start(1));
		}

		name = StringUtil.replace(
			TextFormatter.format(name, TextFormatter.K), CharPool.DASH,
			CharPool.UNDERLINE);

		return StringUtil.replace(name, "mfafido2", "mfa_fido2");
	}

	private Map<String, Integer> _getMVCCommandNamesMap(
		DetailAST annotationArrayInitDetailAST) {

		Map<String, Integer> mvcCommandNamesMap = new HashMap<>();

		List<DetailAST> expressionDetailASTList = getAllChildTokens(
			annotationArrayInitDetailAST, false, TokenTypes.EXPR);

		for (DetailAST expressionDetailAST : expressionDetailASTList) {
			DetailAST firstChildDetailAST = expressionDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.STRING_LITERAL) {
				continue;
			}

			String value = firstChildDetailAST.getText();

			if (value.startsWith("\"mvc.command.name=") &&
				!value.equals("\"mvc.command.name=/\"")) {

				mvcCommandNamesMap.put(
					value.substring(18, value.length() - 1),
					expressionDetailAST.getLineNo());
			}
		}

		return mvcCommandNamesMap;
	}

	private String _getTrimmedPath(
		String[] pathArray, int abbreviatePathCount) {

		StringBundler sb = new StringBundler(pathArray.length);

		for (int i = 0; i < pathArray.length; i++) {
			String s = pathArray[i];

			if (i < abbreviatePathCount) {
				sb.append(s.charAt(0));
			}
			else {
				sb.append(s);
			}
		}

		return sb.toString();
	}

	private boolean _hasValidActionName(
		String actionName, String path, String className) {

		if (actionName.equals(_getExpectedActionName(className))) {
			return true;
		}

		String[] pathArray = StringUtil.split(path, CharPool.UNDERLINE);

		for (int i = 0; i < pathArray.length; i++) {
			String trimmedPath = _getTrimmedPath(pathArray, i);

			if (StringUtil.startsWith(className, trimmedPath)) {
				String expectedActionName = _getExpectedActionName(
					className.substring(trimmedPath.length()));

				if (actionName.equals(expectedActionName)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean _hasValidMVCCommandName(
		String mvcCommandName, DetailAST classDefinitionDetailAST,
		DetailAST annotationArrayInitDetailAST, String className,
		String modulePath, boolean validateActionName) {

		if (!mvcCommandName.startsWith(StringPool.SLASH)) {
			return false;
		}

		String[] parts = StringUtil.split(
			mvcCommandName.substring(1), StringPool.SLASH);

		if (parts.length != 2) {
			return false;
		}

		String actionName = parts[1];
		String path = parts[0];

		if ((!validateActionName ||
			 _hasValidActionName(actionName, path, className)) &&
			_hasValidPath(path, annotationArrayInitDetailAST, modulePath)) {

			return true;
		}

		List<DetailAST> annotationDetailASTList = getAllChildTokens(
			classDefinitionDetailAST, true, TokenTypes.ANNOTATION);

		for (DetailAST annotationDetailAST : annotationDetailASTList) {
			DetailAST identDetailAST = annotationDetailAST.findFirstToken(
				TokenTypes.IDENT);

			if (!Objects.equals(identDetailAST.getText(), "Reference")) {
				continue;
			}

			DetailAST targetAnnotationMemberValuePairDetailAST =
				_getAnnotationMemberValuePairDetailAST(
					annotationDetailAST, "target");

			if (targetAnnotationMemberValuePairDetailAST == null) {
				continue;
			}

			FullIdent fullIdent = FullIdent.createFullIdentBelow(
				targetAnnotationMemberValuePairDetailAST.getLastChild());

			Matcher matcher = _componentNamePattern.matcher(
				fullIdent.getText());

			if (matcher.find()) {
				String expectedPath = StringUtil.replace(
					matcher.group(1), CharPool.PERIOD, CharPool.UNDERLINE);
				String expectedActionName = _getExpectedActionName(
					matcher.group(2));

				if (path.equals(expectedPath) &&
					actionName.equals(expectedActionName)) {

					return true;
				}
			}
		}

		return false;
	}

	private boolean _hasValidPath(
		String path, DetailAST annotationArrayInitDetailAST,
		String modulePath) {

		String moduleName = modulePath.substring(
			modulePath.lastIndexOf(CharPool.SLASH) + 1);

		if (path.equals(StringUtil.replace(moduleName, '-', '_'))) {
			return true;
		}

		List<DetailAST> stringLiteralDetailASTList = getAllChildTokens(
			annotationArrayInitDetailAST, true, TokenTypes.STRING_LITERAL);

		for (DetailAST stringLiteralDetailAST : stringLiteralDetailASTList) {
			if (!Objects.equals(
					stringLiteralDetailAST.getText(),
					"\"javax.portlet.name=\"")) {

				continue;
			}

			List<DetailAST> identDetailASTList = getAllChildTokens(
				stringLiteralDetailAST.getParent(), true, TokenTypes.IDENT);

			for (DetailAST identDetailAST : identDetailASTList) {
				if (StringUtil.equalsIgnoreCase(
						path, identDetailAST.getText())) {

					return true;
				}
			}
		}

		File file = new File(
			modulePath + "-web/src/main/resources/META-INF/resources/" + path);

		return file.exists();
	}

	private static final String _MSG_INCORRECT_VALUE = "value.incorrect";

	private static final Pattern _classNamePattern = Pattern.compile(
		"[A-Z]([A-Z]+)s([A-Z]|\\Z)");
	private static final Pattern _componentNamePattern = Pattern.compile(
		"^\"\\(component\\.name=com\\.liferay\\.(.*)\\.web\\.internal" +
			"\\..*\\.(\\w+MVC\\w+Command)\\)\"$");

}