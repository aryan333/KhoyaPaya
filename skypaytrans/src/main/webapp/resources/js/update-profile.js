$(document)
		.ready(
				function() {

					$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
						var token = $('input[name="_csrf"]').attr('value');
						return jqXHR.setRequestHeader('X-CSRF-Token', token);
					});			
					

					$("#btn1").click(function() {

						checkFormSubmit();

					});
					$("#changePassBtn").click(function() {

						/* $("#updatePassword").submit(); */
						var curPass = $('#curPassField').val();
						var newPass = $('#newPassField').val();
						var confPass = $('#confPassField').val();
						changePassword(curPass, newPass, confPass);
					});

					$("#firstName").keyup(function() {
						checkFirstNameValidity();
					});

					$("#lastName").keyup(function() {

						checkLastNameValidity();
					});

					$("#userDetail.emailId").keyup(function() {
						checkEmailValidity();
					});

					function checkLastNameValidity() {
						var firstNameInput = true, lastNameInput = true;
						var lastNamePattern = /^[a-zA-Z]{3,15}$/;
						if ($("[name='lastName']").val().length == 0) {
							lastNameInput = false;
							$("#lastNameInputError").text(
									"last Name can't be empty");

						} else if (!lastNamePattern.test($("[name='lastName']")
								.val().trim())) {
							lastNameInput = false;

							$("#lastNameInputError")
									.text(
											" last Name minimum 3 character and maximum 15");
						} else {
							$("#lastNameInputError").text("");

						}
					}

					function checkFirstNameValidity() {
						var firstNameInput = true, lastNameInput = true;
						var firstNamePattern = /^[a-zA-Z]{3,15}$/;
						if ($("[name='firstName']").val().length == 0) {
							firstNameInput = false;
							$("#firstNameInputError").text(
									"first Name can't be empty");

						} else if (!firstNamePattern.test($(
								"[name='firstName']").val().trim())) {
							firstNameInput = false;

							$("#firstNameInputError")
									.text(
											" first Name minimum 3 character and maximum 15 ");
						} else {
							$("#firstNameInputError").text("");
						}

					}

					function checkEmailValidity() {
						emailIdInput = true;
						var emailIdPattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

						if ($("[name='userDetail.emailId']").val().length == 0) {
							emailIdInput = false;
							$("#emailIdInputError").text(
									"email id can't be empty");

						} else if (!emailIdPattern.test($(
								"[name='userDetail.emailId']").val())) {
							emailIdInput = false;
							$("#emailIdInputError").text(
									" email id-example abad@gmail.com");
						} else {
							$("#emailIdInputError").text("");
						}
					}

					function checkFormSubmit() {

						var firstNameInput = true, lastNameInput = true;
						emailIdInput = true;
						var lastNamePattern = /^[a-zA-Z]{3,15}$/;
						if ($("[name='lastName']").val().length == 0) {
							lastNameInput = false;
							$("#lastNameInputError").text(
									"last Name can't be empty");

						} else if (!lastNamePattern.test($("[name='lastName']")
								.val().trim())) {
							lastNameInput = false;

							$("#lastNameInputError")
									.text(
											" last Name minimum 3 character and maximum 15");
						} else {
							$("#lastNameInputError").text("");

						}

						var firstNamePattern = /^[a-zA-Z]{3,15}$/;
						if ($("[name='firstName']").val().length == 0) {
							firstNameInput = false;
							$("#firstNameInputError").text(
									"first Name can't be empty");

						} else if (!firstNamePattern.test($(
								"[name='firstName']").val().trim())) {
							firstNameInput = false;

							$("#firstNameInputError")
									.text(
											" first Name minimum 3 character and maximum 15 ");
						} else {
							$("#firstNameInputError").text("");
						}

						var emailIdPattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

						if ($("[name='userDetail.emailId']").val().length == 0) {
							emailIdInput = false;
							$("#emailIdInputError").text(
									"email id can't be empty");

						} else if (!emailIdPattern.test($(
								"[name='userDetail.emailId']").val())) {
							emailIdInput = false;
							$("#emailIdInputError").text("invalid input");
						} else {
							$("#emailIdInputError").text("");
						}

						if (firstNameInput && lastNameInput && emailIdInput) {

							$("#updateProfile").submit();
						}
					}

					function changePassword(curPass, newPass, confPass) {
						$
								.ajax({
									type : "POST",
									url : "../admin/changePassword",
									async : true,
									contentType : 'application/json',
									data : JSON.stringify({
										'currentPassword' : curPass,
										'newPassword' : newPass,
										'confPassword' : confPass
									}),									
									success : function(data) {
										$('#responseMessage').css("color", "green").text(data.response);
										$('#curPassField').val('');
										$('#newPassField').val('');
										$('#confPassField').val('');
									},
									error : function(jqXHR, textStatus, errorThrown) {										
										var responseCode = jqXHR.status;
										var text = jQuery.parseJSON(jqXHR.responseText);
										if(responseCode==400){											
											$('#responseMessage').css("color", "red").text(text.response);											
										}else if(responseCode==500){
											alert('Internal server error.Please try again or try later');
										}else if(responseCode==403){
											alert('please do logout and then login');
										}
										
									}

								});

					}					
					
				});