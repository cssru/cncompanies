<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Список сотрудников подразделения <c:out
		value="${unit.name}" /></title>
<%@ include file="/WEB-INF/views/css.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/navbar_header_list_human.jsp"%>

	<div class="container">

		<c:if test="${(!empty human) and (human.id > 0)}">
Сотрудник успешно добавлен.<br>
		</c:if>

		<c:if test="${empty listHuman}">
			<p>В этом подразделении нет сотрудников.</p>
		</c:if>

		<c:if test="${!empty listHuman}">
			<h4>
				Список сотрудников подразделения "
				<c:out value="${unit.name}" />
				"
			</h4>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Фамилия</th>
						<th>Имя</th>
						<th>Отчество</th>
						<th align="center">Задачи</th>
						<th>&nbsp;</th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listHuman}" var="nextHumanWrapper">
						<tr>
							<td>${nextHumanWrapper.human.surname}</td>
							<td>${nextHumanWrapper.human.name}</td>
							<td>${nextHumanWrapper.human.lastName}</td>

							<td align="center"><div>
									<div class="stat-normal">
										<a
											href="<c:url value="/task.list/${nextHumanWrapper.human.id}" />">
											<c:out value="${nextHumanWrapper.normalTasksCount}" />
										</a>
									</div>
									<div class="stat-expired">
										<a
											href="<c:url value="/task.list/${nextHumanWrapper.human.id}" />">
											<c:out value="${nextHumanWrapper.expiredTasksCount}" />
										</a>
									</div>
									<div class="stat-nearest">
										<a
											href="<c:url value="/task.list/${nextHumanWrapper.human.id}" />">
											<c:out value="${nextHumanWrapper.nearestTasksCount}" />
										</a>
									</div>
									<div class="stat-done">
										<a
											href="<c:url value="/task.list/done/${nextHumanWrapper.human.id}" />">
											<c:out value="${nextHumanWrapper.doneTasksCount}" />
										</a>
									</div>
								</div></td>

							<td><a
								href="<c:url value="/human.edit/${nextHumanWrapper.human.id}" />"><button
										class="btn btn-success btn-sm">Изменить</button></a></td>
							<td><a
								href="<c:url value="/human.delete/${nextHumanWrapper.human.id}" />">
									<button class="btn btn-danger btn-sm">Удалить</button>
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<c:url var="addUrl" value="/human.add/${unit.id}" />
		<sec:authorize access="hasRole('UNIT_MANAGER')">
			<c:url var="unitListUrl" value="/unit.list" />
		</sec:authorize>
		<sec:authorize access="hasRole('COMPANY_MANAGER')">
			<c:url var="unitListUrl" value="/unit.list/${unit.company.id}" />
		</sec:authorize>
		<a href="${addUrl}">
			<button class="btn btn-success btn-sm pull-left">Добавить
				сотрудника</button>
		</a>
		<div class="btn-group pull-right">
			<a href="${unitListUrl}">
				<button class="btn btn-info btn-sm">К списку подразделений</button>
			</a>
			<sec:authorize access="hasRole('COMPANY_MANAGER')">
				<c:url var="companyListUrl" value="/company.list" />
				<a href="${companyListUrl}">
					<button class="btn btn-info btn-sm">К списку компаний</button>
				</a>
			</sec:authorize>
		</div>
	</div>

	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>
	<%@ include file="/WEB-INF/views/script_delete_confirm.jsp"%>

</body>
</html>