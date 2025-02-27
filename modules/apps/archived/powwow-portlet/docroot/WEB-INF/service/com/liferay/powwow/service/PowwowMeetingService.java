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

package com.liferay.powwow.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.powwow.model.PowwowMeeting;
import com.liferay.powwow.model.PowwowParticipant;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for PowwowMeeting. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Shinn Lok
 * @see PowwowMeetingServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface PowwowMeetingService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.powwow.service.impl.PowwowMeetingServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the powwow meeting remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link PowwowMeetingServiceUtil} if injection and service tracking are not available.
	 */
	public PowwowMeeting addPowwowMeeting(
			long groupId, String portletId, long powwowServerId, String name,
			String description, String providerType,
			Map<String, Serializable> providerTypeMetadataMap,
			String languageId, long calendarBookingId, int status,
			List<PowwowParticipant> powwowParticipants,
			ServiceContext serviceContext)
		throws PortalException;

	public PowwowMeeting deletePowwowMeeting(long powwowMeetingId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PowwowMeeting getPowwowMeeting(long powwowMeetingId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PowwowMeeting> getPowwowMeetings(
		long groupId, int start, int end,
		OrderByComparator<PowwowMeeting> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPowwowMeetingsCount(long groupId);

	public PowwowMeeting updatePowwowMeeting(
			long powwowMeetingId, long powwowServerId, String name,
			String description, String providerType,
			Map<String, Serializable> providerTypeMetadataMap,
			String languageId, long calendarBookingId, int status,
			List<PowwowParticipant> powwowParticipants,
			ServiceContext serviceContext)
		throws PortalException;

}