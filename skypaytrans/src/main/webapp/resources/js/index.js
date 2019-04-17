jQuery(document)
		.ready(
				function() {
					
					
					var dateRange;
					var userType;
					

					$('.modal').on('hidden.bs.modal', function (e) {
					    if($('.modal').hasClass('in')) {
					    $('body').addClass('modal-open');
					    }    
					});
					/*
					 * $.ajaxPrefilter(function(options, originalOptions, jqXHR) {
					 * var token = $('input[name="_csrf"]').attr('value');
					 * return jqXHR.setRequestHeader('X-CSRF-Token', token); });
					 */
					ajaxCallForDashboardEzkhataStats("../admin/dashboardrest?testUser=true"
							);
					
					var cb = $('#testUserChkBox');
					var payerName,payeeName;
					$('input')
							.change(
									function() {
										var $input = $(this);
										testUser = $input
												.prop("checked");
										ajaxCallForDashboardEzkhataStats("../admin/dashboardrest?testUser="
												+ testUser);
									});

					// use class noExl on the column which you dont want to import

					$('#exportBtn').on('click', function(e) {
						e.preventDefault();
						ResultsToTable();
					});
					
					
					//  view blupay users on map through dash button
					
					$('.multipleMarkersMapBtnDash').on('click', function(e) {
						//document.location.href = "map";
						dateRange=$(this).attr('dateRange');
						 window.open('map/'+dateRange+'', '_blank');
					});
					
					
					// //  view blupay users on map through modal button
					
					$('.multipleMarkersMapBtnModal').on('click', function(e) {
					
						//document.location.href = "map";
						 window.open('map/'+dateRange+'', '_blank');
					});
					  
					// // view blupay users by sp on map through modal button
					
					
					$('#allUserBySPInDateRangeMapBtnModal').on('click', function(e) {
						//document.location.href = "map";
						 window.open('registeredreferaluserbyspInDaterangemap/'+dateRange+'?userType='+userType+'', '_blank');
					});
					
					
					// to get csrf token from jsp file
					$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
						var token = $('input[name="_csrf"]').attr('value');
						return jqXHR.setRequestHeader('X-CSRF-Token', token);
					});
					
					$(".modal-body").on('click','.view',function(){
						var mobile = $(this).attr('mobile');
					createTableIfUserExist(mobile);
					$('#userTransDetail').modal('show');
					
						
					});
					
					// Ajax request to send email of dashboard data 
					$('#sendMail')
							.on(
									'click',
									function(e) {

										var statsTable = $("#statsTable");

										$
												.ajax({
													type : "POST",
													url : "../admin/sendMail/",
													dataType:"text",
													async : false,
													data : {

														'table' : statsTable
																.html()
													},
													success : function(data) {
													alert("Email sent successfully");
													},
													error : function(xhr,
															httpStatusMessage,
															customErrorMessage) {
														
															document.location.href = "sessionout";

														
													}

												});

									});

					
					
					
					

					function ResultsToTable() {
						$("#statsTable").table2excel({
							exclude : ".noExl",
							name : "Ezkhata Statistics",
							filename : "Ezkhata Statistics"
						});
					}
					
					$('#totalActiveUsersBtn').on('click', function() {						
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForActiveOrInactiveUsers("../admin/getActiveUsers/all?testUser="
								+ includeTestUsers);
						

					});

					$('#todayActiveUsersBtn').on('click', function() {						
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForActiveOrInactiveUsers("../admin/getActiveUsers/today?testUser="
								+ includeTestUsers);

					});

					$('#yesterdayActiveUsersBtn').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForActiveOrInactiveUsers('../admin/getActiveUsers/yest?testUser='
								+ includeTestUsers);

					});

					$('#activeUsersInWeekDateRangeBtn').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForActiveOrInactiveUsers('../admin/getActiveUsers/week?testUser='
								+ includeTestUsers);

					});

					$('#activeUsersInDateRangeBtn').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForActiveOrInactiveUsers('../admin/getActiveUsers/start?testUser='
								+ includeTestUsers);

					});
					
					
					$('#totalUsersBtn').on('click', function() {						
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForOnBoardedUsers("../admin/getOnBoardedUsers/all?testUser="
								+ includeTestUsers);
						dateRange=$(this).attr('dateRange');

					});
					
					$('#todayUsersBtn').on('click', function() {						
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForOnBoardedUsers("../admin/getOnBoardedUsers/today?testUser="
								+ includeTestUsers);
						dateRange=$(this).attr('dateRange');
						
					});

					$('#yesterdayUsersBtn').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForOnBoardedUsers('../admin/getOnBoardedUsers/yest?testUser='
								+ includeTestUsers);
						dateRange=$(this).attr('dateRange');

					});

					$('#usersInWeekDateRangeBtn').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForOnBoardedUsers('../admin/getOnBoardedUsers/week?testUser='
								+ includeTestUsers);
						dateRange=$(this).attr('dateRange');

					});

					$('#usersInDateRangeBtn').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForOnBoardedUsers('../admin/getOnBoardedUsers/start?testUser='
								+ includeTestUsers);
						dateRange=$(this).attr('dateRange');

					});
					
					$('#totalBlockedUsersBtn').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForActiveOrInactiveUsers('../admin/getInactiveUsers/all?testUser='
								+ includeTestUsers);

					});
					
					$('#blockedUsersTodayBtn').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForActiveOrInactiveUsers('../admin/getInactiveUsers/today?testUser='
								+ includeTestUsers);
						
					});
					
					$('#totalYesterdayBlockedUsersBtn').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForActiveOrInactiveUsers('../admin/getInactiveUsers/yest?testUser='
								+ includeTestUsers);
						
					});
					$('#blockedUsersInWeekDateRangeBtn').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForActiveOrInactiveUsers('../admin/getInactiveUsers/week?testUser='
								+ includeTestUsers);
						
					});
					
					$('#blockedUsersInDateRangeBtn').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForActiveOrInactiveUsers('../admin/getInactiveUsers/start?testUser='
								+ includeTestUsers);
						
					});
					
					$('#totalAccTransToday').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=today&paymentStatus=A&testUser='
								+ includeTestUsers);
						
					});
					$('#totalAccTransYesterday').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=yest&paymentStatus=A&testUser='
								+ includeTestUsers);
						
					});
					$('#totalAccTransInWeekRange').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=week&paymentStatus=A&testUser='
								+ includeTestUsers);
						
					});
					$('#totalAccTransInDateRange').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=start&paymentStatus=A&testUser='
								+ includeTestUsers);
						
					});
					$('#totalRejTransToday').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=today&paymentStatus=R&testUser='
								+ includeTestUsers);
						
					});
					$('#totalRejTransYesterday').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=yest&paymentStatus=R&testUser='
								+ includeTestUsers);
						
					});
					$('#totalRejTransInWeekRange').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=week&paymentStatus=R&testUser='
								+ includeTestUsers);
						
					});
					$('#totalRejTransInDateRange').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=start&paymentStatus=R&testUser='
								+ includeTestUsers);
						
					});
					$('#totalPenTransToday').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=today&paymentStatus=P&testUser='
								+ includeTestUsers);
						
					});
					$('#totalPenTransYesterday').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=yest&paymentStatus=P&testUser='
								+ includeTestUsers);
						
					});
					$('#totalPenTransInWeekRange').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=week&paymentStatus=P&testUser='
								+ includeTestUsers);
						
					});
					$('#totalPenTransInDateRange').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=start&paymentStatus=P&testUser='
								+ includeTestUsers);
						
					});
					$('#totalCloTransToday').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=today&paymentStatus=C&testUser='
								+ includeTestUsers);
						
					});
					$('#totalCloTransYesterday').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=yest&paymentStatus=C&testUser='
								+ includeTestUsers);
						
					});
					$('#totalCloTransInWeekRange').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=week&paymentStatus=C&testUser='
								+ includeTestUsers);
						
					});
					$('#totalCloTransInDateRange').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=start&paymentStatus=C&testUser='
								+ includeTestUsers);
						
					});
					
					$('#totalTransToday').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=today&paymentStatus=all&testUser='
								+ includeTestUsers);
						
					});
					$('#totalTransYesterday').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=yest&paymentStatus=all&testUser='
								+ includeTestUsers);
						
					});
					$('#totalTransInWeekRange').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=week&paymentStatus=all&testUser='
								+ includeTestUsers);
						
					});
					$('#totalTransInDateRange').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=start&paymentStatus=all&testUser='
								+ includeTestUsers);
						
					});
					$('#totalTrans').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=all&paymentStatus=all&testUser='
								+ includeTestUsers);
						
					});
					$('#totalAccTrans').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=all&paymentStatus=A&testUser='
								+ includeTestUsers);
						
					});
					$('#totalRejTrans').on('click', function() {
				
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=all&paymentStatus=R&testUser='
								+ includeTestUsers);
						
					});
					$('#totalPenTrans').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=all&paymentStatus=P&testUser='
								+ includeTestUsers);
						
					});
					$('#totalCloTrans').on('click', function() {
						var includeTestUsers =cb.prop("checked");
						setDataTableBodyForTransactions('../admin/getTransactions?period=all&paymentStatus=C&testUser='
								+ includeTestUsers);
						
					});
					
					
					

					//below function provide all users that are registered by  all salesman  in period like today, yes, weekend , ......


					$('.bySalesPersonTotalUsersBtn').click( function() {	
						
						

						var field = $('.modal-title1');
						 
						      $("#onTotalReferalUsersLabel").text("Total Registered User By SalesPerson  ");
						
						userType='P';
						
						dateRange=$(this).attr('dateRange');
						      
						setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/all?userType='+userType);

					});


					$('.bySalesPersonTodayUsersBtn').click( function() {
						
						var field = $('.modal-title1');
						 
					    $("#onTotalReferalUsersLabel").text("Total Registered User By SalesPerson  ");
						
						var userType='P';
						dateRange=$(this).attr('dateRange');
					    
						setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/today?userType='+userType);

					});


					$('.bySalesPersonYesterdayUsersBtn').click( function() {
						
						
						var field = $('.modal-title1');
						 
					    $("#onTotalReferalUsersLabel").text("Total Registered User By SalesPerson  ");
						
						var userType='P';
						dateRange=$(this).attr('dateRange');
					    
						setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/yest?userType='+userType);

					});



					$('.bySalesPersonInWeekRangeUsersBtn').click( function() {	
						
						
						var field = $('.modal-title1');
						 
					    $("#onTotalReferalUsersLabel").text("Total Registered User By SalesPerson  ");
						
						var userType='P';
						dateRange=$(this).attr('dateRange');
					    
						setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/week?userType='+userType);

					});

					$('.bySalesPersonInDateRangeUsersBtn').click( function() {	
						
						
						var field = $('.modal-title1');
						 
					    $("#onTotalReferalUsersLabel").text("Total Registered User By SalesPerson  ");
						
						var userType='P';
						dateRange=$(this).attr('dateRange');
					    
						setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/start?userType='+userType);

					});
					
					
					
					
					
					
					
					//below function provide All  users that are registered by all salesman in period like today, yes, weekend , ...... it is make table and calling above

					function setDataTableBodyForAllReferalUserBySP(url) {

						var table = $('#onTotalReferalUsersTable').DataTable({
							responsive : true,
							"pageLength": 100,
							"destroy" : true,
							dom : 'lBfrtip',							
								  buttons: [
								       {
								           extend: 'pdf',
								           footer: true,
								           exportOptions: {
								                columns: [1,2,3,4,5]
								            }
								       },
								       {
								           extend: 'csv',
								           footer: false,
								           exportOptions: {
								                columns: [1,2,3,4,5]
								            }
								          
								       },
								       {
								           extend: 'excel',
								           footer: false,
								           exportOptions: {
								                columns: [1,2,3,4,5]
								            }
								       }         
								    ]  
								     ,
							"order" : [ [ 4, "desc" ] ],
							"ajax" : {
								"url":url,
								
								error: function (xhr, httpStatusMessage, customErrorMessage) {
					                if (xhr.status === 400) {
					                	document.location.href="sessionout";
					                  
					                }
					               }
							},
							/* "bSort" : false, */
							async : true,
							"columns" : [ {
								"data" : null
							},{
								"data" : "salesPersonName"	
							}, {
								"data" : "firstName"
							}, {
								"data" : "phNumber"
							},{
								"data" : "createdOn"
							
							},{
								data : "longitude",
								render :function(data,type,row){
									var lat = row.latitude, lng = row.longitude;									
									if (lat == null) {
										data = "Not Available";
									} else if (!lat.includes('0.0')
											&& !lng.includes('0.0')) {
										data = '<a href="http://maps.google.com/maps?q=loc:'
												+ lat
												+ ','
												+ lng
												+ '" target="_blank">View on Map</a>';
									} else {
										data = 'Not Available';
									}

									return data;
								}
								 
							},{
								"data":"phNumber",
								
								render :function(data,type,row){
									var mobile = row.phNumber;									
									data='<a href="#" mobile="'+mobile+'" class="view"><i class="fa fa-info-circle"></i></a>'

									return data;
								}
							}
							
							
							
							
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
					};




					function setDataTableBodyForActiveOrInactiveUsers(url) {

						var table = $('#usersListTable').DataTable({
							responsive : true,
							"pageLength": 100,
							"destroy" : true,
							dom : 'lBfrtip',
							buttons: [
							       {
							           extend: 'pdf',
							           footer: true,
							           exportOptions: {
							                columns: [1,2]
							            }
							       },
							       {
							           extend: 'csv',
							           footer: false,
							           exportOptions: {
							                columns: [1,2]
							            }
							          
							       },
							       {
							           extend: 'excel',
							           footer: false,
							           exportOptions: {
							                columns: [1,2]
							            }
							       }         
							    ]  ,
							"order" : [ [ 0, "desc" ] ],
							"ajax" :{
								"url":url,
								error: function (xhr, httpStatusMessage, customErrorMessage) {
				                    if (xhr.status === 400) {
				                    	document.location.href="sessionout";
				                      
				                    }
				                   }
							},
							/* "bSort" : false, */
							async : true,
							"columns" : [ {
								"data" : null
							}, {
								"data" : "firstName"
							}, {
								"data" : "phNumber"
							}]

						});
						table.on('order.dt search.dt', function() {
							table.column(0, {
								search : 'applied',
								order : 'applied'
							}).nodes().each(function(cell, i) {
								cell.innerHTML = i + 1;
							});
						}).draw();
					};
					
					function setDataTableBodyForOnBoardedUsers(url) {

						var table = $('#onBoardedUsersListTable').DataTable({
							responsive : true,
							"pageLength": 100,
							"destroy" : true,
							dom : 'lBfrtip',							
								  buttons: [
								       {
								           extend: 'pdf',
								           footer: true,
								           exportOptions: {
								                columns: [1,2,3,4]
								            }
								       },
								       {
								           extend: 'csv',
								           footer: false,
								           exportOptions: {
								                columns: [1,2,3,4]
								            }
								          
								       },
								       {
								           extend: 'excel',
								           footer: false,
								           exportOptions: {
								                columns: [1,2,3,4]
								            }
								       }         
								    ]  
								     ,
							"order" : [ [ 3, "desc" ] ],
							"ajax" : {
								"url":url,
								error: function (xhr, httpStatusMessage, customErrorMessage) {
				                    if (xhr.status === 400) {
				                    	document.location.href="sessionout";
				                      
				                    }
				                   }
							},
							/* "bSort" : false, */
							async : true,
							"columns" : [ {
								"data" : null
							}, {
								"data" : "firstName"
							}, {
								"data" : "phNumber"
							},{
								"data" : "createdOn"
							},{
								data : "longitude",
								render :function(data,type,row){
									var lat = row.latitude, lng = row.longitude;									
									if (lat == null) {
										data = "Not Available";
									} else if (!lat.includes('0.0')
											&& !lng.includes('0.0')) {
										data = '<a href="http://maps.google.com/maps?q=loc:'
												+ lat
												+ ','
												+ lng
												+ '" target="_blank">View on Map</a>';
									} else {
										data = 'Not Available';
									}

									return data;
								}
								 
							},
							{
								"data":"phNumber",
								
								render :function(data,type,row){
									var mobile = row.phNumber;									
									data='<a href="#" mobile="'+mobile+'" class="view"><i class="fa fa-info-circle"></i></a>'

									return data;
								}
								
								
								
								
								
							}]

						});
						table.on('order.dt search.dt', function() {
							table.column(0, {
								search : 'applied',
								order : 'applied'
							}).nodes().each(function(cell, i) {
								cell.innerHTML = i + 1;
							});
						}).draw();
					};
					
					function setDataTableBodyForTransactions(url) {
                     
						var table = $('#transactionsListTable').DataTable({
							responsive : true,
							"pageLength": 100,
							"destroy" : true,
							dom : 'lBfrtip',
							buttons: [
							       {
							           extend: 'pdf',
							           footer: true,
							           exportOptions: {
							                columns: [1,2,3,4,5,6,7,8,9]
							            }
							       },
							       {
							           extend: 'csv',
							           footer: false,
							           exportOptions: {
							                columns: [1,2,3,4,5,6,7,8,9]
							            }
							          
							       },
							       {
							           extend: 'excel',
							           footer: false,
							           exportOptions: {
							                columns: [1,2,3,4,5,6,7,8,9]
							            }
							       }         
							    ]  ,
							"order" : [ [ 9, "desc" ] ],
							"ajax" : {
								"url":url,
								error: function (xhr, httpStatusMessage, customErrorMessage) {
				                    if (xhr.status === 400) {
				                    	document.location.href="sessionout";
				                      
				                    }
				                   }
							},
							/* "bSort" : false, */
							async : true,
							"columns" : [ {
								"data" : null
							}, {
								"data" : "payerName",
								render : function(data, type, row, meta) {
									payerName = data;
									
									return data;
								}
							}, {
								"data" : "payeeName",
								render : function(data, type, row, meta) {
									payeeName = data;
									
									return data;
								}
							},{
								"data" : "billAmount"							
							},{
								"data" : "cashReceived"
							},{
								"data" : "skyCredit",
								render : function(data, type, row, meta) {
									var skyCredit = data;
									if (skyCredit.includes('-')
											) {
										data ='<span title="'+payerName+' get from '+payeeName+'" style="color: red;">' +skyCredit.replace('-','')+'</span>'
									}else{
										data='<span title="'+payerName+' owe to '+payeeName+'" style="color:#2ad02a;">' +skyCredit+'</span>'
									}

									return data;
								}   
			                
							},{
								"data" : "paymentStatus",
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
							},{
								"data" : "gstenabled",
								render : function(data, type, row, meta) {
									var gstEnabled = data;
									
									if (gstEnabled==true) {
										data ='<span style="color:green;font-weight: bold;">YES</span>' ;
									
									}else{
										data='NO';
									}

									return data;
								}
							},{
								"data" : "transactionType"
							},{
								"data" : "createdOn"
							}
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
					}
					;

					function ajaxCallForDashboardEzkhataStats(url) {
						$.ajax({
							"type" : "GET",
							"url" : url,
							
							async : true,
							success : function(data) {
								var userStats = data.userStats;
								var transStats = data.transStats;
								var spreferalStats=data.spreferalStats;
								
								setUserCountStats(userStats.totalUsers,
										userStats.todayUsers,
										userStats.yesterdayUsers,
										userStats.usersInWeekDateRange,
										userStats.usersInDateRange);
								
								setSpReferalCountStas(spreferalStats.overAllTotalCount,
										spreferalStats.totalCurrentDateCount,
										spreferalStats.totalYesterdayDateCount,
										spreferalStats.totalWeekCount,
										spreferalStats.totalUptoDateCount);
										
								setActiveUsersCount(userStats.totalActiveUsers,userStats.todayActiveUsers,
										userStats.yesterdayActiveUsers,
										userStats.activeUsersInWeekDateRange,
										userStats.activeUsersInDateRange);
								setBlockedUsersCount(
										userStats.totalBlockedUsers,
										userStats.blockedUsersToday,
										userStats.totalYesterdayBlockedUsers,
										userStats.blockedUsersInWeekDateRange,
										userStats.blockedUsersInDateRange);
								setTransactionsCount(transStats);
								setTransactionsValue(transStats);

							},
							error: function (xhr, httpStatusMessage, customErrorMessage) {
			                    if (xhr.status === 400) {
			                    	document.location.href="sessionout";
			                      
			                    }
			                   }
						});
					}

					function setUserCountStats(totalUsers, todayUsers,
							yesterdayUsers, usersInWeekDateRange,
							usersInDateRange) {
						$('#totalUsersBtn').text(totalUsers);
						$('#todayUsersBtn').text(todayUsers);
						$('#yesterdayUsersBtn').text(yesterdayUsers);
						$('#usersInWeekDateRangeBtn')
								.text(usersInWeekDateRange);
						$('#usersInDateRangeBtn').text(usersInDateRange);

					}

					function setActiveUsersCount(totalActiveUsers,todayActiveUsers,
							yesterdayActiveUsers, activeUsersInWeekDateRange,
							activeUsersInDateRange) {
						$('#totalActiveUsersBtn').text(totalActiveUsers);
						$('#todayActiveUsersBtn').text(todayActiveUsers);
						$('#yesterdayActiveUsersBtn')
								.text(yesterdayActiveUsers);
						$('#activeUsersInWeekDateRangeBtn').text(
								activeUsersInWeekDateRange);
						$('#activeUsersInDateRangeBtn').text(
								activeUsersInDateRange);
					}
					
					function setSpReferalCountStas(overAllTotalCount,
							totalCurrentDateCount,
							totalYesterdayDateCount,
							totalWeekCount,
							totalUptoDateCount) {
						
						$('.bySalesPersonTotalUsersBtn').text(overAllTotalCount);
						$('.bySalesPersonTodayUsersBtn').text(totalCurrentDateCount);
						$('.bySalesPersonYesterdayUsersBtn')
								.text(totalYesterdayDateCount);
						$('.bySalesPersonInWeekRangeUsersBtn').text(
								totalWeekCount);
						$('.bySalesPersonInDateRangeUsersBtn').text(
								totalUptoDateCount);
					}

					function setBlockedUsersCount(totalBlockedUsers,
							blockedUsersToday, totalYesterdayBlockedUsers,
							blockedUsersInWeekDateRange,
							blockedUsersInDateRange) {
						$('#totalBlockedUsersBtn').text(totalBlockedUsers);
						$('#blockedUsersTodayBtn').text(blockedUsersToday);
						$('#totalYesterdayBlockedUsersBtn').text(
								totalYesterdayBlockedUsers);
						$('#blockedUsersInWeekDateRangeBtn').text(
								blockedUsersInWeekDateRange);
						$('#blockedUsersInDateRangeBtn').text(
								blockedUsersInDateRange);
					}

					function setTransactionsCount(transStats) {
						var totalTrans = transStats.acceptedTxnsToday
								+ transStats.rejectedTxnsToday
								+ transStats.closedTxnsToday
								+ transStats.pendingTxnsToday
								+ transStats.acceptedTxnsYesterday
								+ transStats.rejectedTxnsYesterday
								+ transStats.pendingTxnsYesterday
								+ transStats.closedTxnsYesterday
								+ transStats.acceptedTxnsInWeekRange
								+ transStats.rejectedTxnsInWeekRange
								+ transStats.closedTxnsInWeekRange
								+ transStats.pendingTxnsInWeekRange
								+ transStats.acceptedTxnsInDateRange
								+ transStats.rejectedTxnsInDateRange
								+ transStats.closedTxnsInDateRange
								+ transStats.pendingTxnsInDateRange;
						var totalTransToday = transStats.acceptedTxnsToday
								+ transStats.rejectedTxnsToday
								+ transStats.closedTxnsToday
								+ transStats.pendingTxnsToday;
						var totalTransYesterday = transStats.acceptedTxnsYesterday
								+ transStats.rejectedTxnsYesterday
								+ transStats.pendingTxnsYesterday
								+ transStats.closedTxnsYesterday;
						var totalTransInWeekRange = transStats.acceptedTxnsInWeekRange
								+ transStats.rejectedTxnsInWeekRange
								+ transStats.closedTxnsInWeekRange
								+ transStats.pendingTxnsInWeekRange;
						var totalTransInDateRange = transStats.acceptedTxnsInDateRange
								+ transStats.rejectedTxnsInDateRange
								+ transStats.closedTxnsInDateRange
								+ transStats.pendingTxnsInDateRange;
						var totalAccTrans = transStats.acceptedTxnsToday
								+ transStats.acceptedTxnsYesterday
								+ transStats.acceptedTxnsInWeekRange
								+ transStats.acceptedTxnsInDateRange;
						var totalRejTranstrans = transStats.rejectedTxnsToday
								+ transStats.rejectedTxnsYesterday
								+ transStats.rejectedTxnsInWeekRange
								+ transStats.rejectedTxnsInDateRange;
						var totalPenTransStats = transStats.pendingTxnsToday
								+ transStats.pendingTxnsYesterday
								+ transStats.pendingTxnsInWeekRange
								+ transStats.pendingTxnsInDateRange;

						var totalCloTrans = transStats.closedTxnsToday
								+ transStats.closedTxnsYesterday
								+ transStats.closedTxnsInWeekRange
								+ transStats.closedTxnsInDateRange

						$('#totalTrans').text(totalTrans);
						$('#totalTransToday').text(totalTransToday);
						$('#totalTransYesterday').text(totalTransYesterday);
						$('#totalTransInWeekRange').text(totalTransInWeekRange);
						$('#totalTransInDateRange').text(totalTransInDateRange);
						$('#totalAccTrans').text(totalAccTrans);
						$('#totalAccTransToday').text(
								transStats.acceptedTxnsToday);
						$('#totalAccTransYesterday').text(
								transStats.acceptedTxnsYesterday);
						$('#totalAccTransInWeekRange').text(
								transStats.acceptedTxnsInWeekRange);
						$('#totalAccTransInDateRange').text(
								transStats.acceptedTxnsInDateRange);
						$('#totalRejTrans').text(totalRejTranstrans);
						$('#totalRejTransToday').text(
								transStats.rejectedTxnsToday);
						$('#totalRejTransYesterday').text(
								transStats.rejectedTxnsYesterday);
						$('#totalRejTransInWeekRange').text(
								transStats.rejectedTxnsInWeekRange);
						$('#totalRejTransInDateRange').text(
								transStats.rejectedTxnsInDateRange);
						$('#totalPenTrans').text(totalPenTransStats);
						$('#totalPenTransToday').text(
								transStats.pendingTxnsToday);
						$('#totalPenTransYesterday').text(
								transStats.pendingTxnsYesterday);
						$('#totalPenTransInWeekRange').text(
								transStats.pendingTxnsInWeekRange);
						$('#totalPenTransInDateRange').text(
								transStats.pendingTxnsInDateRange);
						$('#totalCloTrans').text(totalCloTrans);
						$('#totalCloTransToday').text(
								transStats.closedTxnsToday);
						$('#totalCloTransYesterday').text(
								transStats.closedTxnsYesterday);
						$('#totalCloTransInWeekRange').text(
								transStats.closedTxnsInWeekRange);
						$('#totalCloTransInDateRange').text(
								transStats.closedTxnsInDateRange);
					}

					function setTransactionsValue(transStats) {						
						$('#totalTransVal').text(transStats.totalTxnsValue);
						$('#totalTransValToday').text(transStats.totalTxnsValueToday);
						$('#totalTransValYesterday').text(
								transStats.totalTxnsValueYesterday);
						$('#totalTransValInWeekRange').text(
								transStats.totalTxnsValueInWeekRange);
						$('#totalTransValInDateRange').text(
								transStats.totalTxnsValueInDateRange);
						$('#totalAccTransVal').text(transStats.totalAcceptedTxnsValue);
						$('#totalAccTransValToday').text(
								transStats.acceptedTxnsValueToday);
						$('#totalAccTransValYesterday').text(
								transStats.acceptedTxnsValueYesterday);
						$('#totalAccTransValInWeekRange').text(
								transStats.acceptedTxnsValueInWeekRange);
						$('#totalAccTransValInDateRange').text(
								transStats.acceptedTxnsValueInDateRange);
						$('#totalRejTransVal').text(transStats.totalRejectedTxnsValue);
						$('#totalRejTransValToday').text(
								transStats.rejectedTxnsValueToday);
						$('#totalRejTransValYesterday').text(
								transStats.rejectedTxnsValueYesterday);
						$('#totalRejTransValInWeekRange').text(
								transStats.rejectedTxnsValueInWeekRange);
						$('#totalRejTransValInDateRange').text(
								transStats.rejectedTxnsValueInDateRange);
						$('#totalPenTransVal').text(transStats.totalPendingTxnsValue);
						$('#totalPenTransValToday').text(
								transStats.pendingTxnsValueToday);
						$('#totalPenTransValYesterday').text(
								transStats.pendingTxnsValueYesterday);
						$('#totalPenTransValInWeekRange').text(
								transStats.pendingTxnsValueInWeekRange);
						$('#totalPenTransValInDateRange').text(
								transStats.pendingTxnsValueInDateRange);
						$('#totalCloTransVal').text(transStats.totalClosedTxnsValue);
						$('#totalCloTransValToday').text(
								transStats.closedTxnsValueToday);
						$('#totalCloTransValYesterday').text(
								transStats.closedTxnsValueYesterday);
						$('#totalCloTransValInWeekRange').text(
								transStats.closedTxnsValueInWeekRange);
						$('#totalCloTransValInDateRange').text(
								transStats.closedTxnsValueInDateRange);
					}

				});