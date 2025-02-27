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

package com.liferay.portal.tools.service.builder.test.service.base;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry;
import com.liferay.portal.tools.service.builder.test.service.DSLQueryEntryLocalService;
import com.liferay.portal.tools.service.builder.test.service.persistence.DSLQueryEntryPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the dsl query entry local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.tools.service.builder.test.service.impl.DSLQueryEntryLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.tools.service.builder.test.service.impl.DSLQueryEntryLocalServiceImpl
 * @generated
 */
public abstract class DSLQueryEntryLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements DSLQueryEntryLocalService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>DSLQueryEntryLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.portal.tools.service.builder.test.service.DSLQueryEntryLocalServiceUtil</code>.
	 */

	/**
	 * Adds the dsl query entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DSLQueryEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dslQueryEntry the dsl query entry
	 * @return the dsl query entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DSLQueryEntry addDSLQueryEntry(DSLQueryEntry dslQueryEntry) {
		dslQueryEntry.setNew(true);

		return dslQueryEntryPersistence.update(dslQueryEntry);
	}

	/**
	 * Creates a new dsl query entry with the primary key. Does not add the dsl query entry to the database.
	 *
	 * @param dslQueryEntryId the primary key for the new dsl query entry
	 * @return the new dsl query entry
	 */
	@Override
	@Transactional(enabled = false)
	public DSLQueryEntry createDSLQueryEntry(long dslQueryEntryId) {
		return dslQueryEntryPersistence.create(dslQueryEntryId);
	}

	/**
	 * Deletes the dsl query entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DSLQueryEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dslQueryEntryId the primary key of the dsl query entry
	 * @return the dsl query entry that was removed
	 * @throws PortalException if a dsl query entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DSLQueryEntry deleteDSLQueryEntry(long dslQueryEntryId)
		throws PortalException {

		return dslQueryEntryPersistence.remove(dslQueryEntryId);
	}

	/**
	 * Deletes the dsl query entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DSLQueryEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dslQueryEntry the dsl query entry
	 * @return the dsl query entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DSLQueryEntry deleteDSLQueryEntry(DSLQueryEntry dslQueryEntry) {
		return dslQueryEntryPersistence.remove(dslQueryEntry);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return dslQueryEntryPersistence.dslQuery(dslQuery);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			DSLQueryEntry.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return dslQueryEntryPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DSLQueryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return dslQueryEntryPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DSLQueryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return dslQueryEntryPersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return dslQueryEntryPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return dslQueryEntryPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public DSLQueryEntry fetchDSLQueryEntry(long dslQueryEntryId) {
		return dslQueryEntryPersistence.fetchByPrimaryKey(dslQueryEntryId);
	}

	/**
	 * Returns the dsl query entry with the primary key.
	 *
	 * @param dslQueryEntryId the primary key of the dsl query entry
	 * @return the dsl query entry
	 * @throws PortalException if a dsl query entry with the primary key could not be found
	 */
	@Override
	public DSLQueryEntry getDSLQueryEntry(long dslQueryEntryId)
		throws PortalException {

		return dslQueryEntryPersistence.findByPrimaryKey(dslQueryEntryId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(dslQueryEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DSLQueryEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("dslQueryEntryId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			dslQueryEntryLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(DSLQueryEntry.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"dslQueryEntryId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(dslQueryEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DSLQueryEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("dslQueryEntryId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return dslQueryEntryPersistence.create(
			((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return dslQueryEntryLocalService.deleteDSLQueryEntry(
			(DSLQueryEntry)persistedModel);
	}

	@Override
	public BasePersistence<DSLQueryEntry> getBasePersistence() {
		return dslQueryEntryPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return dslQueryEntryPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the dsl query entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DSLQueryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dsl query entries
	 * @param end the upper bound of the range of dsl query entries (not inclusive)
	 * @return the range of dsl query entries
	 */
	@Override
	public List<DSLQueryEntry> getDSLQueryEntries(int start, int end) {
		return dslQueryEntryPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of dsl query entries.
	 *
	 * @return the number of dsl query entries
	 */
	@Override
	public int getDSLQueryEntriesCount() {
		return dslQueryEntryPersistence.countAll();
	}

	/**
	 * Updates the dsl query entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DSLQueryEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dslQueryEntry the dsl query entry
	 * @return the dsl query entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DSLQueryEntry updateDSLQueryEntry(DSLQueryEntry dslQueryEntry) {
		return dslQueryEntryPersistence.update(dslQueryEntry);
	}

	/**
	 * Returns the dsl query entry local service.
	 *
	 * @return the dsl query entry local service
	 */
	public DSLQueryEntryLocalService getDSLQueryEntryLocalService() {
		return dslQueryEntryLocalService;
	}

	/**
	 * Sets the dsl query entry local service.
	 *
	 * @param dslQueryEntryLocalService the dsl query entry local service
	 */
	public void setDSLQueryEntryLocalService(
		DSLQueryEntryLocalService dslQueryEntryLocalService) {

		this.dslQueryEntryLocalService = dslQueryEntryLocalService;
	}

	/**
	 * Returns the dsl query entry persistence.
	 *
	 * @return the dsl query entry persistence
	 */
	public DSLQueryEntryPersistence getDSLQueryEntryPersistence() {
		return dslQueryEntryPersistence;
	}

	/**
	 * Sets the dsl query entry persistence.
	 *
	 * @param dslQueryEntryPersistence the dsl query entry persistence
	 */
	public void setDSLQueryEntryPersistence(
		DSLQueryEntryPersistence dslQueryEntryPersistence) {

		this.dslQueryEntryPersistence = dslQueryEntryPersistence;
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

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register(
			"com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry",
			dslQueryEntryLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return DSLQueryEntryLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return DSLQueryEntry.class;
	}

	protected String getModelClassName() {
		return DSLQueryEntry.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = dslQueryEntryPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@BeanReference(type = DSLQueryEntryLocalService.class)
	protected DSLQueryEntryLocalService dslQueryEntryLocalService;

	@BeanReference(type = DSLQueryEntryPersistence.class)
	protected DSLQueryEntryPersistence dslQueryEntryPersistence;

	@ServiceReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry
		persistedModelLocalServiceRegistry;

}