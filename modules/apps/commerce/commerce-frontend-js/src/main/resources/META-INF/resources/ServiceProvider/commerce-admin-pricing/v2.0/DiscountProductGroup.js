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

import AJAX from '../../../utilities/AJAX/index';

const DISCOUNTS_PATH = '/discounts';

const DISCOUNT_RULES_PATH = '/discount-product-groups';

const VERSION = 'v2.0';

function resolvePath(
	basePath = '',
	discountId = '',
	discountProductGroupId = ''
) {
	return `${basePath}${VERSION}${DISCOUNTS_PATH}/${discountId}/${DISCOUNT_RULES_PATH}/${discountProductGroupId}`;
}

export default (basePath) => ({
	addDiscountProductGroup: (discountId, json) =>
		AJAX.POST(resolvePath(basePath, discountId), json),
});
