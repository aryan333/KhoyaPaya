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
		
		
		
		
		$("#sendButton").click(function(){
			$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
				var token = $('input[name="_csrf"]').attr('value');
				return jqXHR.setRequestHeader('X-CSRF-Token', token);
			});
			
			var messageTextArea = $("#form_message");
			//var msg=messageTextArea.val();
			var mob=$("#userMobile").val();
			/*alert(mob);*/
			//alert(msg);
			
			if (messageTextArea.val().length == 0) {
				alert("Please enter a message to send");
			} else {
				$.ajax({
					type : "POST",
					url : "../admin/notifyAllIndividual/"+mob,
					dataType : "text",
					async : false,
					data : {
						'message' : messageTextArea.val()						
					},
					success : function(data) {
						Success = true;// doesnt goes here
						alert("message successfully sent");
					},
					error: function (xhr, httpStatusMessage, customErrorMessage) {
	                    if (xhr.status === 400) {
	                    	document.location.href="sessionout";
	                      
	                    }
	                   }

				});

			}
			
			
			
		});

		$("#deactivateBtn").click(function() {
			var action = $('#action').val();
			deactivate(mob, action);
		});
		
		$("#activateBtn").click(function() {
			$("#activatedLabel").text('');
			activate(mob);
		});
		
		$("#mobileNumber").keypress(function() {
			$('#mobileInputError').text('');
		});
		
		$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
			var token = $('input[name="_csrf"]').attr('value');
			return jqXHR.setRequestHeader('X-CSRF-Token', token);
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

function deactivate(mobile, act) {
	$.ajax({
		type : "POST",
		url : "../admin/deactivateUser",
		async : true,
		data : {
			'action' : act,
			'phone' : mobile
		},
		success : function(data) {			
			$('#dropDownActionDiv').hide();
			$('#afterDeactivationDiv').show();
			$('#afterDeactivationReason').text(function() {
				switch (act) {
				case "disable":
					return 'Disabled';
					break;
				case "lock":
					return 'Account Locked';
					break;
				case "expAcc":
					return 'Account Expired';
					break;
				case "expCred":
					return 'Credentials Expired';
					break;
				default:
					return 'Reason Not found'.
					break;
				}
			});
			$('#isActiveCol').text('No');
		},
		error: function (xhr, httpStatusMessage, customErrorMessage) {
            if (xhr.status === 400) {
            	document.location.href="sessionout";
              
            }
           }

	});

}

function activate(mobile) {

	$.ajax({
		type : "POST",
		url : "../admin/activateUser",
		async : true,
		data : {
			'field' : deactivationReason,
			'phone' : mobile
		},
		success : function(data) {
			$('#userDeactivationReasonDiv').hide();			
			$('#dropDownActionDiv').show();			
			alert('user is activated successfully');
			$('#isActiveCol').text('Yes');
		},
		error : function(textStatus, errorThrown) {
			alert('some error occurs. please try after sometime.')
		}

	});

}


function showDivs() {
	if (isUserDeactivatedAlready(arguments[0], arguments[1], arguments[2],
			arguments[3]) == true) {
		$('#userDeactivationReasonDiv').show();
		$('#deactivationReason').text(deactivationReason);		
		$('#dropDownActionDiv').hide();
	} else {
		$('#dropDownActionDiv').show();
		$('#userDeactivationReasonDiv').hide();
	}
	
	$('#userSendNotifyDiv').show();
	$('#userInfoDiv').show();
	$('#transInfoDiv').show();
	$('#associatedUsersDiv').show();
}

