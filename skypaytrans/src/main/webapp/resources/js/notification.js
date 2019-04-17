bluPay = new function() {
	var $instance = this;
	this.init = function(flag) {
		
				
		$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
			var token = $('input[name="_csrf"]').attr('value');
			      return jqXHR.setRequestHeader('X-CSRF-Token', token);			   
			});
		
		
		
		
		var users = [];
		$("#notifyAll").click(function(){
			
			users=[];
	            $.each($("input[name='options']:checked"), function(){            
	                users.push($(this).val());
	            });
	            if(users.length!=0){
	            	 $('#notifyALlModel').modal();
	            }else{
	            	alert("Please check atleast one user to notify");
	            }
		});
		
		
		
		
		
		
		
		var messageTextArea = $("#form_message");
	
        var notifyAllMessageTextArea=$("#form_message_all");

			var table = $('#bluePayUsersTable')
					.DataTable(
							{
								responsive : true,
								"destroy" : true,
								/*"order" : [ [ 0, "desc" ] ],*/
								"ajax" :{ 
									"url":"./getAllRegisteredBluPayUsers",
									error: function (xhr, httpStatusMessage, customErrorMessage) {
					                    if (xhr.status === 400) {
					                    	document.location.href="sessionout";
					                      
					                    }
					                   }
								},
								/*"bSort" : false,*/
								async:true,
								"columns" : [
										{
											"data" : null
										},
										{
											"data" : "firstName"
										},
										{
											"data" : "phNumber"
										},
										{
											"data" : "userType",
											render : function(data, type, row, meta) {
												if (data.includes('P')) {
													data = 'Registered';
												} else {
													data = 'Unregistered';
												}
												return data;
											}
										},
										
										{
											"data" : "createdOn"
										},
										
										{
											"data" : "fcmToken",
											render : function(data, type, row, meta) {
												if (data==null) {
													data='<input class="form-control input-lg" type="checkbox" name="options[]" value='+data+' disabled />'
												} else {
													data='<input class="form-control input-lg" type="checkbox" name="options" value='+data+' />'
												}
												return data;
											}
										},
										
										]
							});
			table.on('order.dt search.dt', function() {
				table.column(0, {
					search : 'applied',
					order : 'applied'
				}).nodes().each(function(cell, i) {
					cell.innerHTML = i + 1;
				});
			}).draw();
	

		
			var sendBtn = $("#sendMessageToAll").click(function() {
				if (notifyAllMessageTextArea.val().length == 0) {
					alert("Please enter a message to send");
				} else {
					$.ajax({
						type : "GET",
						url : "../admin/notifySelectedUsers",
						dataType : "text",
						async : false,
						data : {
							'message' : notifyAllMessageTextArea.val(),
							'tokens[]': users 
						},
						success : function(data) {
							Success = true;// doesnt goes here
							alert(data);
							 $('#notifyALlModel').modal('toggle');
							 
							 $("input[name='options']:checked").prop('checked', false);
						},
						error: function (xhr, httpStatusMessage, customErrorMessage) {
		                    if (xhr.status === 400) {
		                    	document.location.href="sessionout";
		                      
		                    }
		                   }

					});

				}

			});

		
		
		
		
		
		
		
		
		
		var sendBtn = $("#sendButton").click(function() {
			if (messageTextArea.val().length == 0) {
				alert("Please enter a message to send");
			} else {
				$.ajax({
					type : "POST",
					url : "../admin/notifyAll",
					dataType : "text",
					async : false,
					data : {
						'message' : messageTextArea.val(),
					
					},
					success : function(data) {
						Success = true;// doesnt goes here
						alert(data);
					},
					error: function (xhr, httpStatusMessage, customErrorMessage) {
	                    if (xhr.status === 400) {
	                    	document.location.href="sessionout";
	                      
	                    }
	                   }

				});

			}

		});
	}

}