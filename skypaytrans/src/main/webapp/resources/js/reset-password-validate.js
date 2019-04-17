$(document).ready(function() {
	$("#submitForm").click(function() {
		checkInputValidity();
	});

	$("[name='newPassword']").keydown(function() {
		$("#newPasswordInputError").text("");
		$("#confPasswordInputError").text("");
		$("#paramsNotValid").text("");
	});

	$("[name='confPassword']").keydown(function() {
		$("#newPasswordInputError").text("");
		$("#confPasswordInputError").text("");
		$("#paramsNotValid").text("");
	});

	$('input').keypress(function(e) {
		if(e.which==13){
		checkInputValidity();
		}
	});
	
	function checkInputValidity() {
		var newPass= true, confPass = true;	
		var newpassVal = $("[name='newPassword']").val();
		var confPassVal = $("[name='confPassword']").val();
		if (newpassVal.length == 0) {
			newPass = false;			
			$("#newPasswordInputError").text("field can't be empty");
		} else if (newpassVal.length < 4) {
			newPass = false;
			$("#newPasswordInputError").text("at least 4 letters are required");
		}
		if (confPassVal.length == 0) {
			confPass = false;
			$("#confPasswordInputError").text("field can't be empty");
		} else if (confPassVal != newpassVal) { 
			confPass = false;
			$("#confPasswordInputError").text("password do not match");
		}		
		if (newPass && confPass) {
			$("#resetPassForm").submit();
			console.log('ok');
		}
	}
});