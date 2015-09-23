/**
 * 
 */
$(function(){
	$(".spinner").spinner("changed",function(e){
		var newAlertTime = $("#days").prop("value")*24*60*60*1000 + $("#hours").prop("value")*60*60*1000 + $("#minutes").prop("value")*60*1000;
		$("#alertTime").prop("value", newAlertTime);
	});
});

var MILLIS_IN_DAY = 24*60*60*1000;
var MILLIS_IN_HOUR = 60*60*1000;
var MILLIS_IN_MINUTE = 60*1000;

$(document).ready(
		function() {
			var totalMillis = $("#alertTime").prop("value");
			var days = Math.floor(totalMillis / MILLIS_IN_DAY);
			var hours = Math.floor(totalMillis / MILLIS_IN_HOUR) - days*24;
			var minutes = Math.floor(totalMillis / MILLIS_IN_MINUTE) - hours*60 - days*24*60;
			$("#days").prop("value", days);
			$("#hours").prop("value", hours);
			$("#minutes").prop("value", minutes);
		});