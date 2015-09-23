<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<title>Chief Notes Companies</title>
<%@ include file="/WEB-INF/views/css.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/navbar_header.jsp"%>
	<div class="container">

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

		<c:if test="${!empty login and !login.confirmed}">
			<div class="alert alert-success">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4>
					Уважаемый
					<c:out value="${login.human.name}" />
					<c:out value="${login.human.lastName}" />
					!
				</h4>
				Вы успешно зарегистрировались в системе как <strong><c:out
						value="${login.login}" /></strong>.
				<p>
					На Ваш адрес электронной почты <strong><c:out
							value="${login.email}" /></strong> было отправлено письмо для
					подтверждения регистрации. Ссылка в письме действительна в течение
					24 часов.
				</p>
			</div>
		</c:if>
		<c:if test="${confirmed}">
			<div class="alert alert-success">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				Ваш аккаунт подтвержден.
			</div>
		</c:if>

		<sec:authorize access="!isAuthenticated()">
			<h2>Добро пожаловать на сайт системы управления задачами "Chief
				Notes Companies"!</h2>
			<p>
				Система позволяет создавать компании, подразделения в этих компаниях
				и добавлять сотрудников. Руководители компаний и руководители
				подразделений могут добавлять задачи подчиненным сотрудникам. Кроме
				того, сотрудники могут хранить в системе собственные задачи. Данные
				синхронизируются с приложением <a class="navbar-link"
					href="<c:url value="/chiefnotes" />">"Записки Шефа"</a> для
				операционной системы Android.
			</p>
			<p>
				Для начала работы Вам необходимо <a class="navbar-link"
					href="<c:url value="/registration" />">зарегистрироваться</a>.
			</p>
			<p>Для регистрации необходимо иметь действующий почтовый ящик в
				одной из почтовых систем.</p>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<h2>Добро пожаловать в систему управления задачами "Chief Notes
				Companies"!</h2>
			<sec:authorize access="hasRole('COMPANY_MANAGER')">
				<p>
					<strong>Ваш тариф:</strong>
					<c:out value="${login.tarif.tarifName}" />
				</p>
				<p>
					<strong>Максимальное количество сотрудников:</strong>
					<c:out value="${login.tarif.maximumEmployees}" />
				</p>
				<p>
					<strong>Зарегистрировано сотрудников:</strong>
					<c:out value="${employeesCount}" />
				</p>
				<p>
					<strong>Доступно для регистрации (сотрудников):</strong>
					<c:out value="${login.tarif.maximumEmployees - employeesCount}" />
				</p>
				<p>
					<strong>Стоимость в месяц:</strong>
					<c:out value="${login.tarif.monthPay}" />
				</p>
				<p>
					<strong>Действителен до:</strong>
					<fmt:formatDate pattern="dd-MM-YYYY HH:mm"
						value="${login.paidTill}" />
				</p>
				<p>Для начала работы:</p>
				<ul>
					<li>Создайте компанию.</li>
					<li>Создайте подразделение в компании, которому будут
						принадлежать руководители остальных подразделений (например,
						"Управление").</li>
					<li>Добавьте в данное подразделение сотрудников, являющихся
						руководителями подразделений.</li>
					<li>Создайте в компании необходимое количество подразделений,
						каждому из которых Вы сможете назначить руководителя. Руководитель
						имеет право добавлять сотрудников в подчиненное подразделение и
						назначать им задачи.</li>
					<li>При необходимости добавьте в подразделения сотрудников
						самостоятельно.</li>
				</ul>
				<br />
				<p>По вопросам работы с системой обращайтесь на электронную
					почту cssru@mail.ru</p>
			</sec:authorize>

			<sec:authorize access="hasRole('UNIT_MANAGER')">
				<p>Для начала работы:</p>
				<ul>
					<li>Переидите к списку Ваших подразделений.</li>
					<li>Кликните на наименование одного из Ваших подразделений для
						просмотра списка сотрудников.</li>
					<li>Добавьте в данное подразделение сотрудников.</li>
					<li>Добавьте задачи сотрудникам. Для этого кликните по
						соответствующей кнопке в строке с именем сотрудника. На
						появившейся странице добавьте необходимое количество задач.</li>
				</ul>
				<br />
				<p>По вопросам работы с системой обращайтесь на электронную
					почту cssru@mail.ru</p>
			</sec:authorize>

			<sec:authorize access="hasRole('USER')">
				<p>Для начала работы:</p>
				<ul>
					<li>Переидите к списку Ваших задач.</li>
					<li>Вы не можете удалять или изменять задачи, поставленные Вам
						вышестоящими начальниками.</li>
					<li>Для сообщения начальнику о том, что его задача выполнена,
						нажмите "Выполнить". При необходимости, добавьте комментарий.</li>
					<li>Вы можете создавать собственные задачи, необходимые для
						планирования Вашей деятельности. Ваши собственные задачи видны
						только Вам, при этом Вы можете изменять и удалять их.</li>
				</ul>
				<br />
				<p>По вопросам работы с системой обращайтесь на электронную
					почту cssru@mail.ru</p>
			</sec:authorize>
		</sec:authorize>
	</div>

	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>

</body>
</html>