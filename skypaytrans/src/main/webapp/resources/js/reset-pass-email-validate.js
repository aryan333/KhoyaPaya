$(document)
		.ready(
				function() {
					$("#submitBtn").click(function() {
						checkInputValidity();
					});

					$("[name='email']").keypress(function() {
						$("#emailInputError").text("");
						$("#notExist").text("");
					});

					function checkInputValidity() {
						emailIdInput = true;
						var emailIdPattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
						var enteredEmail = $("[name='email']").val();
						if (enteredEmail.length == 0) {
							emailIdInput = false;
							$("#emailInputError").text(
									"email id can't be empty");

						} else if (!emailIdPattern.test(enteredEmail)) {
							emailIdInput = false;
							$("#emailInputError").text("email not valid");
						}
						if (emailIdInput == true) {
							$('#submitBtn').prop('disabled', true);
							$("#notExist").css("color", "black");
							$("#notExist").text("Please wait...");
							$("#form").submit();
						}
					}
				});