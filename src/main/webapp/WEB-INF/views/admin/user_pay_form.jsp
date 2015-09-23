<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Добавление оплаты клиенту <c:out
		value="${login.login}" /></title>
<%@ include file="/WEB-INF/views/css.jsp"%>
</head>
<body>
<div class="navbar navbar-default navbar-static-top">
	<div class="container">
		<button class="navbar-toggle" data-toggle="collapse"
			data-target=".navbar-collapse">
			<span class="icon-bar"></span> <span class="icon-bar"></span> <span
				class="icon-bar"></span>
		</button>
		<a href="/" class="navbar-brand">Chief Notes Companies</a>
		<sec:authorize access="isAuthenticated()">
			<p class="navbar-text pull-right">
				Вы вошли как <a href="<c:url value="/human.profile" />"> <sec:authentication
						property="principal.username" />
				</a>
			</p>
		</sec:authorize>
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="isAuthenticated()">
					<li class="active"><a href="<c:url value="/admin/" />">Главная</a></li>
						<li><a href="<c:url value="/admin/user.list" />">Пользователи</a></li>
						<li><a href="<c:url value="/logout" />">Выход</a></li>
				</sec:authorize>
			</ul>
		</div>
	</div>
</div>
	<div class="container">
		<c:if test="${!empty error}">
			<div class="alert alert-danger">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4>ОШИБКА!</h4>
				<c:out value="${error}" />
			</div>
		</c:if>

		<h4 class="form-signin-heading">Добавление оплаты пользователю 
		<c:out value="${login.login}" /></h4>

		<c:url var="updateUrl" value="/admin/user.pay" />
		<form:form action="${updateUrl}" method="POST" commandName="payProxy">
			<table align="center">
				<tr>
					<td>Сумма:</td>
					<td><div id="summ_div" class="has-success">
							<form:input path="payValue" class="form-control" required="true" />
						</div></td>
				</tr>
			</table>
			<button class="btn btn-success pull-right" type="submit" >Зачислить</button>
			<form:hidden path="userId" />
		</form:form>
	</div>
	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>

</body>
</html>
