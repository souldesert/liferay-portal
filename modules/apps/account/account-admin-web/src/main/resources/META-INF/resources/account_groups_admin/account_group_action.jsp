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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

AccountGroupDisplay accountGroupDisplay = (AccountGroupDisplay)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= !accountGroupDisplay.isDefaultAccountGroup() %>">
		<portlet:renderURL var="editAccountGroupURL">
			<portlet:param name="mvcRenderCommandName" value="/account_admin/edit_account_group" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="accountGroupId" value="<%= String.valueOf(accountGroupDisplay.getAccountGroupId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editAccountGroupURL %>"
		/>

		<portlet:actionURL name="/account_admin/delete_account_groups" var="deleteAccountGroupURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="accountGroupIds" value="<%= String.valueOf(accountGroupDisplay.getAccountGroupId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteAccountGroupURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>