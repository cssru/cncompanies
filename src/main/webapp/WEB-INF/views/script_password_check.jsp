<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<script>
	(function($, undefined) {
		$("#pass1, #pass2").on("input", function(event) {
			if ($("#pass1").prop("value") != $("#pass2").prop("value")) {
				$("#pass_div1").removeClass("has-success");
				$("#pass_div1").addClass("has-error");
				$("#pass_div2").removeClass("has-success");
				$("#pass_div2").addClass("has-error");

				$("#pass_msg").removeClass("text-success");
				$("#pass_msg").addClass("text-danger");
				$("#pass_msg").html("Пароли не совпадают");
				
			} else {
				$("#pass_div1").removeClass("has-error");
				$("#pass_div1").addClass("has-success");
				$("#pass_div2").removeClass("has-error");
				$("#pass_div2").addClass("has-success");

				$("#pass_msg").removeClass("text-danger");
				$("#pass_msg").addClass("text-success");
				$("#pass_msg").html("Пароли совпадают");
			}
		});

		$("#passForm").submit(function(event) {
			if ($("#pass1").prop("value") != $("#pass2").prop("value")) {
				alert("Пароли не совпадают!");
				return false;
			}
			return true;
		});

	})(jQuery);
</script>
