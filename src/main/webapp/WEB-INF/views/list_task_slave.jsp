<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Список задач сотрудников</title>
<%@ include file="/WEB-INF/views/css.jsp"%>
</head>
<body>

	<c:if test="${!executed}">
		<%@ include file="/WEB-INF/views/navbar_header_slave_tasks.jsp"%>
	</c:if>
	<c:if test="${executed}">
		<%@ include file="/WEB-INF/views/navbar_header_slave_done_tasks.jsp"%>
	</c:if>


	<div class="container">

		<c:if test="${empty listTask}">
			<p>Нет задач</p>
		</c:if>

		<c:if test="${!empty listTask}">
			<c:if test="${!executed}">
				<h4>Список не выполненных задач сотрудников</h4>
			</c:if>
			<c:if test="${executed}">
				<h4>Список выполненных задач сотрудников</h4>
			</c:if>

			<table class="table table-hover">
				<thead>
					<tr>
						<th></th>
						<th>Исполнитель</th>
						<th>От кого</th>
						<th>Содержание</th>
						<th>Комментарий</th>
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
							<td>${nextTask.owner.shortName}</td>
							<td>${nextTask.author.shortName}</td>
							<td class="text-field-text" data-type="content">${nextTask.content}</td>
							<td class="text-area-text" data-type="comment">${nextTask.comment}</td>
							<td class="date-element" data-millis="${nextTask.expires.time}"></td>
							<c:if test="${executed}">
								<td>Да ( <span class="date-element"
									data-millis="${nextTask.done.time}"></span>)
								</td>
							</c:if>

							<td><c:if test="${!executed}">
									<a href="<c:url value="/task.execute/${nextTask.id}/slave" />"><button
											class="btn btn-success btn-sm">Выполнить</button></a>
								</c:if> <c:if test="${executed}">
									<a href="<c:url value="/task.undone/${nextTask.id}/slave" />"><button
											class="btn btn-warning btn-sm">Отменить выполнение</button></a>
								</c:if></td>
							<td><c:if test="${!nextTask.readonly}">
									<c:if test="${executed}">
										<a
											href="<c:url value="/task.edit/${nextTask.id}/slave-done" />"><button
												class="btn btn-success btn-sm">Изменить</button></a>
									</c:if>

									<c:if test="${!executed}">
										<a href="<c:url value="/task.edit/${nextTask.id}/slave" />"><button
												class="btn btn-success btn-sm">Изменить</button></a>
									</c:if>
								</c:if></td>
							<td><c:if test="${!nextTask.readonly}">
									<a href="<c:url value="/task.delete/${nextTask.id}/slave" />"><button
											class="btn btn-danger btn-sm delete-control">Удалить</button></a>
								</c:if></td>
							<td><c:if test="${executed}">
									<a
										href="<c:url value="/task.archive/${nextTask.id}/1/slave-done" />"><button
											class="btn btn-info btn-sm">В архив</button></a>
								</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>

	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>
	<%@ include file="/WEB-INF/views/script_list_task.jsp"%>

</body>
</html>