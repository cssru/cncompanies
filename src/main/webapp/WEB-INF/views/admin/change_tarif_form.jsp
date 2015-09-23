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
				Изменение тарифа пользователя "
				<c:out value="${login.login}" />
				"
			</h4>
			<c:url var="updateUrl" value="/admin/user.change_tarif/${login.id}" />
			<form:form role="form" action="${updateUrl}" method="POST"
				commandName="tarif">
				
					<div class="form-group">
						<label for="tarif" class="control-label">Выберите тариф:</label>
						<form:select id="tarif" path="tarif" items="${tarifList}"
							default="${login.tarif.tarif}" class="form-control" />
					</div>
				<input class="btn btn-success pull-right" type="submit"
					value="Сохранить" />
			</form:form>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap_datepicker.jsp"%>

</body>
</html>