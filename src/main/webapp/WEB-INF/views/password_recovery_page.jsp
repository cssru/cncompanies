<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/views/include.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Регистрация пользователя</title>
<%@ include file="/WEB-INF/views/css.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/navbar_header.jsp"%>

	<div class="container" style="width: 300px;">
	
		<c:if test="${!empty error}">
			<div class="alert alert-danger">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4>ОШИБКА!</h4>
				<c:out value="${error}" />
			</div>
		</c:if>

		<h4 class="form-signin-heading">Для восстановления пароля введите указанные данные для вашего аккаунта</h4>
		<c:url var="registrationURL" value="/password_recovery" />
		<form:form id="passForm" role="form" action="${registrationURL}" method="POST"
			commandName="login">
			<div class="form-group">
				<label for="login" class="control-label">Логин</label>
				<form:input id="login" class="form-control" path="login"
					placeholder="Логин" required="true" autofocus="true" value="" />
			</div>
			<div class="form-group">
				<label for="email" class="control-label">Email</label>
				<form:input id="email" class="form-control" path="email" placeholder="Email"
					required="true" value="" />
			</div>
			<div class="form-group">
				<label for="surname" class="control-label">Фамилия</label>
				<form:input id="surname" class="form-control" path="human.surname"
					placeholder="Фамилия" required="true" value="" />
			</div>
			<div class="form-group">
				<label for="name" class="control-label">Имя</label>
				<form:input id="name" class="form-control" path="human.name" placeholder="Имя"
					required="true" value="" />
			</div>
			<div class="form-group">
				<label for="lastName" class="control-label">Отчество</label>
				<form:input id="lastName" class="form-control" path="human.lastName"
					placeholder="Отчество" required="true" value="" />
			</div>
			<input class="btn btn-success pull-right" type="submit"
				value="Получить новый пароль" />
		</form:form>
	</div>

	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>
	<%@ include file="/WEB-INF/views/script_password_check.jsp"%>

</body>
</html>
