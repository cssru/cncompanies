<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<script>
	$(function() {
		$(document).ready(
				function() {
					var now;
					if ($("#expiresMillis").prop("value") == 0) {
						now = new Date();
					} else {
						now = new Date();
						now.setTime($("#expiresMillis").prop("value"));
					}
					var nowDateString = addZero(now.getDate()) + "-"
							+ addZero(now.getMonth() + 1) + "-"
							+ addZero(now.getFullYear());
					var nowTimeString = addZero(now.getHours()) + ":"
							+ addZero(now.getMinutes());
					$("#dpexp").prop("data-date", nowDateString);
					$("#expiresDate").prop("value", nowDateString);
					$("#expiresTime").prop("value", nowTimeString);

				});

		$("#dpexp").datepicker();
		$(".clockpicker").clockpicker();
		$("#taskForm").submit(
				function(event) {
					$("#expiresMillis").prop("value", parseDate($("#expiresDate").prop("value"), $("#expiresTime").prop("value")));
					return true;
				});

	});

	function addZero(value) {
		if (value < 10)
			return "0" + value;
		return value;
	}

	function parseDate(dateString, timeString) {
		var dateParts = dateString.split("-");
		var timeParts = timeString.split(":");
		date = new Date();
		date.setDate(dateParts[0]);
		date.setMonth(dateParts[1] - 1);
		date.setFullYear(dateParts[2]);
		date.setHours(timeParts[0]);
		date.setMinutes(timeParts[1]);
		date.setSeconds(0);
		date.setMilliseconds(0);
		return date.getTime();
	}
</script>