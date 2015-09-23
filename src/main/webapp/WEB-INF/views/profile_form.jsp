<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Изменение данных сотрудника</title>
<%@ include file="/WEB-INF/views/css.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/navbar_header.jsp"%>

	<div class="container">
		<div class="col-md-6 col-md-offset-3">

			<c:url var="updateUrl" value="/human.profile" />
			<form:form action="${updateUrl}" method="POST" commandName="human">
				<div class="form-group">
					<label for="surname" class="control-label">Фамилия:</label>
					<form:input id="surname" class="form-control" path="surname" />
				</div>
				<div class="form-group">
					<label for="name" class="control-label">Имя:</label>
					<form:input id="name" class="form-control" path="name" />
				</div>
				<div class="form-group">
					<label for="lastName" class="control-label">Отчество:</label>
					<form:input id="lastName" class="form-control" path="lastName" />
				</div>
				<input class="btn btn-success pull-right" type="submit"
					value="Сохранить" />
				<form:hidden path="id" />
			</form:form>
			<p>
				<a href="<c:url value="/human.change_password/0" />">Изменить
					пароль</a>
			</p>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>

</body>
</html>