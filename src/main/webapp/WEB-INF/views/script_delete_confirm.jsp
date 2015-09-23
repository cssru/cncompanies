<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<script>
	(function($, undefined) {
		$("button.btn-danger").parent().click(function(event) {
			return confirm("Подтвердите удаление");
		});

	})(jQuery);
</script>
