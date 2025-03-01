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

package com.liferay.headless.commerce.admin.site.setting.client.serdes.v1_0;

import com.liferay.headless.commerce.admin.site.setting.client.dto.v1_0.TaxCategory;
import com.liferay.headless.commerce.admin.site.setting.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class TaxCategorySerDes {

	public static TaxCategory toDTO(String json) {
		TaxCategoryJSONParser taxCategoryJSONParser =
			new TaxCategoryJSONParser();

		return taxCategoryJSONParser.parseToDTO(json);
	}

	public static TaxCategory[] toDTOs(String json) {
		TaxCategoryJSONParser taxCategoryJSONParser =
			new TaxCategoryJSONParser();

		return taxCategoryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(TaxCategory taxCategory) {
		if (taxCategory == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (taxCategory.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append(_toJSON(taxCategory.getDescription()));
		}

		if (taxCategory.getGroupId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"groupId\": ");

			sb.append(taxCategory.getGroupId());
		}

		if (taxCategory.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(taxCategory.getId());
		}

		if (taxCategory.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append(_toJSON(taxCategory.getName()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		TaxCategoryJSONParser taxCategoryJSONParser =
			new TaxCategoryJSONParser();

		return taxCategoryJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(TaxCategory taxCategory) {
		if (taxCategory == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (taxCategory.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description", String.valueOf(taxCategory.getDescription()));
		}

		if (taxCategory.getGroupId() == null) {
			map.put("groupId", null);
		}
		else {
			map.put("groupId", String.valueOf(taxCategory.getGroupId()));
		}

		if (taxCategory.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(taxCategory.getId()));
		}

		if (taxCategory.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(taxCategory.getName()));
		}

		return map;
	}

	public static class TaxCategoryJSONParser
		extends BaseJSONParser<TaxCategory> {

		@Override
		protected TaxCategory createDTO() {
			return new TaxCategory();
		}

		@Override
		protected TaxCategory[] createDTOArray(int size) {
			return new TaxCategory[size];
		}

		@Override
		protected void setField(
			TaxCategory taxCategory, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					taxCategory.setDescription(
						(Map)TaxCategorySerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "groupId")) {
				if (jsonParserFieldValue != null) {
					taxCategory.setGroupId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					taxCategory.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					taxCategory.setName(
						(Map)TaxCategorySerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (jsonParserFieldName.equals("status")) {
				throw new IllegalArgumentException();
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}