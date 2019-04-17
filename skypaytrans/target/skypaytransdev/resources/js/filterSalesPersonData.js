jQuery(document).ready(function() {
	
	var inviteCode;	
	var dateRange;
	var userType;
	var $instance = this;
	
	var startDate, endDate;
	

	
	
   $("#viwonMap").click(function(){
		
		window.open('indivualfilterreferaluserbyspmap/'+startDate+'/'+endDate+'?inviteCode='+inviteCode+'&userType='+userType+'','_blank');
	});
	
	
	// below function provide users that are registered by salesman individual in period like today, yes, weekend , ......
	
   $("#salesPersonFilter").on('click', '.totalUsersBtn', function () {
   		
		
		 document.getElementById('viwonMap').style.visibility = 'visible';
		
		
		inviteCode = $(this).attr('inviteCode');
		
		salesPersonName = $(this).attr('salesPersonName');
		
		userType='P';
		
		dateRange=$(this).attr('dateRange');
		
		var field = $('.modal-title');
		 
		      $("#onReferalUsersLabel").text("Referee Name  " +salesPersonName);
		      
		
		
		
		setDataTableBodyForOnBoardedUsers("../admin/getReferalUsers/all?userType=P&inviteCode="
				+ inviteCode);
		
		  $('#startdatepicker1').val("");
		  $('#enddatepicker1').val("");

	});
	
	
	$("#salesPersonFilter").on('click', '.totalUsersInDateRangeBtn', function () {	
		
		
		 document.getElementById('viwonMap').style.visibility = 'visible';
		
		
		inviteCode = $(this).attr('inviteCode');
		
		salesPersonName = $(this).attr('salesPersonName');
		
		userType='P';
		
		startDate=$(this).attr('startDate');
		endDate=$(this).attr('endDate');
		
		
		
		var field = $('.modal-title');
		 
		      $("#onReferalUsersLabel").text("Referee Name  " +salesPersonName);
		      
		
		
		
		setDataTableBodyForOnBoardedUsers("../admin/getReferalUsersInDateRange/"+startDate+"/"+endDate+"?userType=P&inviteCode="
				+ inviteCode);
		  $('#startdatepicker1').val("");
		  $('#enddatepicker1').val("");

	});
	
	
	
	
	
$('.todayUsersBtn').click( function() {	
	
	document.getElementById('viwonMap').style.visibility = 'visible';
		

	inviteCode = $(this).attr('inviteCode');
	
	salesPersonName = $(this).attr('salesPersonName');
	
	userType='P';
	
	dateRange=$(this).attr('dateRange');
		
		
		
		var field = $('.modal-title');
		 
		      $("#onReferalUsersLabel").text("Referee Name  " +salesPersonName);
		
		
		
		
		setDataTableBodyForOnBoardedUsers("../admin/getReferalUsers/today?userType=P&inviteCode="
				+ inviteCode);

	});

$('.yesterdayUsersBtn').click( function() {	
	
	
	document.getElementById('viwonMap').style.visibility = 'visible';
	

	inviteCode = $(this).attr('inviteCode');
	
	salesPersonName = $(this).attr('salesPersonName');
	
	userType='P';
	
	dateRange=$(this).attr('dateRange');
	
	
	
	var field = $('.modal-title');
	 
	      $("#onReferalUsersLabel").text("Referee Name  " +salesPersonName);
	

	
	
	setDataTableBodyForOnBoardedUsers("../admin/getReferalUsers/yest?userType=P&inviteCode="
			+ inviteCode);

});	

$('.usersInWeekDateRangeBtn').click( function() {	
	
	
	document.getElementById('viwonMap').style.visibility = 'visible';
	

	inviteCode = $(this).attr('inviteCode');
	
	salesPersonName = $(this).attr('salesPersonName');
	
	userType='P';
	
	dateRange=$(this).attr('dateRange');
	
	
	var field = $('.modal-title');
	 
	      $("#onReferalUsersLabel").text("Referee Name  " +salesPersonName);
	
	
	
	
	
	setDataTableBodyForOnBoardedUsers("../admin/getReferalUsers/week?userType=P&inviteCode="
			+ inviteCode);

});

$('.usersInDateRangeBtn').click( function() {	
	
	
	document.getElementById('viwonMap').style.visibility = 'visible';
	
	

	inviteCode = $(this).attr('inviteCode');
	
	salesPersonName = $(this).attr('salesPersonName');
	
	userType='P';
	
	dateRange=$(this).attr('dateRange');
	
	
	
	var field = $('.modal-title');
	 
	      $("#onReferalUsersLabel").text("Referee Name  " +salesPersonName);
	
	
	
	
	setDataTableBodyForOnBoardedUsers("../admin/getReferalUsers/start?userType=P&inviteCode="
			+ inviteCode);

});




// below function provide NAP users that are registered by salesman individual in period like today, yes, weekend , ......


	
	$("#salesPersonFilter").on('click', '.totalUsersBtnNAP', function () {
	
	document.getElementById('viwonMap').style.visibility = 'visible';
	
	
	inviteCode = $(this).attr('inviteCode');
	
	salesPersonName = $(this).attr('salesPersonName');
	
	dateRange=$(this).attr('dateRange');
	
	userType='T';
	
	
	
	
	
	var field = $('.modal-title');
	 
	      $("#onReferalUsersLabel").text("Referee Name  " +salesPersonName);
	      
	
	
	
	setDataTableBodyForOnBoardedUsers("../admin/getReferalUsers/all?userType=T&inviteCode="
			+ inviteCode);
	
	  $('#startdatepicker1').val("");
	  $('#enddatepicker1').val("");

});




$("#salesPersonFilter").on('click', '.totalUsersInDateRangeBtnNAP', function () {	
	
	 document.getElementById('viwonMap').style.visibility = 'visible';
	
	
	inviteCode = $(this).attr('inviteCode');
	
	salesPersonName = $(this).attr('salesPersonName');
	
	userType='T';
	
	startDate=$(this).attr('startDate');
	endDate=$(this).attr('endDate');
	
	
	
	var field = $('.modal-title');
	 
	      $("#onReferalUsersLabel").text("Referee Name  " +salesPersonName);
	      
	
	
	
	setDataTableBodyForOnBoardedUsers("../admin/getReferalUsersInDateRange/"+startDate+"/"+endDate+"?userType=T&inviteCode="
			+ inviteCode);
	
	  $('#startdatepicker1').val("");
	  $('#enddatepicker1').val("");

});

$('.todayUsersBtnNAP').click( function() {	
	
	document.getElementById('viwonMap').style.visibility = 'visible';
	
	
	 inviteCode = $(this).attr('inviteCode');
     salesPersonName = $(this).attr('salesPersonName');
    dateRange=$(this).attr('dateRange');
    userType='T';
	
	
	
	var field = $('.modal-title');
	 
	      $("#onReferalUsersLabel").text("Referee Name  " +salesPersonName);
	
	
	
	
	setDataTableBodyForOnBoardedUsers("../admin/getReferalUsers/today?userType=T&inviteCode="
			+ inviteCode);

});

$('.yesterdayUsersBtnNAP').click( function() {	
	
	document.getElementById('viwonMap').style.visibility = 'visible';


 inviteCode = $(this).attr('inviteCode');

 salesPersonName = $(this).attr('salesPersonName');
dateRange=$(this).attr('dateRange');
userType='T';



var field = $('.modal-title');
 
      $("#onReferalUsersLabel").text("Referee Name  " +salesPersonName);




setDataTableBodyForOnBoardedUsers("../admin/getReferalUsers/yest?userType=T&inviteCode="
		+ inviteCode);

});	

$('.usersInWeekDateRangeBtnNAP').click( function() {
	
	document.getElementById('viwonMap').style.visibility = 'visible';


     inviteCode = $(this).attr('inviteCode');

     salesPersonName = $(this).attr('salesPersonName');
    
    dateRange=$(this).attr('dateRange');
    userType='T';



var field = $('.modal-title');
 
      $("#onReferalUsersLabel").text("Referee Name  " +salesPersonName);





setDataTableBodyForOnBoardedUsers("../admin/getReferalUsers/week?userType=T&inviteCode="
		+ inviteCode);

});

$('.usersInDateRangeBtnNAP').click( function() {	
	
	
document.getElementById('viwonMap').style.visibility = 'visible';


 inviteCode = $(this).attr('inviteCode');

 salesPersonName = $(this).attr('salesPersonName');

dateRange=$(this).attr('dateRange');
userType='T';



var field = $('.modal-title');
 
      $("#onReferalUsersLabel").text("Referee Name  " +salesPersonName);




setDataTableBodyForOnBoardedUsers("../admin/getReferalUsers/start?userType=T&inviteCode="
		+ inviteCode);

});



//below function provide all users that are registered by  all salesman  in period like today, yes, weekend , ......


$('.bySalesPersonTotalUsersBtn').click( function() {	
	
	

	var field = $('.modal-title1');
	 
	      $("#onTotalReferalUsersLabel").text("Total Registered User By SalesPerson  ");
	
	var userType='P';
	      
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/all?userType='+userType);

});


$('.bySalesPersonTodayUsersBtn').click( function() {
	
	var field = $('.modal-title1');
	 
    $("#onTotalReferalUsersLabel").text("Total Registered User By SalesPerson  ");
	
	var userType='P';
    
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/today?userType='+userType);

});


$('.bySalesPersonYesterdayUsersBtn').click( function() {
	
	
	var field = $('.modal-title1');
	 
    $("#onTotalReferalUsersLabel").text("Total Registered User By SalesPerson  ");
	
	var userType='P';
    
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/yest?userType='+userType);

});



$('.bySalesPersonInWeekRangeUsersBtn').click( function() {	
	
	
	var field = $('.modal-title1');
	 
    $("#onTotalReferalUsersLabel").text("Total Registered User By SalesPerson  ");
	
	var userType='P';
    
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/week?userType='+userType);

});

$('.bySalesPersonInDateRangeUsersBtn').click( function() {	
	
	
	var field = $('.modal-title1');
	 
    $("#onTotalReferalUsersLabel").text("Total Registered User By SalesPerson  ");
	
	var userType='P';
    
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/start?userType='+userType);

});


//below function provide all NAP users that are registered by  all salesman  in period like today, yes, weekend , ......


$('.bySalesPersonTotalUsersBtnNAP').click( function() {	
	
	
	var field = $('.modal-title1');
	 
    $("#onTotalReferalUsersLabel").text("Total NAP User By SalesPerson  ");
	
	
	var userType='T';
	

	      
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/all?userType='+userType);

});


$('.bySalesPersonTodayUsersBtnNAP').click( function() {	
	
	
	var field = $('.modal-title1');
	 
    $("#onTotalReferalUsersLabel").text("Total NAP User By SalesPerson  ");
	
	
	var userType='T';
    
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/today?userType='+userType);

});


$('.bySalesPersonYesterdayUsersBtnNAP').click( function() {	
	
	
	var field = $('.modal-title1');
	 
    $("#onTotalReferalUsersLabel").text("Total NAP User By SalesPerson  ");
	
	
	var userType='T';
    
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/yest?userType='+userType);

});



$('.bySalesPersonInWeekRangeUsersBtnNAP').click( function() {	
	
	
	var field = $('.modal-title1');
	 
    $("#onTotalReferalUsersLabel").text("Total NAP User By SalesPerson  ");
	
	
	var userType='T';
    
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/week?userType='+userType);

});

$('.bySalesPersonInDateRangeUsersBtnNAP').click( function() {	
	
	
	var field = $('.modal-title1');
	 
    $("#onTotalReferalUsersLabel").text("Total NAP User By SalesPerson  ");
	
	
    
	var userType='T';
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/start?userType='+userType);

});

$("#viwOnMAp").click(function(){
	windows.open('jsp/'+dateRange+'?inviteCode='+inviteCode+'')
});

//below function provide All  users that are registered by all salesman in period like today, yes, weekend , ...... it is make table and calling above

function setDataTableBodyForAllReferalUserBySP(url) {

	var table = $('#onTotalReferalUsersTable').DataTable({
		responsive : true,
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
			 
		}
		/*{
			"data":"address"
		}*/]

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




//below function provide users that are registered by salesman individual  in period like today, yes, weekend , ...... it is make table and calling above

	function setDataTableBodyForOnBoardedUsers(url) {

		var table = $('#onReferalUsersTable').DataTable({
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
				 
			}
			/*{
				"data":"address"
			}*/]

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
	
	
	
	
	
	//  for Date range in jsp
	
	
	
	$('#findSalesInfoBtn').click(
			function() {
				startDate = $('#startdatepicker').val();
				endDate = $('#enddatepicker').val();
				
				 var date1 = Date.parse(startDate);
				 var date2 = Date.parse(endDate);
				 if(date1>date2){
					alert("please select end date greater than start Date"); 
				 }
				 else{
				setDataTableBodyForAllSPInFilter("../admin/getSalesPersonDataInDateRange/"+startDate+"/"+endDate);
				 }
				
				 
				
			});
	
	   
	
	
	
		$("#startdatepicker").datepicker({
			dateFormat : 'yy-mm-dd',
			inline : true,
			maxDate: new Date()
			
		});
	

	
		$("#enddatepicker").datepicker({
			dateFormat : 'yy-mm-dd',
			inline : true,
			maxDate: new Date()
		});
		
		
		
	//  for Date range in Modal for particular users
		
		
		$('#filterUsersInModalBtn').click(
				function() {
					startDate = $('#startdatepicker1').val();
					endDate = $('#enddatepicker1').val();
					
					alert("aya yah"+inviteCode);
					
					 var date1 = Date.parse(startDate);
					 var date2 = Date.parse(endDate);
					 if(date1>date2){
						alert("please select end date greater than start Date"); 
					 }
					 else{
					
							setDataTableBodyForOnBoardedUsers("../admin/getReferalUsersInDateRange/"+startDate+"/"+endDate+"?userType="+userType+"&inviteCode="
									+ inviteCode);
						
					 }
					
					
				});
		
		   
		
		
		
			$("#startdatepicker1").datepicker({
				dateFormat : 'yy-mm-dd',
				inline : true,
				maxDate: new Date()
				
			});
		

		
			$("#enddatepicker1").datepicker({
				dateFormat : 'yy-mm-dd',
				inline : true,
				maxDate: new Date()
			});
			
		

		
		
		
		
		function setDataTableBodyForAllSPInFilter(url) {
			

			var table = $('#salesPersonFilter').DataTable({
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
					"data" : "salesPersonMobileNumber"
				},{
					"data" : "slaesPersonInviteCode"
			    }, {
					data : "lastSingupTime",
						render :function(data,type,row){
							var lastSingupTime=row.lastSingupTime;
							
								
								data='<h5 class="text-danger">'+lastSingupTime+'</h5>';
							
						   return data;	
						}
						
				},{
					"data" : "salesPersonSignUp"
				}, {
					data : "totalCount",
					render :function(data,type,row){
						var total=row.totalCount;
						
							
							data='<button type="button" invitecode='+row.slaesPersonInviteCode+' salesPersonName='+row.salesPersonName+'  startDate='+startDate+' endDate='+endDate+' class="btn btn-link totalUsersInDateRangeBtn" data-toggle="modal" data-target="#onReferalUsers">'+total+'</button>';
						
					   return data;	
					}
				},{
					data : "totalCountNAP",
					render :function(data,type,row){
						var total=row.totalCountNAP;
						
							
							data='<button type="button" invitecode='+row.slaesPersonInviteCode+' salesPersonName='+row.salesPersonName+'  startDate='+startDate+' endDate='+endDate+' class="btn btn-link totalUsersInDateRangeBtnNAP" data-toggle="modal" data-target="#onReferalUsers">'+total+'</button>';
						
					   return data;	
					}
				
				}
				/*{
					"data":"address"
				}*/]

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

	
					
});