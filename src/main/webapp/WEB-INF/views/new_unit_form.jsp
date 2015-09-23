<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Добавление нового подразделения в компании <c:out
		value="${company.name}" /></title>
<%@ include file="/WEB-INF/views/css.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/navbar_header.jsp"%>

	<div class="container">

		<div class="container">
			<div class="col-md-6 col-md-offset-3">
				<h4 class="form-signin-heading">
					Новое подразделение в компании "
					<c:out value="${company.name}" />
					"
				</h4>
				<c:url var="addUrl" value="/unit.add/${company.id}" />
				<form:form role="form" action="${addUrl}" method="POST"
					commandName="unitProxy">
					<div class="form-group">
						<label for="name" class="control-label">Название
							подразделения:</label>
						<form:input id="name" class="form-control" path="name" />
					</div>
					<div class="form-group">
						<label for="description" class="control-label">Описание:</label>
						<form:textarea id="description" class="form-control"
							path="description" />
					</div>
					<div class="form-group">
						<label for="owner" class="control-label">Руководитель подразделения:</label>
						<form:select id="owner" path="ownerId" items="${humanList}" />
					</div>
					<input type="submit" value="Добавить"
						class="btn btn-success pull-right" />
				</form:form>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>
</body>
</html>