/*var totalUserClick = false, // as data is already shown when page loads
regUserClick = true, tempUserClick = true, todayRegUserClick = true, todayTempUserClick = true;
 */
findUser = new function() {
	var $instance = this;
	this.init = function() {
		var mob,deactivationReason;		
		
		$("#searchUser").click(function() {
			$('#mobileInputError').text('');
			$('#userDeactivationMsg').text('');
			$('#afterDeactivationDiv').hide();	
			$('#userDeactivationReasonDiv').hide();				
			var mobile = $('#mobileNumber').val();
			var res = validateMobileInput(mobile);
			if (res == true) {
				mob = mobile;
				createTableIfUserExist(mobile);
			} else {
				$('#mobileInputErrorDiv').show();
				$('#mobileInputError').text('enter valid phone no.');
			}
			$("#userMobile").val(mob);
		});
		
		


		
		$("#mobileNumber").keypress(function() {
			$('#mobileInputError').text('');
		});
		
		
	};
};

function validateMobileInput(mob) {
	var phoneRegEx = /^\d{10}$/;
	if (phoneRegEx.test(mob)) {
		mobInput = true;
		return true;
	} else {
		return false;
	}
}

function hideDivs() {
	$('#userDeactivationReasonDiv').hide();	
	$('#afterDeactivationDiv').hide();		
	$('#mobileInputErrorDiv').hide();
	$('#dropDownActionDiv').hide();
	$('#userInfoDiv').hide();
	$('#transInfoDiv').hide();
	$('#associatedUsersDiv').hide();
	$('#userSendNotifyDiv').hide();
}






function showDivs() {
	$('#userInfoDiv').show();
}

function createTableIfUserExist(mob) {
	var dataSet,resultSet;
	$('#tableTxnArea').text('');
	$('#tableArea').text('');

	$
			.get(
					'./getUserOtp/' + mob,
					function(data) {
						var user = data.userInfo;
						
						//alert(data.associatedList);
					    //dataSet=JSON.parse(data.associatedList);
						//resultSet=JSON.stringify(dataSet);
					    //alert(resultSet);
						if (user == null) {
							hideDivs();
							$('#mobileInputError').text('user not found');
							$('#mobileInputErrorDiv').show();
							return;
						} else {
							// Crate table html tag
							var table = $(
									"<table id='userInfotable' class='table table-striped table-bordered table-hover'></table>")
									.appendTo("#tableArea");
							// Create table header row
							var colNames = $("<thead></thead>").appendTo(table);
							var rowHeader = $("<tr></tr>").appendTo(colNames);
							
							$("<th></th").text("Mobile Number").appendTo(
									rowHeader);
							$("<th></th>").text("OTP").appendTo(rowHeader)
							$("<th></th>").text("Last Otp Gen. Date").appendTo(
									rowHeader)
							$("<th></th>").text("Is Expired").appendTo(
									rowHeader)
							$("<th></th>").text("Today OTP Count").appendTo(rowHeader);
							$("<th></th>").text("Total OTP Count").appendTo(rowHeader);
							$("<th></th>").text("CreatedOn").appendTo(rowHeader);

							// Create new row
							var tabBody = $("<tbody></tbody>").appendTo(table);
							var row = $("<tr></tr>").appendTo(tabBody);
							$("<td></td>").text(user.mobileNumber).appendTo(row);
							$("<td></td>").text(user.generatedOTP).appendTo(row);
							
							$("<td></td>").text(user.lastOTPGenDate).appendTo(row);
							
							$("<td id='isExpired'></td>")
									.text(
											function() {
												if (user.otpExpired == true) {
													return 'No';
												} else {
													return 'Yes';
												}
											}).appendTo(row);
							$("<td></td>").text(user.todayOTPCount).appendTo(row);
							$("<td></td>").text(user.totalOTPCount).appendTo(row);
							$("<td></td>").text(user.createdOn).appendTo(row);


					showDivs(user.enabled, user.credentialsNonExpired,user.accountNonExpired,user.accountNonLocked);
						}
						
					});
	
}


