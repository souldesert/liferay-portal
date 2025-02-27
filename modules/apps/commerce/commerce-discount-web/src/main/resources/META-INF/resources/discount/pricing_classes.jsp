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
CommerceDiscountRelDisplayContext commerceDiscountRelDisplayContext = (CommerceDiscountRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceDiscount commerceDiscount = commerceDiscountRelDisplayContext.getCommerceDiscount();
SearchContainer<CommerceDiscountRel> commercePricingClassCommerceDiscountRelSearchContainer = commerceDiscountRelDisplayContext.getCommercePricingClassCommerceDiscountRelSearchContainer();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceDiscountRels"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			disabled="<%= true %>"
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceDiscountRelDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<portlet:actionURL name="editCommerceDiscountRel" var="addCommerceDiscountRelURL" />

		<aui:form action="<%= addCommerceDiscountRelURL %>" cssClass="hide" name="addCommerceDiscountRelFm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="classPKs" type="hidden" value="" />
			<aui:input name="className" type="hidden" value="<%= CommercePricingClass.class.getName() %>" />
			<aui:input name="commerceDiscountId" type="hidden" value="<%= commerceDiscountRelDisplayContext.getCommerceDiscountId() %>" />
		</aui:form>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<liferay-frontend:add-menu-item
				id="addCommerceDiscountRelMenuItem"
				title='<%= LanguageUtil.get(request, "add-discount-product-group") %>'
				url="javascript:;"
			/>
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= commercePricingClassCommerceDiscountRelSearchContainer.getOrderByCol() %>"
			orderByType="<%= commercePricingClassCommerceDiscountRelSearchContainer.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= commerceDiscountRelDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteCommerceDiscountRels();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid container-fluid-max-xl">
	<portlet:actionURL name="editCommerceDiscountRel" var="editCommerceDiscountRelActionURL" />

	<aui:form action="<%= editCommerceDiscountRelActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCommerceDiscountRelIds" type="hidden" />

		<liferay-ui:search-container
			id="commerceDiscountRels"
			searchContainer="<%= commercePricingClassCommerceDiscountRelSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.discount.model.CommerceDiscountRel"
				keyProperty="commerceDiscountRelId"
				modelVar="commerceDiscountRel"
			>

				<%
				CommercePricingClass commercePricingClass = commerceDiscountRelDisplayContext.getCommercePricingClass(commerceDiscountRel);
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="name"
					value="<%= HtmlUtil.escape((commercePricingClass == null) ? StringPool.BLANK : commercePricingClass.getTitle(themeDisplay.getLanguageId())) %>"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/discount_rel_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script use="liferay-item-selector-dialog">
	window.document
		.querySelector('#<portlet:namespace />addCommerceDiscountRelMenuItem')
		.addEventListener('click', function (event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: 'pricingClassSelectItem',
				on: {
					selectedItemChange: function (event) {
						var selectedItems = event.newVal;

						if (selectedItems) {
							window.document.querySelector(
								'#<portlet:namespace />classPKs'
							).value = selectedItems;

							var addCommerceDiscountRelFm = window.document.querySelector(
								'#<portlet:namespace />addCommerceDiscountRelFm'
							);

							submitForm(addCommerceDiscountRelFm);
						}
					},
				},
				title:
					'<liferay-ui:message arguments="<%= commerceDiscount.getTitle() %>" key="add-new-product-group-to-x" />',
				url:
					'<%= commerceDiscountRelDisplayContext.getPricingClassItemSelectorUrl() %>',
			});

			itemSelectorDialog.open();
		});
</aui:script>

<aui:script>
	function <portlet:namespace />deleteCommerceDiscountRels() {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-discount-product-groups" />'
			)
		) {
			var form = window.document['<portlet:namespace />fm'];

			form[
				'<portlet:namespace />deleteCommerceDiscountRelIds'
			].value = Liferay.Util.listCheckedExcept(
				form,
				'<portlet:namespace />allRowIds'
			);

			submitForm(form);
		}
	}
</aui:script>