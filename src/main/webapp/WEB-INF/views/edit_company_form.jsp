<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Company data</title>
<%@ include file="/WEB-INF/views/css.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/navbar_header.jsp"%>
	<div class="container">
		<div class="col-md-6 col-md-offset-3">
			<h4 class="form-signin-heading">
				Изменение данных компании "
				<c:out value="${company.name}" />
				"
			</h4>
			<c:url var="updateUrl" value="/company.edit" />
			<form:form action="${updateUrl}" method="POST" commandName="company">
				<div class="form-group">
					<label for="name" class="control-label">Название:</label>
					<form:input id="name" path="name" class="form-control" />
				</div>
				<div class="form-group">
					<label for="description" class="control-label">Описание:</label>
					<form:textarea id="description" path="description"
						class="form-control" />
				</div>
				<input type="submit" value="Сохранить" class="btn btn-success pull-right" />
				<form:hidden path="id" />
			</form:form>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>

</body>
</html>