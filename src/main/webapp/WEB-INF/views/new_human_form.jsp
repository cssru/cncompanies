<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Новый сотрудник</title>
<%@ include file="/WEB-INF/views/css.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/navbar_header.jsp"%>

	<div class="container">
		<c:if test="${!empty error}">
			<div class="alert alert-danger">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4>ОШИБКА!</h4>
				<c:out value="${error}" />
			</div>
		</c:if>
		<c:url var="addUrl" value="/human.add/${unit.id}" />
		<div class="col-md-6 col-md-offset-3">

			<h4 class="form-signin-heading">Введите данные нового сотрудника</h4>
			<form:form id="passForm" role="form" action="${addUrl}" method="POST"
				commandName="login">
				<div class="form-group">
					<label for="login" class="control-label">Логин:</label>
					<form:input id="login" path="login" class="form-control"
						required="true" />
				</div>
				<div id="pass_div1" class="form-group has-success">
					<label for="pass1" class="control-label">Пароль:</label>
					<form:password id="pass1" path="password" class="form-control"
						required="true" />
				</div>
				<div id="pass_div2" class="form-group has-success">
					<label for="pass2" class="control-label">Повтор пароля:</label>
					<form:password id="pass2" path="passwordConfirm"
						class="form-control" required="true" />
					<p id="pass_msg" class="text-success"></p>
				</div>
				<div class="form-group">
					<label for="email" class="control-label">E-mail:</label>
					<form:input id="email" path="email" class="form-control"
						required="true" />
				</div>
				<div class="form-group">
					<label for="surname" class="control-label">Фамилия:</label>
					<form:input id="surname" path="human.surname" class="form-control"
						required="true" />
				</div>
				<div class="form-group">
					<label for="name" class="control-label">Имя:</label>
					<form:input id="name" path="human.name" class="form-control"
						required="true" />
				</div>
				<div class="form-group">
					<label for="lastName" class="control-label">Отчество:</label>
					<form:input id="lastName" path="human.lastName"
						class="form-control" required="true" />
				</div>
				<input class="btn btn-success pull-right" type="submit"
					value="Добавить" />
			</form:form>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>
	<%@ include file="/WEB-INF/views/script_password_check.jsp"%>

</body>
</html>