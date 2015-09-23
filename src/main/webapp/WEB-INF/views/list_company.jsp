<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Список компаний</title>
<%@ include file="/WEB-INF/views/css.jsp"%>
<%@ include file="/WEB-INF/views/navbar_header_companies.jsp"%>
</head>
<body>
	<div class="container">
		<h4>
			Список компаний (владелец -
			<c:out value="${owner.human.shortName}" />
			)
		</h4>
		<c:if test="${empty listCompany}">
			<p>Вы не создали ни одной компании.</p>
		</c:if>
		<c:if test="${!empty listCompany}">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Название</th>
						<th>Описание</th>
						<th>&nbsp;</th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listCompany}" var="nextCompany">
						<tr>
							<td><a href="<c:url value="/unit.list/${nextCompany.id}" />">${nextCompany.name}</a></td>
							<td>${nextCompany.description}</td>
							<td><a
								href="<c:url value="/company.edit/${nextCompany.id}" />"><button
										class="btn btn-success btn-sm">Изменить</button></a></td>
							<td><a
								href="<c:url value="/company.delete/${nextCompany.id}" />"><button
										class="btn btn-danger btn-sm">Удалить</button></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>

		<p>
			<a class="btn btn-success btn-sm"
				href="<c:url value="/company.add" />" role="button">Добавить
				компанию</a>
		</p>
	</div>

	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>
	<%@ include file="/WEB-INF/views/script_delete_confirm.jsp"%>

</body>
</html>