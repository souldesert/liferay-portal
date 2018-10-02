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

package com.liferay.segments.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import com.liferay.segments.service.SegmentsEntryRelServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link SegmentsEntryRelServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryRelServiceSoap
 * @see HttpPrincipal
 * @see SegmentsEntryRelServiceUtil
 * @generated
 */
@ProviderType
public class SegmentsEntryRelServiceHttp {
	public static com.liferay.segments.model.SegmentsEntryRel addSegmentsEntryRel(
		HttpPrincipal httpPrincipal, long segmentsEntryId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(SegmentsEntryRelServiceUtil.class,
					"addSegmentsEntryRel", _addSegmentsEntryRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					segmentsEntryId, classNameId, classPK, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.segments.model.SegmentsEntryRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteSegmentsEntryRel(HttpPrincipal httpPrincipal,
		long segmentsEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(SegmentsEntryRelServiceUtil.class,
					"deleteSegmentsEntryRel",
					_deleteSegmentsEntryRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					segmentsEntryRelId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.segments.model.SegmentsEntryRel> getSegmentsEntryRels(
		HttpPrincipal httpPrincipal, long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(SegmentsEntryRelServiceUtil.class,
					"getSegmentsEntryRels", _getSegmentsEntryRelsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					segmentsEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.segments.model.SegmentsEntryRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.segments.model.SegmentsEntryRel> getSegmentsEntryRels(
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(SegmentsEntryRelServiceUtil.class,
					"getSegmentsEntryRels", _getSegmentsEntryRelsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, classPK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.segments.model.SegmentsEntryRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SegmentsEntryRelServiceHttp.class);
	private static final Class<?>[] _addSegmentsEntryRelParameterTypes0 = new Class[] {
			long.class, long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteSegmentsEntryRelParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getSegmentsEntryRelsParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getSegmentsEntryRelsParameterTypes3 = new Class[] {
			long.class, long.class, long.class
		};
}