function createTableIfUserExist(mob) {
	var dataSet,resultSet;
	$('#tableTxnArea').text('');
	$('#tableArea').text('');

	$
			.get(
					'./findUser/' + mob,
					function(data) {
						var user = data.userInfo;
						var trans = data.transInfo;
						//alert(data.associatedList);
					    //dataSet=JSON.parse(data.associatedList);
						dataSet=data.data;
						transDataSet=data.txnDtoList;
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
							$("<th></th>").text("Name").appendTo(rowHeader);
							$("<th></th").text("Phone Number").appendTo(
									rowHeader);
							$("<th></th>").text("Email Id").appendTo(rowHeader)
							$("<th></th>").text("Invite Code").appendTo(
									rowHeader)
							$("<th></th>").text("Last Transaction Date").appendTo(
									rowHeader)
							$("<th></th>").text("Total Transactions").appendTo(
									rowHeader)
							$("<th></th>").text("isActive").appendTo(rowHeader);

							// Create new row
							var tabBody = $("<tbody></tbody>").appendTo(table);
							var row = $("<tr></tr>").appendTo(tabBody);
							$("<td></td>").text(user.firstName).appendTo(row);
							$("<td></td>").text(user.phNumber).appendTo(row);
							$("<td></td>").text(function() {
								if (user.userDetailDTO.emailId == null) {
									return 'Not Available';
								} else {
									return user.userDetailDTO.emailId;
								}
							}).appendTo(row);
							$("<td></td>").text(user.inviteCode).appendTo(row);
							$("<td></td>").text(trans.lastTransacted).appendTo(row);
							$("<td></td>").text(trans.totalTrans).appendTo(row);
							$("<td id='isActiveCol'></td>")
									.text(
											function() {
												if (isUserDeactivatedAlready(
														user.enabled,
														user.credentialsNonExpired,
														user.accountNonExpired,
														user.accountNonLocked) == true) {
													return 'No';
												} else {
													return 'Yes';
												}
											}).appendTo(row);

							var tableTxn = $(
									"<table id='transStatsTable' class='table table-striped table-bordered table-hover'></table>")
									.appendTo("#tableTxnArea");
							// Create table header row
							var colNamesTxn = $("<thead></thead>").appendTo(
									tableTxn);
							var rowHeaderTxn = $("<tr></tr>").appendTo(
									colNamesTxn);
							$("<th></th>").text("Total Transactions").appendTo(
									rowHeaderTxn);
							
							$("<th></th>").text("Total Buisness Transactions").appendTo(
									rowHeaderTxn);
							$("<th></th>").text("Total Friend Transactions").appendTo(
									rowHeaderTxn);
							$("<th></th").text("Total Amount").appendTo(
									rowHeaderTxn);
							$("<th></th>").text("Total Pending  Transactions")
									.appendTo(rowHeaderTxn)
							$("<th></th>").text("Total Accepted Transactions").appendTo(
									rowHeaderTxn);
							$("<th></th>").text("Total Rejected Transactions").appendTo(
									rowHeaderTxn);
							$("<th></th>").text("Total Pending Amount")
									.appendTo(rowHeaderTxn)

							
							// Create new row
							var tabBodyTxn = $("<tbody></tbody>").appendTo(
									tableTxn);
							var rowTxn = $("<tr></tr>").appendTo(tabBodyTxn);
							$("<td></td>").text(trans.totalTrans).appendTo(
									rowTxn);
							$("<td></td>").text(trans.totalBusinessTransaction).appendTo(
									rowTxn);
							$("<td></td>").text(trans.totalFriendTransactons).appendTo(
									rowTxn);
							$("<td></td>").text(function() {
								if (trans.totalTransAmount == null) {
									return '0.00';
								} else {
									return trans.totalTransAmount;
								}
							}).appendTo(rowTxn);
							$("<td></td>").text(trans.totalPendingTrans)
									.appendTo(rowTxn);
							$("<td></td>").text(trans.totalAccepted)
							.appendTo(rowTxn);
							$("<td></td>").text(trans.totalRejected)
							.appendTo(rowTxn);
							$("<td></td>").text(function() {
								if (trans.totalPendingTransAmount == null) {
									return '0.00';
								} else {
									return trans.totalPendingTransAmount;
								}
							}).appendTo(rowTxn);
							showDivs(user.enabled, user.credentialsNonExpired,
									user.accountNonExpired,
									user.accountNonLocked);
						}
						createDataTable(dataSet);
						createTransactionTable(transDataSet);
					});
	
}

function isUserDeactivatedAlready() {
	if (arguments[0] == false) {
		deactivationReason = 'Disabled'
		return true;
	} else if (arguments[1] == false) {
		deactivationReason = 'Credentials Expired'
		return true;
	} else if (arguments[2] == false) {
		deactivationReason = 'Account Expired'
		return true;
	} else if (arguments[3] == false) {
		deactivationReason = 'Account Locked'
		return true;
	}else{
		return false;
	}

}

