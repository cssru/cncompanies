<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Архив задач сотрудников</title>
<%@ include file="/WEB-INF/views/css.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/navbar_header_archive_slave_tasks.jsp"%>


	<div class="container">

		<h3>Архив задач сотрудников</h3>
		<c:if test="${empty listTask}">
			<p>Нет задач в архиве</p>
		</c:if>

		<c:if test="${!empty listTask}">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Исполнитель</th>
						<th>От кого</th>
						<th>Содержание</th>
						<th>Комментарий</th>
						<th>Срок исполнения</th>
						<th>Когда выполнена</th>
						<th>&nbsp;</th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listTask}" var="nextTask">
						<tr>
							<td>${nextTask.owner.shortName}</td>
							<td>${nextTask.author.shortName}</td>
							<td>${nextTask.content}</td>
							<td>${nextTask.comment}</td>
							<td class="date-element" data-millis="${nextTask.expires.time}"></td>
							<td><span class="date-element"
								data-millis="${nextTask.done.time}"></span></td>

							<td><a
								href="<c:url value="/task.archive/${nextTask.id}/0/slave" />"><button
										class="btn btn-success btn-sm">Извлечь из архива</button></a></td>
							<td><c:if test="${!nextTask.readonly}">
									<a href="<c:url value="/task.delete/${nextTask.id}/archive" />"><button
											class="btn btn-danger btn-sm">Удалить</button></a>
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
	<%@ include file="/WEB-INF/views/script_delete_confirm.jsp"%>
	<script>
		$(function() {
			$(document).ready(
					function() {
						$(".date-element").each(
								function(index) {
									var millis = $(this).data("millis");
									var date = new Date();
									date.setTime(millis);
									var dateString = addZero(date.getDate())
											+ "-"
											+ addZero(date.getMonth() + 1)
											+ "-" + addZero(date.getFullYear())
											+ " " + addZero(date.getHours())
											+ ":" + addZero(date.getMinutes());
									$(this).html(dateString);
								});
					});
		});

		function addZero(value) {
			if (value < 10)
				return "0" + value;
			return "" + value;
		}
	</script>

</body>
</html>