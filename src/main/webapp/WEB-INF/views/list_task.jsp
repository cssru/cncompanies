<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Список задач сотрудника <c:out
		value="${executor.fullName}" /></title>
<%@ include file="/WEB-INF/views/css.jsp"%>
</head>
<body>
	<c:if test="${executed}">
		<%@ include file="/WEB-INF/views/navbar_header_done_tasks.jsp"%>
	</c:if>
	<c:if test="${!executed}">
		<%@ include file="/WEB-INF/views/navbar_header_undone_tasks.jsp"%>
	</c:if>


	<div class="container">
		<c:if test="${!executed}">
			<h4>
				Список задач сотрудника
				<c:out value="${executor.fullName}" />
			</h4>
		</c:if>
		<c:if test="${executed}">
			<h4>
				Список выполненных задач сотрудника
				<c:out value="${executor.fullName}" />
			</h4>
		</c:if>

		<c:if test="${empty listTask}">
			<c:if test="${executed}">
				<p>У этого сотрудника нет выполненных задач.</p>
			</c:if>
			<c:if test="${!executed}">
				<p>У этого сотрудника нет задач.</p>
			</c:if>
		</c:if>

		<c:if test="${!empty listTask}">
			<table class="table table-hover">
				<thead>
					<tr>
						<th></th>
						<th>Содержание</th>
						<th>Комментарий</th>
						<th>От кого</th>
						<th>Срок исполнения</th>
						<c:if test="${executed}">
							<th>Выполнена</th>
						</c:if>
						<th>&nbsp;</th>
						<th>&nbsp;</th>
						<th>&nbsp;</th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listTask}" var="nextTask">
						<jsp:useBean id="now" class="java.util.Date" />
						<tr id="<c:out value="${nextTask.id}"/>">
							<td><c:if
									test="${nextTask.expires.time < now.time and !executed}">
									<span class="glyphicon glyphicon-exclamation-sign text-danger"></span>
								</c:if> <c:if
									test="${nextTask.expires.time > now.time and nextTask.expires.time - nextTask.alertTime < now.time and !executed}">
									<span class="glyphicon glyphicon-exclamation-sign text-warning"></span>
								</c:if></td>
							<c:if test="${!nextTask.readonly}">
								<td class="text-field-text" data-type="content">${nextTask.content}</td>
								<td class="text-area-text" data-type="comment">${nextTask.comment}</td>
							</c:if>

							<c:if test="${nextTask.readonly}">
								<td>${nextTask.content}</td>
								<td>${nextTask.comment}</td>
							</c:if>

							<td><c:if test="${nextTask.author eq nextTask.owner}">
					-
							</c:if> <c:if test="${nextTask.author ne nextTask.owner}">
					${nextTask.author.shortName}
					</c:if></td>
							<td class="date-element" data-millis="${nextTask.expires.time}"></td>
							<c:if test="${executed}">
								<td>Да ( <span class="date-element"
									data-millis="${nextTask.done.time}"></span>)
								</td>
							</c:if>
							<td><c:if test="${!executed}">
									<%-- /task.execute/{taskId}/{redirect}, if redirect == 0 -> my own tasks  --%>
									<c:if test="${!showMyTasks}">
										<a
											href="<c:url value="/task.execute/${nextTask.id}/${executor.id}" />"><button
												class="btn btn-success btn-sm">Выполнить</button></a>
									</c:if>

									<c:if test="${showMyTasks}">
										<a href="<c:url value="/task.execute/${nextTask.id}/0" />"><button
												class="btn btn-success btn-sm">Выполнить</button></a>
									</c:if>

								</c:if> <c:if test="${executed}">

									<c:if test="${!showMyTasks}">
										<%-- /task.undone/{taskId}/{redirect}, if redirect == 0 -> my own tasks --%>
										<a
											href="<c:url value="/task.undone/${nextTask.id}/${executor.id}" />"><button
												class="btn btn-warning btn-sm">Отменить выполнение</button></a>
									</c:if>

									<c:if test="${showMyTasks}">
										<a href="<c:url value="/task.undone/${nextTask.id}/0" />"><button
												class="btn btn-warning btn-sm">Отменить выполнение</button></a>
									</c:if>

								</c:if></td>
							<td><c:if test="${!nextTask.readonly}">

									<c:if test="${!showMyTasks}">
										<a
											href="<c:url value="/task.edit/${nextTask.id}/${executor.id}" />"><button
												class="btn btn-success btn-sm">Изменить</button></a>
									</c:if>

									<c:if test="${showMyTasks}">
										<a href="<c:url value="/task.edit/${nextTask.id}/0" />"><button
												class="btn btn-success btn-sm">Изменить</button></a>
									</c:if>

								</c:if></td>
							<td><c:if test="${!nextTask.readonly}">

									<c:if test="${!showMyTasks}">
										<button class="btn btn-danger btn-sm delete-control">Удалить</button>
									</c:if>

									<c:if test="${showMyTasks}">
										<button class="btn btn-danger btn-sm delete-control">Удалить</button>
									</c:if>

								</c:if></td>
							<td><c:if test="${nextTask.taskDone and !nextTask.readonly}">

									<c:if test="${!showMyTasks}">
										<a
											href="<c:url value="/task.archive/${nextTask.id}/1/${executor.id}" />"><button
												class="btn btn-info btn-sm">В архив</button></a>
									</c:if>

									<c:if test="${showMyTasks}">
										<a href="<c:url value="/task.archive/${nextTask.id}/1/0" />"><button
												class="btn btn-info btn-sm">В архив</button></a>
									</c:if>

								</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<c:if test="${!executed}">
			<c:url var="addUrl" value="/task.add/${executor.id}" />
			<a class="pull-left" href="${addUrl}"><button
					class="btn btn-success btn-sm">Добавить задачу</button></a>
		</c:if>
		<c:if test="${empty showMyTasks}">
			<sec:authorize access="hasRole('UNIT_MANAGER')">
				<c:url var="humanListUrl" value="/human.list/${executor.unit.id}" />
				<c:url var="unitListUrl" value="/unit.list" />
			</sec:authorize>
			<sec:authorize access="hasRole('COMPANY_MANAGER')">
				<c:url var="humanListUrl" value="/human.list/${executor.unit.id}" />
				<c:url var="unitListUrl"
					value="/unit.list/${executor.unit.company.id}" />
				<c:url var="companyListUrl" value="/company.list" />
			</sec:authorize>

			<sec:authorize
				access="hasRole('COMPANY_MANAGER') or hasRole('UNIT_MANAGER')">

				<div class="btn-group pull-right">
					<a href="${humanListUrl}"><button class="btn btn-info btn-sm">К
							списку сотрудников</button></a> <a href="${unitListUrl}"><button
							class="btn btn-info btn-sm">К списку подразделений</button></a>
					<sec:authorize access="hasRole('COMPANY_MANAGER')">
						<a href="${companyListUrl}"><button
								class="btn btn-info btn-sm">К списку компаний</button></a>
					</sec:authorize>
				</div>
			</sec:authorize>
		</c:if>
	</div>

	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>
	<%@ include file="/WEB-INF/views/script_list_task.jsp"%>
</body>
</html>