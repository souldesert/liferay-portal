<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
String portletResource = ParamUtil.getString(request, "portletResource");
%>

<c:if test="<%= Validator.isNotNull(portletResource) %>">
	<liferay-ui:success key='<%= portletResource + "requestProcessed" %>' message="your-request-completed-successfully" />
</c:if>

<c:choose>
	<c:when test="<%= themeDisplay.isStatePopUp() || themeDisplay.isWidget() || layoutTypePortlet.hasStateMax() %>">

		<%
		String ppid = ParamUtil.getString(request, "p_p_id");

		String templateId = null;
		String templateContent = null;
		String langType = null;

		if (themeDisplay.isStatePopUp() || themeDisplay.isWidget()) {
			templateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "pop_up";
			templateContent = LayoutTemplateLocalServiceUtil.getContent("pop_up", true, theme.getThemeId());
			langType = LayoutTemplateLocalServiceUtil.getLangType("pop_up", true, theme.getThemeId());
		}
		else {
			ppid = StringUtil.split(layoutTypePortlet.getStateMax())[0];

			templateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "max";
			templateContent = LayoutTemplateLocalServiceUtil.getContent("max", true, theme.getThemeId());
			langType = LayoutTemplateLocalServiceUtil.getLangType("max", true, theme.getThemeId());
		}

		if (Validator.isNotNull(templateContent)) {
			HttpServletRequest originalServletRequest = (HttpServletRequest)request.getAttribute(PortletLayoutTypeControllerWebKeys.ORIGINAL_HTTP_SERVLET_REQUEST);

			RuntimePageUtil.processTemplate(originalServletRequest, response, ppid, new StringTemplateResource(templateId, templateContent), langType);
		}
		%>

	</c:when>
	<c:otherwise>
		<style type="text/css">
			.master-layout-fragment .portlet-header {
				display: none;
			}
		</style>

		<%
		PortletLayoutDisplayContext portletLayoutDisplayContext = new PortletLayoutDisplayContext();
		%>

		<liferay-layout:render-layout-structure
			layoutStructure="<%= portletLayoutDisplayContext.getLayoutStructure(themeDisplay.getScopeGroupId(), themeDisplay.getLayout()) %>"
		/>
	</c:otherwise>
</c:choose>

<liferay-ui:layout-common />