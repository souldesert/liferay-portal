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

package com.liferay.portlet.messageboards.service.persistence.impl;

import com.liferay.message.boards.kernel.exception.NoSuchDiscussionException;
import com.liferay.message.boards.kernel.model.MBDiscussion;
import com.liferay.message.boards.kernel.service.persistence.MBDiscussionPersistence;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portlet.messageboards.model.impl.MBDiscussionImpl;
import com.liferay.portlet.messageboards.model.impl.MBDiscussionModelImpl;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the message boards discussion service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MBDiscussionPersistenceImpl
	extends BasePersistenceImpl<MBDiscussion>
	implements MBDiscussionPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>MBDiscussionUtil</code> to access the message boards discussion persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		MBDiscussionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the message boards discussions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards discussions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @return the range of matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards discussions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MBDiscussion> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards discussions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MBDiscussion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<MBDiscussion> list = null;

		if (useFinderCache) {
			list = (List<MBDiscussion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBDiscussion mbDiscussion : list) {
					if (!uuid.equals(mbDiscussion.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_MBDISCUSSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(MBDiscussionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<MBDiscussion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBDiscussion>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message boards discussion in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards discussion
	 * @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion findByUuid_First(
			String uuid, OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = fetchByUuid_First(uuid, orderByComparator);

		if (mbDiscussion != null) {
			return mbDiscussion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchDiscussionException(msg.toString());
	}

	/**
	 * Returns the first message boards discussion in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByUuid_First(
		String uuid, OrderByComparator<MBDiscussion> orderByComparator) {

		List<MBDiscussion> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards discussion in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards discussion
	 * @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion findByUuid_Last(
			String uuid, OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = fetchByUuid_Last(uuid, orderByComparator);

		if (mbDiscussion != null) {
			return mbDiscussion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchDiscussionException(msg.toString());
	}

	/**
	 * Returns the last message boards discussion in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByUuid_Last(
		String uuid, OrderByComparator<MBDiscussion> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<MBDiscussion> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards discussions before and after the current message boards discussion in the ordered set where uuid = &#63;.
	 *
	 * @param discussionId the primary key of the current message boards discussion
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards discussion
	 * @throws NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	 */
	@Override
	public MBDiscussion[] findByUuid_PrevAndNext(
			long discussionId, String uuid,
			OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException {

		uuid = Objects.toString(uuid, "");

		MBDiscussion mbDiscussion = findByPrimaryKey(discussionId);

		Session session = null;

		try {
			session = openSession();

			MBDiscussion[] array = new MBDiscussionImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, mbDiscussion, uuid, orderByComparator, true);

			array[1] = mbDiscussion;

			array[2] = getByUuid_PrevAndNext(
				session, mbDiscussion, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBDiscussion getByUuid_PrevAndNext(
		Session session, MBDiscussion mbDiscussion, String uuid,
		OrderByComparator<MBDiscussion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBDISCUSSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(MBDiscussionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbDiscussion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<MBDiscussion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards discussions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (MBDiscussion mbDiscussion :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mbDiscussion);
		}
	}

	/**
	 * Returns the number of message boards discussions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching message boards discussions
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBDISCUSSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"mbDiscussion.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(mbDiscussion.uuid IS NULL OR mbDiscussion.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the message boards discussion where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchDiscussionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards discussion
	 * @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion findByUUID_G(String uuid, long groupId)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = fetchByUUID_G(uuid, groupId);

		if (mbDiscussion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchDiscussionException(msg.toString());
		}

		return mbDiscussion;
	}

	/**
	 * Returns the message boards discussion where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the message boards discussion where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof MBDiscussion) {
			MBDiscussion mbDiscussion = (MBDiscussion)result;

			if (!Objects.equals(uuid, mbDiscussion.getUuid()) ||
				(groupId != mbDiscussion.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_MBDISCUSSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<MBDiscussion> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					MBDiscussion mbDiscussion = list.get(0);

					result = mbDiscussion;

					cacheResult(mbDiscussion);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByUUID_G, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (MBDiscussion)result;
		}
	}

	/**
	 * Removes the message boards discussion where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the message boards discussion that was removed
	 */
	@Override
	public MBDiscussion removeByUUID_G(String uuid, long groupId)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = findByUUID_G(uuid, groupId);

		return remove(mbDiscussion);
	}

	/**
	 * Returns the number of message boards discussions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching message boards discussions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBDISCUSSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"mbDiscussion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(mbDiscussion.uuid IS NULL OR mbDiscussion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"mbDiscussion.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the message boards discussions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards discussions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @return the range of matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards discussions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBDiscussion> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards discussions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MBDiscussion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<MBDiscussion> list = null;

		if (useFinderCache) {
			list = (List<MBDiscussion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBDiscussion mbDiscussion : list) {
					if (!uuid.equals(mbDiscussion.getUuid()) ||
						(companyId != mbDiscussion.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_MBDISCUSSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(MBDiscussionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<MBDiscussion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBDiscussion>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message boards discussion in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards discussion
	 * @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (mbDiscussion != null) {
			return mbDiscussion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchDiscussionException(msg.toString());
	}

	/**
	 * Returns the first message boards discussion in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<MBDiscussion> orderByComparator) {

		List<MBDiscussion> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards discussion in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards discussion
	 * @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (mbDiscussion != null) {
			return mbDiscussion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchDiscussionException(msg.toString());
	}

	/**
	 * Returns the last message boards discussion in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<MBDiscussion> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<MBDiscussion> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards discussions before and after the current message boards discussion in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param discussionId the primary key of the current message boards discussion
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards discussion
	 * @throws NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	 */
	@Override
	public MBDiscussion[] findByUuid_C_PrevAndNext(
			long discussionId, String uuid, long companyId,
			OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException {

		uuid = Objects.toString(uuid, "");

		MBDiscussion mbDiscussion = findByPrimaryKey(discussionId);

		Session session = null;

		try {
			session = openSession();

			MBDiscussion[] array = new MBDiscussionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, mbDiscussion, uuid, companyId, orderByComparator,
				true);

			array[1] = mbDiscussion;

			array[2] = getByUuid_C_PrevAndNext(
				session, mbDiscussion, uuid, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBDiscussion getByUuid_C_PrevAndNext(
		Session session, MBDiscussion mbDiscussion, String uuid, long companyId,
		OrderByComparator<MBDiscussion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_MBDISCUSSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(MBDiscussionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbDiscussion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<MBDiscussion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards discussions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (MBDiscussion mbDiscussion :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(mbDiscussion);
		}
	}

	/**
	 * Returns the number of message boards discussions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching message boards discussions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBDISCUSSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"mbDiscussion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(mbDiscussion.uuid IS NULL OR mbDiscussion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"mbDiscussion.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByClassNameId;
	private FinderPath _finderPathWithoutPaginationFindByClassNameId;
	private FinderPath _finderPathCountByClassNameId;

	/**
	 * Returns all the message boards discussions where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByClassNameId(long classNameId) {
		return findByClassNameId(
			classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards discussions where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @return the range of matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByClassNameId(
		long classNameId, int start, int end) {

		return findByClassNameId(classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards discussions where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByClassNameId(
		long classNameId, int start, int end,
		OrderByComparator<MBDiscussion> orderByComparator) {

		return findByClassNameId(
			classNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards discussions where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards discussions
	 */
	@Override
	public List<MBDiscussion> findByClassNameId(
		long classNameId, int start, int end,
		OrderByComparator<MBDiscussion> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByClassNameId;
				finderArgs = new Object[] {classNameId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByClassNameId;
			finderArgs = new Object[] {
				classNameId, start, end, orderByComparator
			};
		}

		List<MBDiscussion> list = null;

		if (useFinderCache) {
			list = (List<MBDiscussion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBDiscussion mbDiscussion : list) {
					if (classNameId != mbDiscussion.getClassNameId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_MBDISCUSSION_WHERE);

			query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(MBDiscussionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				if (!pagination) {
					list = (List<MBDiscussion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBDiscussion>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first message boards discussion in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards discussion
	 * @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion findByClassNameId_First(
			long classNameId, OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = fetchByClassNameId_First(
			classNameId, orderByComparator);

		if (mbDiscussion != null) {
			return mbDiscussion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append("}");

		throw new NoSuchDiscussionException(msg.toString());
	}

	/**
	 * Returns the first message boards discussion in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByClassNameId_First(
		long classNameId, OrderByComparator<MBDiscussion> orderByComparator) {

		List<MBDiscussion> list = findByClassNameId(
			classNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards discussion in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards discussion
	 * @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion findByClassNameId_Last(
			long classNameId, OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = fetchByClassNameId_Last(
			classNameId, orderByComparator);

		if (mbDiscussion != null) {
			return mbDiscussion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append("}");

		throw new NoSuchDiscussionException(msg.toString());
	}

	/**
	 * Returns the last message boards discussion in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByClassNameId_Last(
		long classNameId, OrderByComparator<MBDiscussion> orderByComparator) {

		int count = countByClassNameId(classNameId);

		if (count == 0) {
			return null;
		}

		List<MBDiscussion> list = findByClassNameId(
			classNameId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards discussions before and after the current message boards discussion in the ordered set where classNameId = &#63;.
	 *
	 * @param discussionId the primary key of the current message boards discussion
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards discussion
	 * @throws NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	 */
	@Override
	public MBDiscussion[] findByClassNameId_PrevAndNext(
			long discussionId, long classNameId,
			OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = findByPrimaryKey(discussionId);

		Session session = null;

		try {
			session = openSession();

			MBDiscussion[] array = new MBDiscussionImpl[3];

			array[0] = getByClassNameId_PrevAndNext(
				session, mbDiscussion, classNameId, orderByComparator, true);

			array[1] = mbDiscussion;

			array[2] = getByClassNameId_PrevAndNext(
				session, mbDiscussion, classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBDiscussion getByClassNameId_PrevAndNext(
		Session session, MBDiscussion mbDiscussion, long classNameId,
		OrderByComparator<MBDiscussion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBDISCUSSION_WHERE);

		query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(MBDiscussionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbDiscussion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<MBDiscussion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards discussions where classNameId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByClassNameId(long classNameId) {
		for (MBDiscussion mbDiscussion :
				findByClassNameId(
					classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mbDiscussion);
		}
	}

	/**
	 * Returns the number of message boards discussions where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the number of matching message boards discussions
	 */
	@Override
	public int countByClassNameId(long classNameId) {
		FinderPath finderPath = _finderPathCountByClassNameId;

		Object[] finderArgs = new Object[] {classNameId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBDISCUSSION_WHERE);

			query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2 =
		"mbDiscussion.classNameId = ?";

	private FinderPath _finderPathFetchByThreadId;
	private FinderPath _finderPathCountByThreadId;

	/**
	 * Returns the message boards discussion where threadId = &#63; or throws a <code>NoSuchDiscussionException</code> if it could not be found.
	 *
	 * @param threadId the thread ID
	 * @return the matching message boards discussion
	 * @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion findByThreadId(long threadId)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = fetchByThreadId(threadId);

		if (mbDiscussion == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("threadId=");
			msg.append(threadId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchDiscussionException(msg.toString());
		}

		return mbDiscussion;
	}

	/**
	 * Returns the message boards discussion where threadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param threadId the thread ID
	 * @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByThreadId(long threadId) {
		return fetchByThreadId(threadId, true);
	}

	/**
	 * Returns the message boards discussion where threadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param threadId the thread ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByThreadId(long threadId, boolean useFinderCache) {
		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {threadId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByThreadId, finderArgs, this);
		}

		if (result instanceof MBDiscussion) {
			MBDiscussion mbDiscussion = (MBDiscussion)result;

			if (threadId != mbDiscussion.getThreadId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_MBDISCUSSION_WHERE);

			query.append(_FINDER_COLUMN_THREADID_THREADID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				List<MBDiscussion> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByThreadId, finderArgs, list);
					}
				}
				else {
					MBDiscussion mbDiscussion = list.get(0);

					result = mbDiscussion;

					cacheResult(mbDiscussion);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByThreadId, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (MBDiscussion)result;
		}
	}

	/**
	 * Removes the message boards discussion where threadId = &#63; from the database.
	 *
	 * @param threadId the thread ID
	 * @return the message boards discussion that was removed
	 */
	@Override
	public MBDiscussion removeByThreadId(long threadId)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = findByThreadId(threadId);

		return remove(mbDiscussion);
	}

	/**
	 * Returns the number of message boards discussions where threadId = &#63;.
	 *
	 * @param threadId the thread ID
	 * @return the number of matching message boards discussions
	 */
	@Override
	public int countByThreadId(long threadId) {
		FinderPath finderPath = _finderPathCountByThreadId;

		Object[] finderArgs = new Object[] {threadId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBDISCUSSION_WHERE);

			query.append(_FINDER_COLUMN_THREADID_THREADID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(threadId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_THREADID_THREADID_2 =
		"mbDiscussion.threadId = ?";

	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns the message boards discussion where classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchDiscussionException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching message boards discussion
	 * @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion findByC_C(long classNameId, long classPK)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = fetchByC_C(classNameId, classPK);

		if (mbDiscussion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchDiscussionException(msg.toString());
		}

		return mbDiscussion;
	}

	/**
	 * Returns the message boards discussion where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByC_C(long classNameId, long classPK) {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns the message boards discussion where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	 */
	@Override
	public MBDiscussion fetchByC_C(
		long classNameId, long classPK, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {classNameId, classPK};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByC_C, finderArgs, this);
		}

		if (result instanceof MBDiscussion) {
			MBDiscussion mbDiscussion = (MBDiscussion)result;

			if ((classNameId != mbDiscussion.getClassNameId()) ||
				(classPK != mbDiscussion.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_MBDISCUSSION_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<MBDiscussion> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByC_C, finderArgs, list);
					}
				}
				else {
					MBDiscussion mbDiscussion = list.get(0);

					result = mbDiscussion;

					cacheResult(mbDiscussion);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByC_C, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (MBDiscussion)result;
		}
	}

	/**
	 * Removes the message boards discussion where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the message boards discussion that was removed
	 */
	@Override
	public MBDiscussion removeByC_C(long classNameId, long classPK)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = findByC_C(classNameId, classPK);

		return remove(mbDiscussion);
	}

	/**
	 * Returns the number of message boards discussions where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching message boards discussions
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBDISCUSSION_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"mbDiscussion.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"mbDiscussion.classPK = ?";

	public MBDiscussionPersistenceImpl() {
		setModelClass(MBDiscussion.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
				"_dbColumnNames");

			field.setAccessible(true);

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the message boards discussion in the entity cache if it is enabled.
	 *
	 * @param mbDiscussion the message boards discussion
	 */
	@Override
	public void cacheResult(MBDiscussion mbDiscussion) {
		EntityCacheUtil.putResult(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED, MBDiscussionImpl.class,
			mbDiscussion.getPrimaryKey(), mbDiscussion);

		FinderCacheUtil.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {mbDiscussion.getUuid(), mbDiscussion.getGroupId()},
			mbDiscussion);

		FinderCacheUtil.putResult(
			_finderPathFetchByThreadId,
			new Object[] {mbDiscussion.getThreadId()}, mbDiscussion);

		FinderCacheUtil.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				mbDiscussion.getClassNameId(), mbDiscussion.getClassPK()
			},
			mbDiscussion);

		mbDiscussion.resetOriginalValues();
	}

	/**
	 * Caches the message boards discussions in the entity cache if it is enabled.
	 *
	 * @param mbDiscussions the message boards discussions
	 */
	@Override
	public void cacheResult(List<MBDiscussion> mbDiscussions) {
		for (MBDiscussion mbDiscussion : mbDiscussions) {
			if (EntityCacheUtil.getResult(
					MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
					MBDiscussionImpl.class, mbDiscussion.getPrimaryKey()) ==
						null) {

				cacheResult(mbDiscussion);
			}
			else {
				mbDiscussion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all message boards discussions.
	 *
	 * <p>
	 * The <code>com.liferay.portal.kernel.dao.orm.EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(MBDiscussionImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the message boards discussion.
	 *
	 * <p>
	 * The <code>com.liferay.portal.kernel.dao.orm.EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MBDiscussion mbDiscussion) {
		EntityCacheUtil.removeResult(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED, MBDiscussionImpl.class,
			mbDiscussion.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((MBDiscussionModelImpl)mbDiscussion, true);
	}

	@Override
	public void clearCache(List<MBDiscussion> mbDiscussions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MBDiscussion mbDiscussion : mbDiscussions) {
			EntityCacheUtil.removeResult(
				MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
				MBDiscussionImpl.class, mbDiscussion.getPrimaryKey());

			clearUniqueFindersCache((MBDiscussionModelImpl)mbDiscussion, true);
		}
	}

	protected void cacheUniqueFindersCache(
		MBDiscussionModelImpl mbDiscussionModelImpl) {

		Object[] args = new Object[] {
			mbDiscussionModelImpl.getUuid(), mbDiscussionModelImpl.getGroupId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByUUID_G, args, mbDiscussionModelImpl, false);

		args = new Object[] {mbDiscussionModelImpl.getThreadId()};

		FinderCacheUtil.putResult(
			_finderPathCountByThreadId, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByThreadId, args, mbDiscussionModelImpl, false);

		args = new Object[] {
			mbDiscussionModelImpl.getClassNameId(),
			mbDiscussionModelImpl.getClassPK()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByC_C, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByC_C, args, mbDiscussionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		MBDiscussionModelImpl mbDiscussionModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				mbDiscussionModelImpl.getUuid(),
				mbDiscussionModelImpl.getGroupId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUUID_G, args);
			FinderCacheUtil.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((mbDiscussionModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				mbDiscussionModelImpl.getOriginalUuid(),
				mbDiscussionModelImpl.getOriginalGroupId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUUID_G, args);
			FinderCacheUtil.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {mbDiscussionModelImpl.getThreadId()};

			FinderCacheUtil.removeResult(_finderPathCountByThreadId, args);
			FinderCacheUtil.removeResult(_finderPathFetchByThreadId, args);
		}

		if ((mbDiscussionModelImpl.getColumnBitmask() &
			 _finderPathFetchByThreadId.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				mbDiscussionModelImpl.getOriginalThreadId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByThreadId, args);
			FinderCacheUtil.removeResult(_finderPathFetchByThreadId, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				mbDiscussionModelImpl.getClassNameId(),
				mbDiscussionModelImpl.getClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_C, args);
		}

		if ((mbDiscussionModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_C.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				mbDiscussionModelImpl.getOriginalClassNameId(),
				mbDiscussionModelImpl.getOriginalClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_C, args);
		}
	}

	/**
	 * Creates a new message boards discussion with the primary key. Does not add the message boards discussion to the database.
	 *
	 * @param discussionId the primary key for the new message boards discussion
	 * @return the new message boards discussion
	 */
	@Override
	public MBDiscussion create(long discussionId) {
		MBDiscussion mbDiscussion = new MBDiscussionImpl();

		mbDiscussion.setNew(true);
		mbDiscussion.setPrimaryKey(discussionId);

		String uuid = PortalUUIDUtil.generate();

		mbDiscussion.setUuid(uuid);

		mbDiscussion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return mbDiscussion;
	}

	/**
	 * Removes the message boards discussion with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param discussionId the primary key of the message boards discussion
	 * @return the message boards discussion that was removed
	 * @throws NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	 */
	@Override
	public MBDiscussion remove(long discussionId)
		throws NoSuchDiscussionException {

		return remove((Serializable)discussionId);
	}

	/**
	 * Removes the message boards discussion with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the message boards discussion
	 * @return the message boards discussion that was removed
	 * @throws NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	 */
	@Override
	public MBDiscussion remove(Serializable primaryKey)
		throws NoSuchDiscussionException {

		Session session = null;

		try {
			session = openSession();

			MBDiscussion mbDiscussion = (MBDiscussion)session.get(
				MBDiscussionImpl.class, primaryKey);

			if (mbDiscussion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDiscussionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(mbDiscussion);
		}
		catch (NoSuchDiscussionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected MBDiscussion removeImpl(MBDiscussion mbDiscussion) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(mbDiscussion)) {
				mbDiscussion = (MBDiscussion)session.get(
					MBDiscussionImpl.class, mbDiscussion.getPrimaryKeyObj());
			}

			if (mbDiscussion != null) {
				session.delete(mbDiscussion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (mbDiscussion != null) {
			clearCache(mbDiscussion);
		}

		return mbDiscussion;
	}

	@Override
	public MBDiscussion updateImpl(MBDiscussion mbDiscussion) {
		boolean isNew = mbDiscussion.isNew();

		if (!(mbDiscussion instanceof MBDiscussionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(mbDiscussion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					mbDiscussion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in mbDiscussion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom MBDiscussion implementation " +
					mbDiscussion.getClass());
		}

		MBDiscussionModelImpl mbDiscussionModelImpl =
			(MBDiscussionModelImpl)mbDiscussion;

		if (Validator.isNull(mbDiscussion.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mbDiscussion.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (mbDiscussion.getCreateDate() == null)) {
			if (serviceContext == null) {
				mbDiscussion.setCreateDate(now);
			}
			else {
				mbDiscussion.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!mbDiscussionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				mbDiscussion.setModifiedDate(now);
			}
			else {
				mbDiscussion.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (mbDiscussion.isNew()) {
				session.save(mbDiscussion);

				mbDiscussion.setNew(false);
			}
			else {
				mbDiscussion = (MBDiscussion)session.merge(mbDiscussion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!MBDiscussionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {mbDiscussionModelImpl.getUuid()};

			FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				mbDiscussionModelImpl.getUuid(),
				mbDiscussionModelImpl.getCompanyId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {mbDiscussionModelImpl.getClassNameId()};

			FinderCacheUtil.removeResult(_finderPathCountByClassNameId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByClassNameId, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((mbDiscussionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					mbDiscussionModelImpl.getOriginalUuid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {mbDiscussionModelImpl.getUuid()};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((mbDiscussionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					mbDiscussionModelImpl.getOriginalUuid(),
					mbDiscussionModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					mbDiscussionModelImpl.getUuid(),
					mbDiscussionModelImpl.getCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((mbDiscussionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByClassNameId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					mbDiscussionModelImpl.getOriginalClassNameId()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByClassNameId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByClassNameId, args);

				args = new Object[] {mbDiscussionModelImpl.getClassNameId()};

				FinderCacheUtil.removeResult(
					_finderPathCountByClassNameId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByClassNameId, args);
			}
		}

		EntityCacheUtil.putResult(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED, MBDiscussionImpl.class,
			mbDiscussion.getPrimaryKey(), mbDiscussion, false);

		clearUniqueFindersCache(mbDiscussionModelImpl, false);
		cacheUniqueFindersCache(mbDiscussionModelImpl);

		mbDiscussion.resetOriginalValues();

		return mbDiscussion;
	}

	/**
	 * Returns the message boards discussion with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards discussion
	 * @return the message boards discussion
	 * @throws NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	 */
	@Override
	public MBDiscussion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDiscussionException {

		MBDiscussion mbDiscussion = fetchByPrimaryKey(primaryKey);

		if (mbDiscussion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDiscussionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return mbDiscussion;
	}

	/**
	 * Returns the message boards discussion with the primary key or throws a <code>NoSuchDiscussionException</code> if it could not be found.
	 *
	 * @param discussionId the primary key of the message boards discussion
	 * @return the message boards discussion
	 * @throws NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	 */
	@Override
	public MBDiscussion findByPrimaryKey(long discussionId)
		throws NoSuchDiscussionException {

		return findByPrimaryKey((Serializable)discussionId);
	}

	/**
	 * Returns the message boards discussion with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards discussion
	 * @return the message boards discussion, or <code>null</code> if a message boards discussion with the primary key could not be found
	 */
	@Override
	public MBDiscussion fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = EntityCacheUtil.getResult(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED, MBDiscussionImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		MBDiscussion mbDiscussion = (MBDiscussion)serializable;

		if (mbDiscussion == null) {
			Session session = null;

			try {
				session = openSession();

				mbDiscussion = (MBDiscussion)session.get(
					MBDiscussionImpl.class, primaryKey);

				if (mbDiscussion != null) {
					cacheResult(mbDiscussion);
				}
				else {
					EntityCacheUtil.putResult(
						MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
						MBDiscussionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(
					MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
					MBDiscussionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return mbDiscussion;
	}

	/**
	 * Returns the message boards discussion with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param discussionId the primary key of the message boards discussion
	 * @return the message boards discussion, or <code>null</code> if a message boards discussion with the primary key could not be found
	 */
	@Override
	public MBDiscussion fetchByPrimaryKey(long discussionId) {
		return fetchByPrimaryKey((Serializable)discussionId);
	}

	@Override
	public Map<Serializable, MBDiscussion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, MBDiscussion> map =
			new HashMap<Serializable, MBDiscussion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			MBDiscussion mbDiscussion = fetchByPrimaryKey(primaryKey);

			if (mbDiscussion != null) {
				map.put(primaryKey, mbDiscussion);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = EntityCacheUtil.getResult(
				MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
				MBDiscussionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (MBDiscussion)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler(
			uncachedPrimaryKeys.size() * 2 + 1);

		query.append(_SQL_SELECT_MBDISCUSSION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (MBDiscussion mbDiscussion : (List<MBDiscussion>)q.list()) {
				map.put(mbDiscussion.getPrimaryKeyObj(), mbDiscussion);

				cacheResult(mbDiscussion);

				uncachedPrimaryKeys.remove(mbDiscussion.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(
					MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
					MBDiscussionImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the message boards discussions.
	 *
	 * @return the message boards discussions
	 */
	@Override
	public List<MBDiscussion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards discussions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @return the range of message boards discussions
	 */
	@Override
	public List<MBDiscussion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards discussions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message boards discussions
	 */
	@Override
	public List<MBDiscussion> findAll(
		int start, int end, OrderByComparator<MBDiscussion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards discussions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>MBDiscussionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards discussions
	 * @param end the upper bound of the range of message boards discussions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of message boards discussions
	 */
	@Override
	public List<MBDiscussion> findAll(
		int start, int end, OrderByComparator<MBDiscussion> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<MBDiscussion> list = null;

		if (useFinderCache) {
			list = (List<MBDiscussion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_MBDISCUSSION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MBDISCUSSION;

				if (pagination) {
					sql = sql.concat(MBDiscussionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<MBDiscussion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBDiscussion>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the message boards discussions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MBDiscussion mbDiscussion : findAll()) {
			remove(mbDiscussion);
		}
	}

	/**
	 * Returns the number of message boards discussions.
	 *
	 * @return the number of message boards discussions
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MBDISCUSSION);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return MBDiscussionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the message boards discussion persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
			MBDiscussionModelImpl.FINDER_CACHE_ENABLED, MBDiscussionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
			MBDiscussionModelImpl.FINDER_CACHE_ENABLED, MBDiscussionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
			MBDiscussionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
			MBDiscussionModelImpl.FINDER_CACHE_ENABLED, MBDiscussionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
			MBDiscussionModelImpl.FINDER_CACHE_ENABLED, MBDiscussionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			MBDiscussionModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
			MBDiscussionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
			MBDiscussionModelImpl.FINDER_CACHE_ENABLED, MBDiscussionImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			MBDiscussionModelImpl.UUID_COLUMN_BITMASK |
			MBDiscussionModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
			MBDiscussionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
			MBDiscussionModelImpl.FINDER_CACHE_ENABLED, MBDiscussionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
			MBDiscussionModelImpl.FINDER_CACHE_ENABLED, MBDiscussionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			MBDiscussionModelImpl.UUID_COLUMN_BITMASK |
			MBDiscussionModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
			MBDiscussionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByClassNameId = new FinderPath(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
			MBDiscussionModelImpl.FINDER_CACHE_ENABLED, MBDiscussionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByClassNameId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByClassNameId = new FinderPath(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
			MBDiscussionModelImpl.FINDER_CACHE_ENABLED, MBDiscussionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByClassNameId",
			new String[] {Long.class.getName()},
			MBDiscussionModelImpl.CLASSNAMEID_COLUMN_BITMASK);

		_finderPathCountByClassNameId = new FinderPath(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
			MBDiscussionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByClassNameId",
			new String[] {Long.class.getName()});

		_finderPathFetchByThreadId = new FinderPath(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
			MBDiscussionModelImpl.FINDER_CACHE_ENABLED, MBDiscussionImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByThreadId",
			new String[] {Long.class.getName()},
			MBDiscussionModelImpl.THREADID_COLUMN_BITMASK);

		_finderPathCountByThreadId = new FinderPath(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
			MBDiscussionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByThreadId",
			new String[] {Long.class.getName()});

		_finderPathFetchByC_C = new FinderPath(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
			MBDiscussionModelImpl.FINDER_CACHE_ENABLED, MBDiscussionImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			MBDiscussionModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			MBDiscussionModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			MBDiscussionModelImpl.ENTITY_CACHE_ENABLED,
			MBDiscussionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(MBDiscussionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_MBDISCUSSION =
		"SELECT mbDiscussion FROM MBDiscussion mbDiscussion";

	private static final String _SQL_SELECT_MBDISCUSSION_WHERE_PKS_IN =
		"SELECT mbDiscussion FROM MBDiscussion mbDiscussion WHERE discussionId IN (";

	private static final String _SQL_SELECT_MBDISCUSSION_WHERE =
		"SELECT mbDiscussion FROM MBDiscussion mbDiscussion WHERE ";

	private static final String _SQL_COUNT_MBDISCUSSION =
		"SELECT COUNT(mbDiscussion) FROM MBDiscussion mbDiscussion";

	private static final String _SQL_COUNT_MBDISCUSSION_WHERE =
		"SELECT COUNT(mbDiscussion) FROM MBDiscussion mbDiscussion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "mbDiscussion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No MBDiscussion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No MBDiscussion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		MBDiscussionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

}