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

package com.liferay.portal.service.base;

import com.liferay.counter.kernel.service.persistence.CounterPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.service.TeamService;
import com.liferay.portal.kernel.service.persistence.GroupFinder;
import com.liferay.portal.kernel.service.persistence.GroupPersistence;
import com.liferay.portal.kernel.service.persistence.RoleFinder;
import com.liferay.portal.kernel.service.persistence.RolePersistence;
import com.liferay.portal.kernel.service.persistence.TeamFinder;
import com.liferay.portal.kernel.service.persistence.TeamPersistence;
import com.liferay.portal.kernel.service.persistence.UserFinder;
import com.liferay.portal.kernel.service.persistence.UserGroupFinder;
import com.liferay.portal.kernel.service.persistence.UserGroupPersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the team remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.service.impl.TeamServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.impl.TeamServiceImpl
 * @generated
 */
public abstract class TeamServiceBaseImpl
	extends BaseServiceImpl implements IdentifiableOSGiService, TeamService {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>TeamService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.portal.kernel.service.TeamServiceUtil</code>.
	 */

	/**
	 * Returns the team local service.
	 *
	 * @return the team local service
	 */
	public com.liferay.portal.kernel.service.TeamLocalService
		getTeamLocalService() {

		return teamLocalService;
	}

	/**
	 * Sets the team local service.
	 *
	 * @param teamLocalService the team local service
	 */
	public void setTeamLocalService(
		com.liferay.portal.kernel.service.TeamLocalService teamLocalService) {

		this.teamLocalService = teamLocalService;
	}

	/**
	 * Returns the team remote service.
	 *
	 * @return the team remote service
	 */
	public TeamService getTeamService() {
		return teamService;
	}

	/**
	 * Sets the team remote service.
	 *
	 * @param teamService the team remote service
	 */
	public void setTeamService(TeamService teamService) {
		this.teamService = teamService;
	}

	/**
	 * Returns the team persistence.
	 *
	 * @return the team persistence
	 */
	public TeamPersistence getTeamPersistence() {
		return teamPersistence;
	}

	/**
	 * Sets the team persistence.
	 *
	 * @param teamPersistence the team persistence
	 */
	public void setTeamPersistence(TeamPersistence teamPersistence) {
		this.teamPersistence = teamPersistence;
	}

	/**
	 * Returns the team finder.
	 *
	 * @return the team finder
	 */
	public TeamFinder getTeamFinder() {
		return teamFinder;
	}

	/**
	 * Sets the team finder.
	 *
	 * @param teamFinder the team finder
	 */
	public void setTeamFinder(TeamFinder teamFinder) {
		this.teamFinder = teamFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService
		getCounterLocalService() {

		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService
			counterLocalService) {

		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the counter persistence.
	 *
	 * @return the counter persistence
	 */
	public CounterPersistence getCounterPersistence() {
		return counterPersistence;
	}

	/**
	 * Sets the counter persistence.
	 *
	 * @param counterPersistence the counter persistence
	 */
	public void setCounterPersistence(CounterPersistence counterPersistence) {
		this.counterPersistence = counterPersistence;
	}

	/**
	 * Returns the group local service.
	 *
	 * @return the group local service
	 */
	public com.liferay.portal.kernel.service.GroupLocalService
		getGroupLocalService() {

		return groupLocalService;
	}

	/**
	 * Sets the group local service.
	 *
	 * @param groupLocalService the group local service
	 */
	public void setGroupLocalService(
		com.liferay.portal.kernel.service.GroupLocalService groupLocalService) {

		this.groupLocalService = groupLocalService;
	}

	/**
	 * Returns the group remote service.
	 *
	 * @return the group remote service
	 */
	public com.liferay.portal.kernel.service.GroupService getGroupService() {
		return groupService;
	}

	/**
	 * Sets the group remote service.
	 *
	 * @param groupService the group remote service
	 */
	public void setGroupService(
		com.liferay.portal.kernel.service.GroupService groupService) {

		this.groupService = groupService;
	}

	/**
	 * Returns the group persistence.
	 *
	 * @return the group persistence
	 */
	public GroupPersistence getGroupPersistence() {
		return groupPersistence;
	}

	/**
	 * Sets the group persistence.
	 *
	 * @param groupPersistence the group persistence
	 */
	public void setGroupPersistence(GroupPersistence groupPersistence) {
		this.groupPersistence = groupPersistence;
	}

	/**
	 * Returns the group finder.
	 *
	 * @return the group finder
	 */
	public GroupFinder getGroupFinder() {
		return groupFinder;
	}

	/**
	 * Sets the group finder.
	 *
	 * @param groupFinder the group finder
	 */
	public void setGroupFinder(GroupFinder groupFinder) {
		this.groupFinder = groupFinder;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService
		getResourceLocalService() {

		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService
			resourceLocalService) {

		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the role local service.
	 *
	 * @return the role local service
	 */
	public com.liferay.portal.kernel.service.RoleLocalService
		getRoleLocalService() {

		return roleLocalService;
	}

	/**
	 * Sets the role local service.
	 *
	 * @param roleLocalService the role local service
	 */
	public void setRoleLocalService(
		com.liferay.portal.kernel.service.RoleLocalService roleLocalService) {

		this.roleLocalService = roleLocalService;
	}

	/**
	 * Returns the role remote service.
	 *
	 * @return the role remote service
	 */
	public com.liferay.portal.kernel.service.RoleService getRoleService() {
		return roleService;
	}

	/**
	 * Sets the role remote service.
	 *
	 * @param roleService the role remote service
	 */
	public void setRoleService(
		com.liferay.portal.kernel.service.RoleService roleService) {

		this.roleService = roleService;
	}

	/**
	 * Returns the role persistence.
	 *
	 * @return the role persistence
	 */
	public RolePersistence getRolePersistence() {
		return rolePersistence;
	}

	/**
	 * Sets the role persistence.
	 *
	 * @param rolePersistence the role persistence
	 */
	public void setRolePersistence(RolePersistence rolePersistence) {
		this.rolePersistence = rolePersistence;
	}

	/**
	 * Returns the role finder.
	 *
	 * @return the role finder
	 */
	public RoleFinder getRoleFinder() {
		return roleFinder;
	}

	/**
	 * Sets the role finder.
	 *
	 * @param roleFinder the role finder
	 */
	public void setRoleFinder(RoleFinder roleFinder) {
		this.roleFinder = roleFinder;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService
		getUserLocalService() {

		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {

		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.kernel.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.kernel.service.UserService userService) {

		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the user finder.
	 *
	 * @return the user finder
	 */
	public UserFinder getUserFinder() {
		return userFinder;
	}

	/**
	 * Sets the user finder.
	 *
	 * @param userFinder the user finder
	 */
	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	/**
	 * Returns the user group local service.
	 *
	 * @return the user group local service
	 */
	public com.liferay.portal.kernel.service.UserGroupLocalService
		getUserGroupLocalService() {

		return userGroupLocalService;
	}

	/**
	 * Sets the user group local service.
	 *
	 * @param userGroupLocalService the user group local service
	 */
	public void setUserGroupLocalService(
		com.liferay.portal.kernel.service.UserGroupLocalService
			userGroupLocalService) {

		this.userGroupLocalService = userGroupLocalService;
	}

	/**
	 * Returns the user group remote service.
	 *
	 * @return the user group remote service
	 */
	public com.liferay.portal.kernel.service.UserGroupService
		getUserGroupService() {

		return userGroupService;
	}

	/**
	 * Sets the user group remote service.
	 *
	 * @param userGroupService the user group remote service
	 */
	public void setUserGroupService(
		com.liferay.portal.kernel.service.UserGroupService userGroupService) {

		this.userGroupService = userGroupService;
	}

	/**
	 * Returns the user group persistence.
	 *
	 * @return the user group persistence
	 */
	public UserGroupPersistence getUserGroupPersistence() {
		return userGroupPersistence;
	}

	/**
	 * Sets the user group persistence.
	 *
	 * @param userGroupPersistence the user group persistence
	 */
	public void setUserGroupPersistence(
		UserGroupPersistence userGroupPersistence) {

		this.userGroupPersistence = userGroupPersistence;
	}

	/**
	 * Returns the user group finder.
	 *
	 * @return the user group finder
	 */
	public UserGroupFinder getUserGroupFinder() {
		return userGroupFinder;
	}

	/**
	 * Sets the user group finder.
	 *
	 * @param userGroupFinder the user group finder
	 */
	public void setUserGroupFinder(UserGroupFinder userGroupFinder) {
		this.userGroupFinder = userGroupFinder;
	}

	public void afterPropertiesSet() {
	}

	public void destroy() {
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return TeamService.class.getName();
	}

	protected Class<?> getModelClass() {
		return Team.class;
	}

	protected String getModelClassName() {
		return Team.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = teamPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(
		type = com.liferay.portal.kernel.service.TeamLocalService.class
	)
	protected com.liferay.portal.kernel.service.TeamLocalService
		teamLocalService;

	@BeanReference(type = TeamService.class)
	protected TeamService teamService;

	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;

	@BeanReference(type = TeamFinder.class)
	protected TeamFinder teamFinder;

	@BeanReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@BeanReference(type = CounterPersistence.class)
	protected CounterPersistence counterPersistence;

	@BeanReference(
		type = com.liferay.portal.kernel.service.GroupLocalService.class
	)
	protected com.liferay.portal.kernel.service.GroupLocalService
		groupLocalService;

	@BeanReference(type = com.liferay.portal.kernel.service.GroupService.class)
	protected com.liferay.portal.kernel.service.GroupService groupService;

	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;

	@BeanReference(type = GroupFinder.class)
	protected GroupFinder groupFinder;

	@BeanReference(
		type = com.liferay.portal.kernel.service.ResourceLocalService.class
	)
	protected com.liferay.portal.kernel.service.ResourceLocalService
		resourceLocalService;

	@BeanReference(
		type = com.liferay.portal.kernel.service.RoleLocalService.class
	)
	protected com.liferay.portal.kernel.service.RoleLocalService
		roleLocalService;

	@BeanReference(type = com.liferay.portal.kernel.service.RoleService.class)
	protected com.liferay.portal.kernel.service.RoleService roleService;

	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;

	@BeanReference(type = RoleFinder.class)
	protected RoleFinder roleFinder;

	@BeanReference(
		type = com.liferay.portal.kernel.service.UserLocalService.class
	)
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

	@BeanReference(type = com.liferay.portal.kernel.service.UserService.class)
	protected com.liferay.portal.kernel.service.UserService userService;

	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;

	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;

	@BeanReference(
		type = com.liferay.portal.kernel.service.UserGroupLocalService.class
	)
	protected com.liferay.portal.kernel.service.UserGroupLocalService
		userGroupLocalService;

	@BeanReference(
		type = com.liferay.portal.kernel.service.UserGroupService.class
	)
	protected com.liferay.portal.kernel.service.UserGroupService
		userGroupService;

	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;

	@BeanReference(type = UserGroupFinder.class)
	protected UserGroupFinder userGroupFinder;

}