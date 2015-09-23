<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Добавление комментария к задаче</title>
<%@ include file="/WEB-INF/views/css.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/navbar_header.jsp"%>

	<div class="container">

		<c:url var="addCommentUrl" value="/task.execute/${redirect}" />
		<form:form action="${addCommentUrl}" method="POST" commandName="task">
			<p class="text-success">Задача отмечена как выполненная.</p>
			<p class="text-success">Добавьте комментарий к выполненной
				задаче:</p>
			<form:textarea path="comment" class="form-control" />
			<form:hidden path="id" />
			<input class="btn btn-success" type="submit" value="Отправить" />
		</form:form>
	</div>
	<%@ include file="/WEB-INF/views/include_footer.jsp"%>
	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>
	<%@ include file="/WEB-INF/views/script_bootstrap.jsp"%>
</body>
</html>