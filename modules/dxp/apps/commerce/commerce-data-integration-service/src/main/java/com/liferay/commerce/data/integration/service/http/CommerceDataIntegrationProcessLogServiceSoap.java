/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.data.integration.service.http;

import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessLogServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceDataIntegrationProcessLogServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcessLogSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcessLog</code>, that is translated to a
 * <code>com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcessLogSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcessLogServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceDataIntegrationProcessLogServiceSoap {

	public static com.liferay.commerce.data.integration.model.
		CommerceDataIntegrationProcessLogSoap
				addCommerceDataIntegrationProcessLog(
					long userId, long commerceDataIntegrationProcessId,
					String error, String output, int status,
					java.util.Date startDate, java.util.Date endDate)
			throws RemoteException {

		try {
			com.liferay.commerce.data.integration.model.
				CommerceDataIntegrationProcessLog returnValue =
					CommerceDataIntegrationProcessLogServiceUtil.
						addCommerceDataIntegrationProcessLog(
							userId, commerceDataIntegrationProcessId, error,
							output, status, startDate, endDate);

			return com.liferay.commerce.data.integration.model.
				CommerceDataIntegrationProcessLogSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceDataIntegrationProcessLog(
			long cDataIntegrationProcessLogId)
		throws RemoteException {

		try {
			CommerceDataIntegrationProcessLogServiceUtil.
				deleteCommerceDataIntegrationProcessLog(
					cDataIntegrationProcessLogId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.data.integration.model.
		CommerceDataIntegrationProcessLogSoap
				getCommerceDataIntegrationProcessLog(
					long cDataIntegrationProcessLogId)
			throws RemoteException {

		try {
			com.liferay.commerce.data.integration.model.
				CommerceDataIntegrationProcessLog returnValue =
					CommerceDataIntegrationProcessLogServiceUtil.
						getCommerceDataIntegrationProcessLog(
							cDataIntegrationProcessLogId);

			return com.liferay.commerce.data.integration.model.
				CommerceDataIntegrationProcessLogSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.data.integration.model.
		CommerceDataIntegrationProcessLogSoap[]
				getCommerceDataIntegrationProcessLogs(
					long commerceDataIntegrationProcessId, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.data.integration.model.
					CommerceDataIntegrationProcessLog> returnValue =
						CommerceDataIntegrationProcessLogServiceUtil.
							getCommerceDataIntegrationProcessLogs(
								commerceDataIntegrationProcessId, start, end);

			return com.liferay.commerce.data.integration.model.
				CommerceDataIntegrationProcessLogSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceDataIntegrationProcessLogsCount(
			long commerceDataIntegrationProcessId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceDataIntegrationProcessLogServiceUtil.
					getCommerceDataIntegrationProcessLogsCount(
						commerceDataIntegrationProcessId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.data.integration.model.
		CommerceDataIntegrationProcessLogSoap
				updateCommerceDataIntegrationProcessLog(
					long cDataIntegrationProcessLogId, String error,
					String output, int status, java.util.Date endDate)
			throws RemoteException {

		try {
			com.liferay.commerce.data.integration.model.
				CommerceDataIntegrationProcessLog returnValue =
					CommerceDataIntegrationProcessLogServiceUtil.
						updateCommerceDataIntegrationProcessLog(
							cDataIntegrationProcessLogId, error, output, status,
							endDate);

			return com.liferay.commerce.data.integration.model.
				CommerceDataIntegrationProcessLogSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceDataIntegrationProcessLogServiceSoap.class);

}