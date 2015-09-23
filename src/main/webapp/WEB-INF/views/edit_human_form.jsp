<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Редактирование данных сотрудника</title>
<%@ include file="/WEB-INF/views/css.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/navbar_header.jsp"%>
	<div class="container">
		<div class="col-md-6 col-md-offset-3">
			<h4 class="form-signin-heading">
				Изменение данных пользователя "
				<c:out value="${humanProxy.login}" />
				"
			</h4>
			<c:url var="updateUrl" value="/human.edit" />
			<form:form role="form" action="${updateUrl}" method="POST"
				commandName="humanProxy">
				<div class="form-group">
					<label for="surname" class="control-label">Фамилия:</label>
					<form:input id="surname" path="surname" class="form-control" />
				</div>
				<div class="form-group">
					<label for="name" class="control-label">Имя:</label>
					<form:input id="name" path="name" class="form-control" />
				</div>
				<div class="form-group">
					<label for="lastName" class="control-label">Отчество:</label>
					<form:input id="lastName" path="lastName" class="form-control" />
				</div>
				<c:if test="${!empty unitsList}">
					<div class="form-group">
						<label for="unit" class="control-label">Подразделение:</label>
						<form:select id="unit" path="unitId" items="${unitsList}"
							default="${unitId}" class="form-control" />
					</div>
				</c:if>
				<input class="btn btn-success pull-right" type="submit"
					value="Сохранить" />
				<a href="<c:url value="/human.change_password/${humanProxy.id}" />">Изменить
					пароль</a>
				<form:hidden path="id" />
				<form:hidden path="unitId" />
			</form:form>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap_datepicker.jsp"%>

</body>
</html>