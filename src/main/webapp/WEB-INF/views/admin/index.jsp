<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/views/include.jsp"%>

<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Главная страница</title>
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
		<sec:authorize access="isAuthenticated()">
			<h2>Добро пожаловать в систему администрирования "Chief Notes
				Companies"!</h2>
		</sec:authorize>
	</div>

	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>

</body>
</html>