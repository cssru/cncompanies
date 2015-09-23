<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Редактирование задачи</title>
<%@ include file="/WEB-INF/views/css.jsp"%>
<link href="<c:url value="/resources/css/datepicker.css" />"
	rel="stylesheet">
<link
	href="<c:url value="/resources/css/bootstrap-clockpicker.min.css" />"
	rel="stylesheet">
</head>
<body>
	<div class="navbar navbar-default navbar-static-top">
		<div class="container">
			<button class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a href="#" class="navbar-brand">Chief Notes Companies</a>
			<sec:authorize access="isAuthenticated()">
				<p class="navbar-text pull-right">
					Вы вошли как
					<sec:authentication property="principal.username" />
				</p>
			</sec:authorize>

			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav navbar-right">
					<sec:authorize access="isAuthenticated()">
						<li class="active"><a href="#">Главная</a></li>
						<sec:authorize access="hasRole('COMPANY_MANAGER')">
							<li><a href="<c:url value="/company.list" />">Компании</a></li>
						</sec:authorize>
						<sec:authorize access="hasRole('UNIT_MANAGER')">
							<li><a href="<c:url value="/unit.list" />">Подразделения</a></li>
						</sec:authorize>
						<li><a href="<c:url value="/task.list/0" />">Мои задачи</a></li>
						<li><a href="<c:url value="/logout" />">Выход</a></li>
					</sec:authorize>
				</ul>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="col-md-6 col-md-offset-3">
			<h4 class="form-signin-heading">Изменение задачи:</h4>
			<c:url var="updateUrl"
				value="/task.update/${taskProxy.id}/${redirect}" />
			<form:form id="taskForm" action="${updateUrl}" method="POST"
				commandName="taskProxy">
				<div class="form-group">
					<label for="content" class="control-label">Содержание
						задачи:</label>
					<form:textarea class="form-control" id="content" path="content" />
				</div>
				<div class="form-group">
					<label for="comment" class="control-label">Комментарий:</label>
					<form:textarea class="form-control" id="comment" path="comment"
						value="" />
				</div>
				<div class="form-group">
					<label for="datetime" class="control-label">Срок
						исполнения:</label>
					<div id="datetime" class="row">
						<div class="input-group date" id="dpexp" data-date=""
							data-date-format="dd-mm-yyyy">
							<input id="expiresDate" class="form-control" value="" readonly />
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-calendar"></span></span>
						</div>
						<div class="input-group clockpicker">
							<input id="expiresTime" class="form-control" value="" readonly />
							<span class="input-group-addon"> <span
								class="glyphicon glyphicon-time"> </span>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label for="alertTimeDiv" class="control-label">Срок
						предупреждения:</label>
					<div id="alertTimeDiv" class="row">

						<label for="daysDiv" class="control-label">Дни:</label>
						<div id="daysDiv" class="input-group spinner"
							data-trigger="spinner">
							<input id="days" type="text" class="form-control" value="0"
								data-min="0" readonly>
							<div class="input-group-addon">
								<a href="javascript:;" class="spin-up" data-spin="up"><span
									class="glyphicon glyphicon-arrow-up"></span></a> <a
									href="javascript:;" class="spin-down" data-spin="down"><span
									class="glyphicon glyphicon-arrow-down"></span></a>
							</div>
						</div>

						<label for="hoursDiv" class="control-label">Часы:</label>
						<div id="hoursDiv" class="input-group spinner"
							data-trigger="spinner">
							<input id="hours" type="text" class="form-control" value="0"
								data-max="23" data-min="0" readonly>
							<div class="input-group-addon">
								<a href="#" class="spin-up" data-spin="up"><span
									class="glyphicon glyphicon-arrow-up"></span></a> <a href="#"
									class="spin-down" data-spin="down"><span
									class="glyphicon glyphicon-arrow-down"></span></a>
							</div>
						</div>

						<label for="minutesDiv" class="control-label">Минуты:</label>
						<div id="minutesDiv" class="input-group spinner"
							data-trigger="spinner">
							<input id="minutes" type="text" class="form-control" value="0"
								data-max="59" data-min="0" readonly>
							<div class="input-group-addon">
								<a href="#" class="spin-up" data-spin="up"><span
									class="glyphicon glyphicon-arrow-up"></span></a> <a href="#"
									class="spin-down" data-spin="down"><span
									class="glyphicon glyphicon-arrow-down"></span></a>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label for="done" class="control-label">Выполнена:</label>
					<form:checkbox id="done" path="done" />
				</div>
				<input class="btn btn-success pull-right" type="submit"
					value="Сохранить" />
				<form:hidden path="id" />
				<form:hidden id="expiresMillis" path="expiresMillis" />
				<form:hidden id="alertTime" path="alertTime" />
			</form:form>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap_datepicker.jsp"%>
	<%@ include file="/WEB-INF/views/script_new_task.jsp"%>
	<%@ include file="/WEB-INF/views/script_spinner.jsp"%>
	<%@ include file="/WEB-INF/views/script_alerttime.jsp"%>

</body>
</html>