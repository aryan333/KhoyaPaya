$(document).ready(function() {

	$("#submitForm").click(function() {
		checkInputValidity();
	});

	$("[name='username']").keypress(function() {
		$("#mobInputError").text("");
		$("#notExistSpan").text("");
		$("#passReset").text("");
	});

	$("[name='password']").keypress(function() {
		$("#passInputError").text("");
		$("#notExistSpan").text("");
		$("#passReset").text("");
	});

	/*
	 * $('.input').enter(function(e) { if(e.which == 13) { alert('You pressed
	 * enter!');
	 *  } });
	 */
	$('input').keypress(function(e) {
		if(e.which==13){
		checkInputValidity();
		}
	});
	
	function checkInputValidity() {
		var emailInput= true, passInput = true;
		var emailPattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		if ($("[name='username']").val().length == 0) {
			emailInput= false;
			$("#mobInputError").text("User Login can't be empty");

		} else if (!emailPattern.test($("[name='username']").val())) {
			emailInput= false;
			$("#mobInputError").text("invalid input");
		}
		if ($("[name='password']").val().length == 0) {
			passInput = false;
			$("#passInputError").text("password can't be empty");
		} else if (($("[name='password']").val().length < 4)) {
			passInput = false;
			$("#passInputError").text("at least 4 letters are required");
		}

		if (emailInput&& passInput) {
			$("#loginForm").submit();
		}
	}
});