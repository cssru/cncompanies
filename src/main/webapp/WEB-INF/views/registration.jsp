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
	<div class="alert alert-success">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<h4>Уважаемые пользователи!</h4>
			В настоящее время сервис Chief Notes Companies находится в
			разработке! Вы можете использовать данный сервис только в
			ознакомительных целях, так как до момента ввода его в эксплуатацию
			пользовательские данные будут удаляться с сайта без предварительного
			уведомления! Синхронизация данных с мобильным приложением пока не
			функционирует.
		</div>
	
		<h4 class="form-signin-heading">Зарегистрируйтесь в системе</h4>
		<c:url var="registrationURL" value="/registration" />
		<form:form id="passForm" role="form" action="${registrationURL}" method="POST"
			commandName="accountDto">
			<div class="form-group">
				<label for="login" class="control-label">Логин</label>
				<form:input id="login" class="form-control" path="login"
					placeholder="Логин" required="true" autofocus="true" value="" />
					<form:errors path="login" element="div" cssClass="text-danger alert-danger"/>
			</div>
			<div id="pass_div1" class="form-group has-success">
				<label for="pass1" class="control-label">Пароль</label>
				<form:password id="pass1" class="form-control" path="password"
					placeholder="Пароль" required="true" value="" />
					<form:errors path="password" element="div" cssClass="text-danger alert-danger"/>
			</div>
			<div id="pass_div2" class="form-group has-success">
				<label for="pass2" class="control-label">Повтор пароля</label>
				<form:password id="pass2" class="form-control"
					path="passwordConfirm" placeholder="Повтор пароля" required="true"
					value="" />
			</div>
			<div class="form-group">
				<label for="email" class="control-label">Email</label>
				<form:input id="email" class="form-control" path="email" placeholder="Email"
					required="true" value="" />
					<form:errors path="email" element="div" cssClass="text-danger alert-danger"/>
			</div>
			<div class="form-group">
				<label for="surname" class="control-label">Фамилия</label>
				<form:input id="surname" class="form-control" path="surname"
					placeholder="Фамилия" required="true" value="" />
					<form:errors path="surname" element="div" cssClass="text-danger alert-danger"/>
			</div>
			<div class="form-group">
				<label for="name" class="control-label">Имя</label>
				<form:input id="name" class="form-control" path="name" placeholder="Имя"
					required="true" value="" />
					<form:errors path="name" element="div" cssClass="text-danger alert-danger"/>
			</div>
			<div class="form-group">
				<label for="lastName" class="control-label">Отчество</label>
				<form:input id="lastName" class="form-control" path="lastName"
					placeholder="Отчество" required="true" value="" />
					<form:errors path="lastName" element="div" cssClass="text-danger alert-danger"/>
			</div>
			<input class="btn btn-success pull-right" type="submit"
				value="Зарегистрироваться" />
		</form:form>
	</div>

	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>
	<%@ include file="/WEB-INF/views/script_password_check.jsp"%>

</body>
</html>
