<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<div class="navbar navbar-default navbar-static-top">
	<div class="container">
		<button class="navbar-toggle" data-toggle="collapse"
			data-target=".navbar-collapse">
			<span class="icon-bar"></span> <span class="icon-bar"></span> <span
				class="icon-bar"></span>
		</button>
		<a href="<c:url value="/" />" class="navbar-brand"
			title="Chief Notes Companies"> <img style="margin-top: -14px;"
			src="<c:url value="/resources/img/chessoft-96.png"/>"></a> <a
			href="<c:url value="/" />" class="navbar-brand"
			title="Chief Notes Companies">Chief Notes Companies</a>
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
					<li><a href="<c:url value="/" />">Главная</a></li>
					<sec:authorize access="hasRole('COMPANY_MANAGER')">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown">Структура <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="<c:url value="/company.list" />">Компании</a></li>
								<li><a href="<c:url value="/unit.list/all" />">Все
										подразделения</a></li>
							</ul></li>
					</sec:authorize>

					<sec:authorize access="hasRole('UNIT_MANAGER')">
						<li><a href="<c:url value="/unit.list" />">Подразделения</a></li>
					</sec:authorize>

					<sec:authorize
						access="hasRole('UNIT_MANAGER') || hasRole('COMPANY_MANAGER')">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown">Задачи <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="<c:url value="/task.list/0" />">Мои задачи</a></li>
								<li><a href="<c:url value="/task.list/slave" />">Задачи
										подчиненных</a></li>
							</ul></li>
						<li class="active"><a
							href="<c:url value="/task.list/archive/0" />">Архив моих
								задач</a></li>
						<li><a href="<c:url value="/task.list/archive/slave" />">Архив
								подчиненных</a></li>
					</sec:authorize>

					<sec:authorize access="hasRole('USER')">
						<li class="active"><a
							href="<c:url value="/task.list/archive/0" />">Архив моих
								задач</a></li>
					</sec:authorize>
					<li><a href="<c:url value="/logout" />">Выход</a></li>
				</sec:authorize>
			</ul>
		</div>
	</div>
</div>