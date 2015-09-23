/**
 * 
 */
(function($, undefined) {

	$(document).ready(
			function() {
				$(".date-element").each(
						function(index) {
							var millis = $(this).data("millis");
							var date = new Date();
							date.setTime(millis);
							var dateString = addZero(date.getDate())
							+ "-"
							+ addZero(date.getMonth() + 1)
							+ "-" + addZero(date.getFullYear())
							+ " " + addZero(date.getHours())
							+ ":" + addZero(date.getMinutes());
							$(this).html(dateString);
						});
			});

	function addZero(value) {
		if (value < 10)
			return "0" + value;
		return "" + value;
	}

	$(".delete-control").click(function(event) {
		if (confirm("Подтвердите удаление")) {
			sendDeleteAjax($(this).parent().parent().attr("id"));
		}
	});

	function sendDeleteAjax(id) {
		$.ajax({
			url : "/cncompanies/task.delete.ajax/" + id,
			type : "GET",
			dataType : 'json',
			data : {},
			contentType : 'application/json',
			success : function(data) {
				var rowId = "#" + id;
				$(rowId).hide("slow", function() {
					$(rowId).remove();
				});
			},
			error : function(data, status, er) {
				alert("AJAX Error: " + status + " " + er);
			}
		});
	}

	// edit task content
	var textFieldTextClick = function() {
		var currentText = $(this).text();
		$(this)
		.html(
				"<input type=\"text\" class=\"text-field-input\"></input>");
		$(".text-field-input").focus().attr("value", currentText);
		$(".text-field-text").unbind("click");
		$(".text-field-input").blur(function() {
			var newText = $(this).prop("value");
			var id = $(this).parent().parent().attr("id");
			// send ajax data
			$.ajax({
				url : "/cncompanies/task.edit.ajax/set",
				type : "POST",
				dataType : "json",
				data : JSON.stringify({
					id : id,
					fieldName : $(this).parent().data("type"),
					value : newText
				}),
				contentType : 'application/json; charset=utf-8',
				success : function(data) {
					$(this).unbind("blur");
					$(this).remove();
					$("#" + id + ">.text-field-text").text(newText);
					$(".text-field-text").click(textFieldTextClick);
				},
				error : function(data, status, er) {
					alert("AJAX Error: " + status + " " + er);
				}
			});
		});
	}
	$(".text-field-text").click(textFieldTextClick);

	var textAreaTextClick = function() {
		var currentText = $(this).text();
		$(this).html("<textarea class=\"text-area-input\"></textarea>");
		$(".text-area-input").focus().prop("value", currentText);
		$(".text-area-text").unbind("click");
		$(".text-area-input").blur(function() {
			var newText = $(this).prop("value");
			var id = $(this).parent().parent().attr("id");

			// send ajax data
			$.ajax({
				url : "/cncompanies/task.edit.ajax/set",
				type : "POST",
				dataType : "json",
				data : JSON.stringify({
					id : id,
					fieldName : $(this).parent().data("type"),
					value : newText
				}),
				contentType : 'application/json; charset=utf-8',
				success : function(data) {
					$(this).unbind("blur");
					$(this).remove();
					$("#" + id + ">.text-area-text").text(newText);
					$(".text-area-text").click(textAreaTextClick);
				},
				error : function(data, status, er) {
					alert("AJAX Error: " + status + " " + er);
				}
			});
		});
	}
	$(".text-area-text").click(textAreaTextClick);

})(jQuery);