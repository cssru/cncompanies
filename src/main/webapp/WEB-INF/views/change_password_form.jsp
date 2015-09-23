<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Изменение пароля сотрудника <c:out
		value="${passwordProxy.loginName}" /></title>
<%@ include file="/WEB-INF/views/css.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/navbar_header.jsp"%>

	<div class="container">
		<div class="col-md-6 col-md-offset-3">
			<c:if test="${!empty error}">
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>ОШИБКА!</h4>
					<c:out value="${error}" />
				</div>
			</c:if>

			<h4 class="form-signin-heading">
				Смена пароля для пользователя
				<c:out value="${passwordProxy.loginName}" />
			</h4>

			<c:url var="updateUrl" value="/human.change_password" />
			<form:form id="passForm" role="form" action="${updateUrl}" method="POST"
				commandName="passwordProxy">
	<c:if test="${oldPasswordNeeded}">
					<div class="form-group">
						<label for="curPass" class="control-label">Ваш действующий
							пароль:</label>
						<form:password id="curPass" path="oldPassword"
							class="form-control" required="true" placeholder="Текущий пароль" />
					</div>
					</c:if>
				<div id="pass_div1" class="form-group has-success">
					<label for="pass1" class="control-label">Новый пароль:</label>
					<form:password id="pass1" path="newPassword" class="form-control"
						required="true" placeholder="Новый пароль" />
				</div>
				<div id="pass_div2" class="form-group has-success">
					<label for="pass2" class="control-label">Повторите новый
						пароль:</label>
					<form:password id="pass2" path="newPassword2" class="form-control"
						required="true" placeholder="Повтор пароля" />
					<p id="pass_msg" class="text-success"></p>
				</div>
				<input class="btn btn-success pull-right" type="submit"
					value="Изменить" />
				<form:hidden path="loginName" />
			</form:form>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>
	<%@ include file="/WEB-INF/views/script_password_check.jsp"%>

</body>
</html>
