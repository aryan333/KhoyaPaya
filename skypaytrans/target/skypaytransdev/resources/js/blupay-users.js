/*var totalUserClick = false, // as data is already shown when page loads
regUserClick = true, tempUserClick = true, todayRegUserClick = true, todayTempUserClick = true;
 */
bluPay = new function() {
	var $instance = this;
	this.init = function(flag) {

		var startDate= '';
		var  endDate='';
		$instance.initiateStartDatePicker();
		$instance.initiateEndDatePicker();
		var period ='All';
		
		
		
		// this is for  show hide button in users Table 
		
		$('#demo').on('hide.bs.collapse', function () {
		    $('#button').html('<span class="glyphicon glyphicon-collapse-down"></span> Show');
		  })
		$('#demo').on('show.bs.collapse', function () {
		    $('#button').html('<span class="glyphicon glyphicon-collapse-up"></span> Hide');
		  })
		  
		  
	   $('#demo1').on('hide.bs.collapse', function () {
		    $('#button1').html('<span class="glyphicon glyphicon-collapse-down"></span> Show');
		  })
		$('#demo1').on('show.bs.collapse', function () {
		    $('#button1').html('<span class="glyphicon glyphicon-collapse-up"></span> Hide');
		  })	  
		
		
		  
		  // this for view on map on users table section
		  
		$("#viewBlupayUsersOnMap").click(function(){
			
			window.open('blupayUsersOnmap/'+period+'?startDate='+startDate+'&endDate='+endDate+'','_blank');
		});
		
		

		if (!flag.localeCompare('all')) { // localCompare return 0 if content
			// matches and 0 is false inside if
			$instance.setDataTableBody("./getAllBluPayUsers?tod=n");

		} else {

			$instance.setDataTableBody("./getAllBluPayUsers?tod=y");
			$("#userListHeadingDiv").text("New Users Today");
		}
		$('#totalUsersButton').click(
				
				
				
				function() {
					$instance.setDataTableBody("./getAllBluPayUsers?tod=n",
							"All BluPay Users");
					$instance.disableButton(this);
					period = 'All';

				});

		$('#totalRegisteredButton').click(
				function() {
					$instance.setDataTableBody("./getAllRegisteredBluPayUsers",
							"Registered BluPay Users");
					$instance.disableButton(this);
					period = 'AllR';

				});

		$('#totalUnregisteredButton').click(
				function() {
					$instance.setDataTableBody("./getAllTemporaryBluPayUsers",
							"Unregistered BluPay Users");
					$instance.disableButton(this);
					period = 'AllU';

				});

		$('#totalNewRegisteredButton').click(
				function() {
					$instance.setDataTableBody(
							"./getRegisteredBluPayUsersToday",
							"Today's Registered BluPay Users");
					$instance.disableButton(this);
					period = 'TR'; // today Registered User

				});

		$('#totalNewUnregisteredButton').click(
				function() {
					$instance.setDataTableBody(
							"./getTemporaryBluPayUsersToday",
							"Today's Unregistered BluPay Users");
					$instance.disableButton(this);
					period = 'TU';

				});

		$('#totalNewRegisteredWeekButton').click(
				function() {
					$instance.setDataTableBody("./getCurrentWeekRegUsers",
							"BluPay Users This Week");
					$instance.disableButton(this);
					period = 'WR';

				});
		$('#totalNewUnregisteredWeekButton').click(
				function() {
					$instance.setDataTableBody("./getCurrentWeekTempUsers",
							"Unregistered Guests This Week");
					$instance.disableButton(this);
					period = 'WU';

				});

		$('#totalNewRegisteredMonthButton').click(
				function() {
					$instance.setDataTableBody("./getCurrentMonthRegUsers",
							"Registered BluPay Users This Month");
					$instance.disableButton(this);
					period = 'MR';

				});

		$('#totalNewUnregisteredMonthButton').click(
				function() {
					$instance.setDataTableBody("./getCurrentMonthTempUsers",
							"Unregistered Guests This Month");
					$instance.disableButton(this);
					period = 'MU';

				});

		$('#findUsersBtn').click(
				function() {
					startDate = $('#startdatepicker').val();
					endDate = $('#enddatepicker').val();
					var url = "../admin/getAllBluPayUsers/" + startDate + "/"
							+ endDate + "/A";
					$instance
							.setUserDataForDateRange(url, startDate, endDate,
									'BluPay Users from ' + startDate + ' to '
											+ endDate);
					period = 'AllD';
				});

		$('#totalUsersDRButton').click(
				function() {
					var url = "../admin/getAllBluPayUsers/" + startDate + "/"
					+ endDate + "/A";
			$instance
					.setUserDataForDateRange(url, startDate, endDate,
							'BluPay Users from ' + startDate + ' to '
									+ endDate);
			period = 'AllD';
				});
		
		$('#totalRegisteredDRButton').click(
				function() {
					var url = "../admin/getAllBluPayUsers/" + startDate + "/"
							+ endDate + "/P";
					$instance.setUserDataForDateRange(url, startDate, endDate,
							'Registered Users from ' + startDate + ' to '
									+ endDate);
					period = 'RD';
				});

		$('#totalUnregisteredDRButton').click(
				function() {
					var url = "../admin/getAllBluPayUsers/" + startDate + "/"
							+ endDate + "/T";
					$instance.setUserDataForDateRange(url, startDate, endDate,
							'Unregistered Guests from ' + startDate + ' to '
									+ endDate);
					period = 'UD';
				});

	};

	$instance.setDataTableBody = function(url, heading) {

		$("#dateRangeUsersCountDiv").hide();
		$("#userListHeadingDiv").text(heading);

		var table = $('#bluePayUsersTable')
				.DataTable(
						{
							responsive : true,
							"pageLength": 100,
							"destroy" : true,
							dom : 'lBfrtip',
							buttons : ['excelHtml5', 'csvHtml5',
									'pdfHtml5' ],
							/*"order" : [ [ 0, "desc" ] ],*/
							"ajax" :{ 
								"url":url,
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
										"data" : "testUser",
										render : function(data, type, row, meta) {
											if (data == true) {
												data = 'Y';
											} else {
												data = 'N';
											}
											return data;
										}
									},
									{
										"data" : "active",
										render : function(data, type, row, meta) {

											if (data == true) {
												data = 'Y';
											} else {
												data = 'N';
											}
											return data;
										}
									},
									{
										"data" : "userDetail.starValue",

									},
									{
										"data" : "usersTotalEarnedPoints.totalPointsEarned"
									},
									{
										"data" : "createdOn"
									},
									{
										"data" : "userDetail",
										render : function(data, type, row, meta) {
											var lat = data.latitude, lng = data.longitude;											
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
									
									} ,
									
									{
										"data": "phNumber",
											
											
												
												render : function(data, type, row, meta) {
													
													data='<a href="findUserWithMob?mob='+data+'"> View Details</a>'
												
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

	$instance.disableButton = function(clickedButton) {
		var input = clickedButton;
		input.disabled = true;
		setTimeout(function() {
			input.disabled = false;
		}, 2000);
	};

	$instance.initiateStartDatePicker = function() {
		$("#startdatepicker").datepicker({
			dateFormat : 'yy-mm-dd',
			inline : true
		});
	};

	$instance.initiateEndDatePicker = function() {
		$("#enddatepicker").datepicker({
			dateFormat : 'yy-mm-dd',
			inline : true
		});
	};

	$instance.setUserDataForDateRange = function(url, startDate, endDate,
			heading) {
		console.log("url == " + url);
		$instance.disableButton(this);
		$.ajax({
			type : "GET",
			url : url,
			async : true,
			success : function(data) {
				var dataSet = data.data;
				var usersCount = data.userStats;
				$instance.setUsersStatsForDateRange(usersCount);
				$instance.setDataTableBodyForDateRangeUsers(dataSet, heading);
			},
			error : function(textStatus, errorThrown) {
				alert('some error occurs. please try after sometime.')
			}

		});

	}

	$instance.setDataTableBodyForDateRangeUsers = function(dataSet, heading) {
		$("#userListHeadingDiv").text(heading);
		var table = $('#bluePayUsersTable')
				.DataTable(
						{
							data : dataSet,
							"pageLength": 100,
							"destroy" : true,
							dom : 'lBfrtip',
							buttons : ['excelHtml5', 'csvHtml5',
									'pdfHtml5' ],
						/*	"order" : [ [ 0, "desc" ] ],*/
							
							columns : [
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
										"data" : "testUser",
										render : function(data, type, row, meta) {
											if (data == true) {
												data = 'Yes';
											} else {
												data = 'No';
											}
											return data;
										}
									},
									{
										"data" : "active",
										render : function(data, type, row, meta) {

											if (data == true) {
												data = 'Yes';
											} else {
												data = 'No';
											}
											return data;
										}
									},
									{
										"data" : "userDetail.starValue",

									},
									{
										"data" : "usersTotalEarnedPoints.totalPointsEarned"
									},
									{
										"data" : "createdOn"
									},
									{
										"data" : "userDetail",
										render : function(data, type, row, meta) {
											var lat = data.latitude, lng = data.longitude;
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
									
                                        } ,
									
									{
										"data": "phNumber",
											
											
												
												render : function(data, type, row, meta) {
													
													data='<a href="findUserWithMob?mob='+data+'"> View Details</a>'
												
												return data;
												}
										
										
									} ]
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

	$instance.setUsersStatsForDateRange = function(data) {
		$("#dateRangeUsersCountDiv").show();
		$('#totalUsersDRButton').text(data.totalUsers);
		$('#totalRegisteredDRButton').text(data.totalPermanentUsers);
		$('#totalUnregisteredDRButton').text(data.totalTemporaryUsers);

	}

};