function createDataTable(dataSet){	
	var i=0;
	
	//var result = [{"createdOn":"test","createdBy":"test2","modifiedOn":"test3","modifiedBy":"test4","userId":"5","name":"suraj","phNumber":"test","balance":"test","emailId":"test","address":"","blobId":"","userType":"","gstin":"","mapped":"","mappedName":"","active":"","relation":""}];
	  var table=  $('#associatedUsers').DataTable( {
	    	data: dataSet,
	    	"bSort":false,
			"destroy" : true,
			dom : 'lBfrtip',
			buttons : [ 'copyHtml5', 'excelHtml5', 'csvHtml5',
					'pdfHtml5' ],
			"order" : [ [ 0, "desc" ] ],			
	        columns: [	
	        	{ "data": null,
	        		
	        		render : function(data, type, row, meta) {
	        			i++;
						
						return i;
					}
	        	
	        	
	        	
	        	
	        	
	        	},
	        	{ "data": "name" },
	            { "data": "mappedName" },
	            { "data": "phNumber" },
	            { "data": "balance" ,
	            	
	            	render : function(data, type, row, meta) {
						var skyCredit = data;
						if (skyCredit.includes('-')) {
							data ='<span style="color: red;">' +skyCredit.replace('-','')+'</span>'
						}else{
							data='<span style="color:#2ad02a;">' +skyCredit+'</span>'
						}

						return data;
					}   
                
	            
	            
	            
	            },
	        	{ "data": "active" },
	            { "data": "relation" },
	            { "data": "userType",
	            	
	            	render : function(data, type, row, meta) {
						var userType = data;
						if (userType.includes('P')
								) {
							data ='Registered User' ;
						}else{
							data='Guest User';
						}

						return data;
					} 
	            
	            
	            
	            
	            },
	            { "data": "createdOn" }
	        
	        ]
	    } );
	
}

function createTransactionTable(transDataSet){
var table=  $('#transList').DataTable( {
	 data: transDataSet,
	 responsive :true,
	 "scrollX": true,
 	"bSort":false,
		"destroy" : true,
		dom : 'lBfrtip',
		buttons : [ 'copyHtml5', 'excelHtml5', 'csvHtml5',
				'pdfHtml5' ],
		"order" : [ [ 0, "desc" ] ],	
   columns: [
       { "data": "transactionId" },
       { "data": "payerName",
       	render : function(data, type, row, meta) {
				payerName = data;
				
				return data;
			}
       
       },
       { "data": "payeeName",
       	render : function(data, type, row, meta) {
				payeeName = data;
				
				return data;
			}
       
       
       
       },
       { "data": "paymentStatus",
       		
       	render : function(data, type, row, meta) {
				var paymentStatus = data;
				if (paymentStatus.includes('P')
						) {
					data ='Pending' ;
				} else if (paymentStatus.includes('A')) {
					data = 'Accepted';
				}else if(paymentStatus.includes('R')){
					data='Rejected';
				}else{
					data='Closed';
				}

				return data;
			}
       },
       { "data": "cashReceived" },
       { "data": "skyCredit",
       	render : function(data, type, row, meta) {
				var skyCredit = data+'';
				if (skyCredit.includes('-')
						) {
					data ='<span title="'+payerName+' get from '+payeeName+'" style="color:red;">' +skyCredit.replace('-','')+'</span>'
				}else{
					data='<span title="'+payerName+' owe to '+payeeName+'" style="color:#2ad02a;">' +skyCredit+'</span>'
				}

				return data;
			}   
       
       
       
       
       
       
       },
       { "data": "billAmount" },
       
       { "data": "gstEnabled",
   		
       	render : function(data, type, row, meta) {
				var gstEnabled = data;
				console.log(gstEnabled);
				if (gstEnabled==true) {
					data ='YES' ;
				
				}else{
					data='NO';
				}

				return data;
			}
       },
       { "data": "userType",
       	
       	render : function(data, type, row, meta) {
				var userType = data;
				if (userType.includes('P')
						) {
					data ='Registered User' ;
				}else{
					data='Guest User';
				}

				return data;
			}   
       
       },
       
       { "data": "createdOn" },
       { "data": "modifiedOn" }
   ]
} );

}
