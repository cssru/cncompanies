<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="display"></div>

	<%@ include file="/WEB-INF/views/script_jquery.jsp"%>

	<script>
		(function($, undefined) {
			$(document).ready(function() {
				sendAjax();

			});

			function sendAjax() {
				$
						.ajax({
							url : "http://admin-pc:8080/cncompanies/human.synch",
							type : "POST",
							dataType : 'json',
							data : JSON.stringify({
								lastSynchTime : 12345,
								items : [ {
									clientId : 100,
									serverId : 200,
								}, {
									clientId : 300,
									serverId : 400,
								}, {
									clientId : 500,
									serverId : 600,
								} ],
								objects : [ {
									clientId : 1,
									surname : "Иванов",
									name : "Иван",
									lastName : "Иванович",
									note : "Примечание",
									birthday : 20,
									lastModified : 54321,
									metadata : []
								} ],
								deletedItems : [ {
									clientId : 111,
									serverId : 111
								} ]
							}),
							contentType : 'application/json',
							success : function(data) {
								var d = "lastSynchTime:" + data.lastSynchTime
										+ "<br/>";
								d += "Items:<br/>";
								for (var i = 0; i < data.items.length; i++) {
									d += "clientId:" + data.items[i].clientId
											+ "<br/>";
									d += "serverId:" + data.items[i].serverId
											+ "<br/>";
								}
								d += "Objects:<br/>";
								for (var i = 0; i < data.objects.length; i++) {
									d += "surname:" + data.objects[i].surname + " " +data.objects[i].name + " " + data.objects[i].lastName 
											+ "<br/>";
								}
								d += "Deleted items:<br/>";
								for (var i = 0; i < data.deletedItems.length; i++) {
									d += "clientId:"
											+ data.deletedItems[i].clientId
											+ "<br/>";
									d += "serverId:"
											+ data.deletedItems[i].serverId
											+ "<br/>";
								}
								$("#display").html(d);
							},
							error : function(data, status, er) {
								alert("Error: " + status + " " + er);
							}
						});
			}

		})(jQuery);
	</script>
</body>
</html>