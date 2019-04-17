jQuery(document).ready(function() {
	
	var inviteCode;	
	var dateRange;
	var userType;
	var $instance = this;
	
	var startDate, endDate;
	
	
	
	$('#allUserBySPInDateRangeMapBtnModal').on('click', function(e) {
		//document.location.href = "map";
		 window.open('registeredreferaluserbyspInDaterangemap/'+dateRange+'?userType='+userType+'', '_blank');
	});
	
	$('#allUserBySPInDateRangeMapBtnDashboard').on('click', function(e) {
		//document.location.href = "map";
		dateRange = 'all' ;
		userType = 'P' ;
		 window.open('registeredreferaluserbyspInDaterangemap/'+dateRange+'?userType='+userType+'', '_blank');
	});
	
	
	$("#viwonMap").click(function(){
		
		window.open('indivualreferaluserbyspmap/'+dateRange+'?inviteCode='+inviteCode+'&userType='+userType+'','_blank');
	});
	
	
	// below function provide users that are registered by salesman individual in period like today, yes, weekend , ......
	
	
	
	
	$('#salesPersonP').on('click','.totalUsersBtn',function(){	
		
		
		
		 document.getElementById('viwonMap').style.visibility = 'visible';
		
		
		inviteCode = $(this).attr('inviteCode');
		
		salesPersonName = $(this).attr('salesPersonName');
		
		userType='P';
		
		dateRange=$(this).attr('dateRange');
		
		var field = $('.modal-title');
		 
		      $("#onReferalUsersLabel").text("Referee Name  " +salesPersonName);
		      
		
		
		
		setDataTableBodyForOnBoardedUsers("../admin/getReferalUsers/all?userType=P&inviteCode="
				+ inviteCode);

	});
	
	
	
	$('#salesPersonP').on('click','.todayUsersBtn',function(){
	
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

	
	$('#salesPersonP').on('click','.yesterdayUsersBtn',function(){	
	
	
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

	
	$('#salesPersonP').on('click','.usersInWeekDateRangeBtn',function(){	
		
	
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
	
	
	$('#salesPersonP').on('click','.usersInDateRangeBtn',function(){	
	
	
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

	
	$('#salesPersonT').on('click','.totalUsersBtnNAP',function(){	

	document.getElementById('viwonMap').style.visibility = 'visible';
	
	
	inviteCode = $(this).attr('inviteCode');
	
	salesPersonName = $(this).attr('salesPersonName');
	
	dateRange=$(this).attr('dateRange');
	
	userType='T';
	
	
	
	
	
	var field = $('.modal-title');
	 
	      $("#onReferalUsersLabel").text("Referee Name  " +salesPersonName);
	      
	
	
	
	setDataTableBodyForOnBoardedUsers("../admin/getReferalUsers/all?userType=T&inviteCode="
			+ inviteCode);

});


	
$('#salesPersonT').on('click','.todayUsersBtnNAP',function(){	
	
	
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


$('#salesPersonT').on('click','.yesterdayUsersBtnNAP',function(){

	
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



$('#salesPersonT').on('click','.usersInWeekDateRangeBtnNAP',function(){

	
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


$('#salesPersonT').on('click','.usersInDateRangeBtnNAP',function(){
		
	
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
	
	userType='P';
	
	dateRange=$(this).attr('dateRange');
	      
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/all?userType='+userType);

});


$('.bySalesPersonTodayUsersBtn').click( function() {
	
	var field = $('.modal-title1');
	 
    $("#onTotalReferalUsersLabel").text("Total Registered User By SalesPerson  ");
	
	 userType='P';
	dateRange=$(this).attr('dateRange');
    
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/today?userType='+userType);

});


$('.bySalesPersonYesterdayUsersBtn').click( function() {
	
	
	var field = $('.modal-title1');
	 
    $("#onTotalReferalUsersLabel").text("Total Registered User By SalesPerson  ");
	
	 userType='P';
	dateRange=$(this).attr('dateRange');
    
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/yest?userType='+userType);

});



$('.bySalesPersonInWeekRangeUsersBtn').click( function() {	
	
	
	var field = $('.modal-title1');
	 
    $("#onTotalReferalUsersLabel").text("Total Registered User By SalesPerson  ");
	
	 userType='P';
	dateRange=$(this).attr('dateRange');
    
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/week?userType='+userType);

});

$('.bySalesPersonInDateRangeUsersBtn').click( function() {	
	
	
	var field = $('.modal-title1');
	 
    $("#onTotalReferalUsersLabel").text("Total Registered User By SalesPerson  ");
	
     userType='P';
	dateRange=$(this).attr('dateRange');
    
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/start?userType='+userType);

});


//below function provide all NAP users that are registered by  all salesman  in period like today, yes, weekend , ......


$('.bySalesPersonTotalUsersBtnNAP').click( function() {	
	
	
	var field = $('.modal-title1');
	 
    $("#onTotalReferalUsersLabel").text("Total NAP User By SalesPerson  ");
	
	
	 userType='T';
	dateRange=$(this).attr('dateRange');
	

	      
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/all?userType='+userType);

});


$('.bySalesPersonTodayUsersBtnNAP').click( function() {	
	
	
	var field = $('.modal-title1');
	 
    $("#onTotalReferalUsersLabel").text("Total NAP User By SalesPerson  ");
	
	
	userType='T';
	dateRange=$(this).attr('dateRange');
    
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/today?userType='+userType);

});


$('.bySalesPersonYesterdayUsersBtnNAP').click( function() {	
	
	
	var field = $('.modal-title1');
	 
    $("#onTotalReferalUsersLabel").text("Total NAP User By SalesPerson  ");
	
	
	 userType='T';
	dateRange=$(this).attr('dateRange');
    
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/yest?userType='+userType);

});



$('.bySalesPersonInWeekRangeUsersBtnNAP').click( function() {	
	
	
	var field = $('.modal-title1');
	 
    $("#onTotalReferalUsersLabel").text("Total NAP User By SalesPerson  ");
	
	
	 userType='T';
	dateRange=$(this).attr('dateRange');
    
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/week?userType='+userType);

});

$('.bySalesPersonInDateRangeUsersBtnNAP').click( function() {	
	
	
	var field = $('.modal-title1');
	 
    $("#onTotalReferalUsersLabel").text("Total NAP User By SalesPerson  ");
	
	
    
	userType='T';
	dateRange=$(this).attr('dateRange');
	setDataTableBodyForAllReferalUserBySP('../admin/getAllBySPReferalUsers/start?userType='+userType);

});

$("#viwOnMAp").click(function(){
	windows.open('jsp/'+dateRange+'?inviteCode='+inviteCode+'')
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

	
					
});