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
OrderSummaryCheckoutStepDisplayContext orderSummaryCheckoutStepDisplayContext = (OrderSummaryCheckoutStepDisplayContext)request.getAttribute(CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = orderSummaryCheckoutStepDisplayContext.getCommerceOrder();

CommerceOrderPrice commerceOrderPrice = orderSummaryCheckoutStepDisplayContext.getCommerceOrderPrice();

CommerceDiscountValue shippingDiscountValue = commerceOrderPrice.getShippingDiscountValue();
CommerceMoney shippingValueCommerceMoney = commerceOrderPrice.getShippingValue();
CommerceMoney subtotalCommerceMoney = commerceOrderPrice.getSubtotal();
CommerceDiscountValue subtotalCommerceDiscountValue = commerceOrderPrice.getSubtotalDiscountValue();
CommerceMoney taxValueCommerceMoney = commerceOrderPrice.getTaxValue();
CommerceDiscountValue totalCommerceDiscountValue = commerceOrderPrice.getTotalDiscountValue();
CommerceMoney totalOrderCommerceMoney = commerceOrderPrice.getTotal();

String priceDisplayType = orderSummaryCheckoutStepDisplayContext.getCommercePriceDisplayType();

if (priceDisplayType.equals(CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {
	shippingDiscountValue = commerceOrderPrice.getShippingDiscountValueWithTaxAmount();
	shippingValueCommerceMoney = commerceOrderPrice.getShippingValueWithTaxAmount();
	subtotalCommerceMoney = commerceOrderPrice.getSubtotalWithTaxAmount();
	subtotalCommerceDiscountValue = commerceOrderPrice.getSubtotalDiscountValueWithTaxAmount();
	totalCommerceDiscountValue = commerceOrderPrice.getTotalDiscountValueWithTaxAmount();
	totalOrderCommerceMoney = commerceOrderPrice.getTotalWithTaxAmount();
}

String commercePaymentMethodName = StringPool.BLANK;

String commercePaymentMethodKey = commerceOrder.getCommercePaymentMethodKey();

if (commercePaymentMethodKey != null) {
	commercePaymentMethodName = orderSummaryCheckoutStepDisplayContext.getPaymentMethodName(commercePaymentMethodKey, locale);
}

String commerceShippingOptionName = commerceOrder.getShippingOptionName();

Map<Long, List<CommerceOrderValidatorResult>> commerceOrderValidatorResultMap = orderSummaryCheckoutStepDisplayContext.getCommerceOrderValidatorResults();
%>

<div class="commerce-order-summary">
	<liferay-ui:error exception="<%= CommerceDiscountLimitationTimesException.class %>" message="the-inserted-coupon-code-has-reached-its-usage-limit" />
	<liferay-ui:error exception="<%= CommerceOrderBillingAddressException.class %>" message="please-select-a-valid-billing-address" />
	<liferay-ui:error exception="<%= CommerceOrderGuestCheckoutException.class %>" message="you-must-sign-in-to-complete-this-order" />
	<liferay-ui:error exception="<%= CommerceOrderPaymentMethodException.class %>" message="please-select-a-valid-payment-method" />
	<liferay-ui:error exception="<%= CommerceOrderShippingAddressException.class %>" message="please-select-a-valid-shipping-address" />
	<liferay-ui:error exception="<%= CommerceOrderShippingMethodException.class %>" message="please-select-a-valid-shipping-method" />
	<liferay-ui:error exception="<%= NoSuchDiscountException.class %>" message="the-inserted-coupon-is-no-longer-valid" />

	<aui:row>
		<aui:col cssClass="commerce-checkout-summary" width="<%= 70 %>">
			<ul class="commerce-checkout-summary-header">
				<li class="autofit-row">
					<div class="autofit-col autofit-col-expand">
						<h5 class="commerce-title">
							<liferay-ui:message arguments="<%= orderSummaryCheckoutStepDisplayContext.getCommerceOrderItemsQuantity() %>" key="items-x" translateArguments="<%= false %>" />
						</h5>
					</div>
				</li>
			</ul>

			<div class="commerce-checkout-summary-body" id="<portlet:namespace />entriesContainer">
				<liferay-ui:search-container
					cssClass="list-group-flush"
					id="commerceOrderItems"
				>
					<liferay-ui:search-container-results
						results="<%= commerceOrder.getCommerceOrderItems() %>"
					/>

					<liferay-ui:search-container-row
						className="com.liferay.commerce.model.CommerceOrderItem"
						cssClass="entry-display-style"
						keyProperty="CommerceOrderItemId"
						modelVar="commerceOrderItem"
					>

						<%
						CPDefinition cpDefinition = commerceOrderItem.getCPDefinition();
						%>

						<liferay-ui:search-container-column-image
							cssClass="thumbnail-section"
							name="image"
							src="<%= orderSummaryCheckoutStepDisplayContext.getCommerceOrderItemThumbnailSrc(commerceOrderItem) %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="autofit-col-expand"
							name="product"
						>
							<div class="description-section">
								<div class="list-group-title">
									<%= HtmlUtil.escape(cpDefinition.getName(themeDisplay.getLanguageId())) %>
								</div>

								<%
								List<KeyValuePair> keyValuePairs = orderSummaryCheckoutStepDisplayContext.getKeyValuePairs(commerceOrderItem.getCPDefinitionId(), commerceOrderItem.getJson(), locale);

								StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);

								for (KeyValuePair keyValuePair : keyValuePairs) {
									stringJoiner.add(keyValuePair.getValue());
								}
								%>

								<div class="list-group-subtitle"><%= HtmlUtil.escape(stringJoiner.toString()) %></div>

								<c:if test="<%= !commerceOrderValidatorResultMap.isEmpty() %>">

									<%
									List<CommerceOrderValidatorResult> commerceOrderValidatorResults = commerceOrderValidatorResultMap.get(commerceOrderItem.getCommerceOrderItemId());

									for (CommerceOrderValidatorResult commerceOrderValidatorResult : commerceOrderValidatorResults) {
									%>

										<div class="alert-danger commerce-alert-danger">
											<liferay-ui:message key="<%= commerceOrderValidatorResult.getLocalizedMessage() %>" />
										</div>

									<%
									}
									%>

								</c:if>
							</div>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							name="quantity"
						>
							<div class="quantity-section">
								<span class="commerce-quantity"><%= commerceOrderItem.getQuantity() %></span><span class="inline-item-after">x</span>
							</div>
						</liferay-ui:search-container-column-text>

						<%
						CommerceProductPrice commerceProductPrice = orderSummaryCheckoutStepDisplayContext.getCommerceProductPrice(commerceOrderItem);
						CPInstance cpInstance = commerceOrderItem.fetchCPInstance();
						%>

						<liferay-ui:search-container-column-text
							name="price"
						>
							<c:if test="<%= commerceProductPrice != null %>">

								<%
								CommerceMoney unitPriceCommerceMoney = commerceProductPrice.getUnitPrice();
								CommerceMoney unitPromoPriceCommerceMoney = commerceProductPrice.getUnitPromoPrice();

								if (priceDisplayType.equals(CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {
									unitPriceCommerceMoney = commerceProductPrice.getUnitPriceWithTaxAmount();
									unitPromoPriceCommerceMoney = commerceProductPrice.getUnitPromoPriceWithTaxAmount();
								}
								%>

								<div class="value-section">
									<span class="price">
										<c:choose>
											<c:when test="<%= !unitPromoPriceCommerceMoney.isEmpty() && CommerceBigDecimalUtil.gt(unitPromoPriceCommerceMoney.getPrice(), BigDecimal.ZERO) %>">
												<span class="price-value price-value-promo">
													<%= HtmlUtil.escape(unitPromoPriceCommerceMoney.format(locale)) %>
												</span>
												<span class="price-value price-value-inactive">
													<%= HtmlUtil.escape(unitPriceCommerceMoney.format(locale)) %>
												</span>
											</c:when>
											<c:otherwise>
												<span class="price-value {$additionalPriceClasses}">
													<%= HtmlUtil.escape(unitPriceCommerceMoney.format(locale)) %>
												</span>
											</c:otherwise>
										</c:choose>
									</span>

									<c:if test="<%= (cpInstance != null) && Validator.isNotNull(cpInstance.getCPSubscriptionInfo()) %>">
										<span class="commerce-subscription-info">
											<commerce-ui:product-subscription-info
												CPInstanceId="<%= commerceOrderItem.getCPInstanceId() %>"
												showDuration="<%= false %>"
											/>
										</span>
									</c:if>
								</div>
							</c:if>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							name="discount"
						>
							<c:if test="<%= commerceProductPrice != null %>">

								<%
								CommerceDiscountValue discountValue = commerceProductPrice.getDiscountValue();

								if (priceDisplayType.equals(CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {
									discountValue = commerceProductPrice.getDiscountValueWithTaxAmount();
								}

								CommerceMoney discountAmountCommerceMoney = null;

								if (discountValue != null) {
									discountAmountCommerceMoney = discountValue.getDiscountAmount();
								}
								%>

								<div class="value-section">
									<span class="commerce-value">
										<%= (discountAmountCommerceMoney == null) ? StringPool.BLANK : HtmlUtil.escape(discountAmountCommerceMoney.format(locale)) %>
									</span>
								</div>
							</c:if>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							name="total"
						>
							<c:if test="<%= commerceProductPrice != null %>">

								<%
								CommerceMoney finalPriceCommerceMoney = commerceProductPrice.getFinalPrice();

								if (priceDisplayType.equals(CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {
									finalPriceCommerceMoney = commerceProductPrice.getFinalPriceWithTaxAmount();
								}
								%>

								<div class="value-section">
									<span class="commerce-value">
										<%= HtmlUtil.escape(finalPriceCommerceMoney.format(locale)) %>
									</span>
								</div>
							</c:if>
						</liferay-ui:search-container-column-text>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						displayStyle="list"
						markupView="lexicon"
						paginate="<%= false %>"
					/>
				</liferay-ui:search-container>
			</div>

			<ul class="commerce-checkout-summary-footer">
				<li class="autofit-row commerce-subtotal">
					<div class="autofit-col autofit-col-expand">
						<div class="commerce-description"><liferay-ui:message key="subtotal" /></div>
					</div>

					<div class="autofit-col">
						<div class="commerce-value"><%= HtmlUtil.escape(subtotalCommerceMoney.format(locale)) %></div>
					</div>
				</li>

				<c:if test="<%= subtotalCommerceDiscountValue != null %>">

					<%
					CommerceMoney subtotalDiscountAmountCommerceMoney = subtotalCommerceDiscountValue.getDiscountAmount();
					%>

					<li class="autofit-row commerce-subtotal-discount">
						<div class="autofit-col autofit-col-expand">
							<div class="commerce-description"><liferay-ui:message key="subtotal-discount" /></div>
						</div>

						<div class="commerce-value">
							<%= HtmlUtil.escape(subtotalDiscountAmountCommerceMoney.format(locale)) %>
						</div>
					</li>
					<li class="autofit-row commerce-subtotal-discount">
						<div class="autofit-col autofit-col-expand"></div>

						<div class="commerce-value">
							<%= HtmlUtil.escape(orderSummaryCheckoutStepDisplayContext.getLocalizedPercentage(subtotalCommerceDiscountValue.getDiscountPercentage(), locale)) %>
						</div>
					</li>
				</c:if>

				<li class="autofit-row commerce-delivery">
					<div class="autofit-col autofit-col-expand">
						<div class="commerce-description"><liferay-ui:message key="delivery" /></div>
					</div>

					<div class="autofit-col">
						<div class="commerce-value"><%= HtmlUtil.escape(shippingValueCommerceMoney.format(locale)) %></div>
					</div>
				</li>

				<c:if test="<%= shippingDiscountValue != null %>">

					<%
					CommerceMoney shippingDiscountAmountCommerceMoney = shippingDiscountValue.getDiscountAmount();
					%>

					<li class="autofit-row commerce-delivery-discount">
						<div class="autofit-col autofit-col-expand">
							<div class="commerce-description"><liferay-ui:message key="delivery-discount" /></div>
						</div>

						<div class="commerce-value">
							<%= HtmlUtil.escape(shippingDiscountAmountCommerceMoney.format(locale)) %>
						</div>
					</li>
					<li class="autofit-row commerce-delivery-discount">
						<div class="autofit-col autofit-col-expand"></div>

						<div class="commerce-value">
							<%= HtmlUtil.escape(orderSummaryCheckoutStepDisplayContext.getLocalizedPercentage(shippingDiscountValue.getDiscountPercentage(), locale)) %>
						</div>
					</li>
				</c:if>

				<c:if test="<%= priceDisplayType.equals(CommercePricingConstants.TAX_EXCLUDED_FROM_PRICE) %>">
					<li class="autofit-row commerce-tax">
						<div class="autofit-col autofit-col-expand">
							<div class="commerce-description"><liferay-ui:message key="tax" /></div>
						</div>

						<div class="autofit-col">
							<div class="commerce-value"><%= HtmlUtil.escape(taxValueCommerceMoney.format(locale)) %></div>
						</div>
					</li>
				</c:if>

				<c:if test="<%= totalCommerceDiscountValue != null %>">

					<%
					CommerceMoney totalDiscountAmountCommerceAmount = totalCommerceDiscountValue.getDiscountAmount();
					%>

					<li class="autofit-row commerce-total-discount">
						<div class="autofit-col autofit-col-expand">
							<div class="commerce-description"><liferay-ui:message key="total-discount" /></div>
						</div>

						<div class="autofit-col commerce-value">
							<%= HtmlUtil.escape(totalDiscountAmountCommerceAmount.format(locale)) %>
						</div>
					</li>
					<li class="autofit-row commerce-total-discount">
						<div class="autofit-col autofit-col-expand"></div>

						<div class="autofit-col commerce-value">
							<%= HtmlUtil.escape(orderSummaryCheckoutStepDisplayContext.getLocalizedPercentage(totalCommerceDiscountValue.getDiscountPercentage(), locale)) %>
						</div>
					</li>
				</c:if>

				<li class="autofit-row commerce-total">
					<div class="autofit-col autofit-col-expand">
						<div class="commerce-description"><liferay-ui:message key="total" /></div>
					</div>

					<div class="autofit-col">
						<div class="commerce-value"><%= HtmlUtil.escape(totalOrderCommerceMoney.format(locale)) %></div>
					</div>
				</li>
			</ul>
		</aui:col>

		<aui:col cssClass="commerce-checkout-info" width="<%= 30 %>">

			<%
			CommerceAddress shippingAddress = commerceOrder.getShippingAddress();
			%>

			<c:if test="<%= shippingAddress != null %>">
				<address class="shipping-address">
					<h5>
						<liferay-ui:message key="shipping-address" />
					</h5>

					<%
					request.setAttribute("address.jsp-commerceAddress", shippingAddress);
					%>

					<%= HtmlUtil.escape(shippingAddress.getName()) %> <br />
					<%= HtmlUtil.escape(shippingAddress.getStreet1()) %> <br />

					<c:if test="<%= Validator.isNotNull(shippingAddress.getStreet2()) %>">
						<%= HtmlUtil.escape(shippingAddress.getStreet2()) %> <br />
					</c:if>

					<c:if test="<%= Validator.isNotNull(shippingAddress.getStreet3()) %>">
						<%= HtmlUtil.escape(shippingAddress.getStreet3()) %> <br />
					</c:if>

					<%= HtmlUtil.escape(shippingAddress.getCity()) %> <br />

					<%
					CommerceCountry commerceCountry = shippingAddress.getCommerceCountry();
					%>

					<c:if test="<%= commerceCountry != null %>">
						<%= HtmlUtil.escape(commerceCountry.getName(locale)) %><br />
					</c:if>
				</address>
			</c:if>

			<c:if test="<%= Validator.isNotNull(commerceShippingOptionName) %>">
				<div class="shipping-method">
					<h5>
						<liferay-ui:message key="method" />
					</h5>

					<div class="shipping-description">
						<%= HtmlUtil.escape(commerceShippingOptionName) %>
					</div>

					<div class="shipping-cost">
						<%= HtmlUtil.escape(shippingValueCommerceMoney.format(locale)) %>
					</div>
				</div>
			</c:if>

			<c:if test="<%= Validator.isNotNull(commercePaymentMethodName) %>">
				<div class="payment-method">
					<h5>
						<liferay-ui:message key="payment" />
					</h5>

					<div class="shipping-description">
						<%= HtmlUtil.escape(commercePaymentMethodName) %>
					</div>
				</div>
			</c:if>
		</aui:col>
	</aui:row>
</div>