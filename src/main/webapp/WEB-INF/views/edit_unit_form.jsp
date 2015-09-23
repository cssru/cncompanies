<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Изменение подразделения компании <c:out
		value="${company.name}" /></title>
<%@ include file="/WEB-INF/views/css.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/navbar_header.jsp"%>

	<div class="container">

		<c:url var="updateUrl" value="/unit.edit" />
		<form:form action="${updateUrl}" method="POST" commandName="unitProxy">
			<table>
				<tr>
					<td>Название:</td>
					<td><form:input path="name" /></td>
				</tr>
				<tr>
					<td>Описание:</td>
					<td><form:textarea path="description" /></td>
				</tr>
				<tr>
					<td colspan="2"><p>Руководитель подразделения:</p> <form:select
							path="ownerId" items="${humanList}" default="${ownerId}" /></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="Сохранить"
						class="btn btn-success" /></td>
				</tr>
			</table>
			<form:hidden path="id" />
			<form:hidden path="companyId" />
		</form:form>
	</div>
	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>
</body>
</html>