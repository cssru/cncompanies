<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ошибка!</title>
<%@ include file="/WEB-INF/views/css.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/navbar_header.jsp"%>

	<div class="container">
		<div class="text-danger">
			<c:out value="${error}" />
		</div>

		<c:if test="${wrong_credentials}">
			<div class="text-info">
				<a href="<c:url value="/password_recovery" />">Забыли пароль?</a>
			</div>
		</c:if>

		<a href="<c:url value="/logout" />"><button
				class="btn btn-success">Продолжить</button></a>
	</div>

	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>
</body>
</html>