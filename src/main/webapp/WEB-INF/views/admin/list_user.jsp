<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Список зарегистрированных пользователей</title>
<%@ include file="/WEB-INF/views/css.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/navbar_header.jsp"%>

	<div class="container">
		<c:if test="${empty listUser}">
			<p>Нет зарегистрированных пользователей.</p>
		</c:if>

		<c:if test="${!empty listUser}">
			<h4>Список зарегистрированных пользователей</h4>
			<table class="table">
				<tr>
					<th>Логин</th>
					<th>Фамилия</th>
					<th>Имя</th>
					<th>Отчество</th>
					<th>Тариф</th>
					<th>Оплачен до</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
				</tr>
				<c:forEach items="${listUser}" var="nextUser">
					<tr>
						<td>${nextUser.login}</td>
						<td>${nextUser.human.surname}</td>
						<td>${nextUser.human.name}</td>
						<td>${nextUser.human.lastName}</td>
						<td><a
							href="<c:url value="/admin/user.change_tarif/${nextUser.id}" />">${nextUser.tarif.tarifName}</a></td>
						<td><fmt:formatDate pattern="dd-MM-YYYY HH:mm"
								value="${nextUser.paidTill}" /></td>
						<td><a
							href="<c:url value="/admin/user.change_password/${nextUser.id}" />">
								<button class="btn btn-success btn-sm">Изменить пароль</button>
						</a></td>
						<td><c:if test="${!nextUser.tarif.free}">
								<a href="<c:url value="/admin/user.pay/${nextUser.id}" />"><button
										class="btn btn-warning btn-sm">Оплата</button></a>
							</c:if></td>
						<td><c:if test="${nextUser.locked}">
								<a href="<c:url value="/admin/user.lock/${nextUser.id}/0" />"><button
										class="btn btn-warning btn-sm">Разблокировать</button></a>
							</c:if> <c:if test="${!nextUser.locked}">
								<a href="<c:url value="/admin/user.lock/${nextUser.id}/1" />"><button
										class="btn btn-warning btn-sm">Заблокировать</button></a>
							</c:if></td>
						<td><a
							href="<c:url value="/admin/user.delete/${nextUser.id}" />">
								<button class="btn btn-danger btn-sm">Удалить</button>
						</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>

	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>
	<%@ include file="/WEB-INF/views/script_delete_confirm.jsp"%>

</body>
</html>