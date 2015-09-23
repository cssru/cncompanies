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

		<sec:authorize access="hasRole('UNIT_MANAGER')">
			<c:url var="unitListUrl" value="/unit.list" />
		</sec:authorize>
		<sec:authorize access="hasRole('COMPANY_MANAGER')">
			<c:url var="unitListUrl" value="/unit.list/${unit.company.id}" />
		</sec:authorize>
		<div class="btn-group pull-right">
			<a class="btn" href="${unitListUrl}" role="button">К списку
				подразделений</a>
			<sec:authorize access="hasRole('COMPANY_MANAGER')">
				<c:url var="companyListUrl" value="/company.list" />
				<a class="btn" href="${companyListUrl}" role="button">К списку
					компаний</a>
			</sec:authorize>
		</div>
	</div>

	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>
</body>
</html>