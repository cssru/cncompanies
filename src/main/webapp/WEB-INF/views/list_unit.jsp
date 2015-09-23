<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Список подразделений компании <c:out
		value="${company.name}" /></title>
<%@ include file="/WEB-INF/views/css.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/navbar_header_units.jsp"%>

	<div class="container">
		<h4>
			Список подразделений компании
			<c:out value="${company.name}" />
		</h4>
		<c:if test="${empty listUnit}">
			<p>
				<sec:authorize access="hasRole('COMPANY_MANAGER')">
				Вы не создали ни одного подразделения в компании
				<c:out value="${company.name}" />
				</sec:authorize>

				<sec:authorize access="hasRole('UNIT_MANAGER')">
				У вас нет подчиненных подразделений
				</sec:authorize>
			</p>
		</c:if>
		<c:if test="${!empty listUnit}">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Название</th>
						<th>Описание</th>
						<sec:authorize access="hasRole('COMPANY_MANAGER')">
							<th>Руководитель</th>
							<th>&nbsp;</th>
							<th>&nbsp;</th>
						</sec:authorize>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listUnit}" var="nextUnit">
						<tr>
							<sec:authorize access="hasRole('COMPANY_MANAGER')">
								<td><a href="<c:url value="/human.list/${nextUnit.id}" />">${nextUnit.name}</a></td>
								<td>${nextUnit.description}</td>
								<td>${nextUnit.owner.shortName}</td>
								<td><a href="<c:url value="/unit.edit/${nextUnit.id}" />"><button
											class="btn btn-success btn-sm">Изменить</button></a></td>
								<td><a href="<c:url value="/unit.delete/${nextUnit.id}" />"><button
											class="btn btn-danger btn-sm">Удалить</button></a></td>
							</sec:authorize>
							<sec:authorize access="hasRole('UNIT_MANAGER')">

								<td><a href="<c:url value="/human.list/${nextUnit.id}" />">${nextUnit.name}</a></td>
								<td>${nextUnit.description}</td>
							</sec:authorize>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</c:if>
		<sec:authorize access="hasRole('COMPANY_MANAGER')">
			<a href="<c:url value="/unit.add/${company.id}" />"><button
					class="btn btn-success btn-sm">Добавить подразделение</button></a>
			<div class="btn-group pull-right">
				<c:url var="companyListUrl" value="/company.list" />
				<a href="${companyListUrl}"><button class="btn btn-info btn-sm">К
						списку компаний</button></a>
			</div>
		</sec:authorize>
	</div>

	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>
	<%@ include file="/WEB-INF/views/script_delete_confirm.jsp"%>

</body>
</html>