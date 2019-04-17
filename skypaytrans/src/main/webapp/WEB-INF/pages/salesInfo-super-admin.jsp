<!DOCTYPE html>
<%@page import="com.saifintex.web.domain.UsersStatsDashboard"%>
<%@page import="com.saifintex.web.dto.UsersStatsDashboardDTO"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="refresh"
	content="<%=session.getMaxInactiveInterval()%>;url=login" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Ezkhata Sales Man Information</title>
<link rel="icon" href="../resources/images/favicon.ico"
	type="image/x-icon">
<!-- Bootstrap Core CSS -->
<link href="../resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="../resources/vendor/metisMenu/metisMenu.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


<!-- DataTables CSS -->
<link
	href="../resources/vendor/datatables-plugins/dataTables.bootstrap.css"
	rel="stylesheet">

<!-- DataTables Responsive CSS -->
<link
	href="../resources/vendor/datatables-responsive/dataTables.responsive.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="../resources/dist/css/master-table-modal.css" rel="stylesheet">
<link href="../resources/dist/css/sb-admin-2.css" rel="stylesheet">
<!-- Custom Fonts -->
<!-- <link href="../resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"> -->

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<div id="wrapper">
		<jsp:include page="header-super-admin.jsp"></jsp:include>
		<input type="hidden" id="flag" value=${flag}>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Sales Man Information</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
	
			<!-- /.row -->
			
			<div class="row" >
				<div class="col-lg-12">
					<div class="panel panel-default">
					<button type="button"  class="btn"  style="float: right; margin-right: 10px;"
										id="allRegisterUserBySPMapBtn">View All Registered Users on Map</button>
										
					<button type="button"  class="btn"  style="float: right; margin-right: 10px;"
										id="allNAPUserBySPMapBtn">View All NAP Users on Map</button>					
						<div class="panel-heading"><strong>Total User By Sales Person</strong>
						
						
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="table-responsive">
								<table  class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
										    <th> USER TYPE</th>
											<th>Total Users<br>(since
												${dateParams.startingDate})</th>
											<th> Today<br>(${dateParams.currentDate})
											
											</th>
											
											
											<th> yesterday<br>(${dateParams.yesterdayDate})
											
											</th>
											
											<th> Last 7 days<br>(${dateParams.startingDateOfWeek}-${dateParams.endDateOfWeek})
											
											 </th>
											 
											 <th> Upto <br>${dateParams.uptoDate}
											
											</th>
											 
										</tr>
									</thead>									
									<tbody>
										<tr>
										
										 <td class="text-success"><strong> REGISTERED USER </strong></td>
										<td > <button type="button" class="btn btn-link bySalesPersonTotalUsersBtn" data-toggle="modal" data-target="#onTotalReferalUsers" >
												<strong> ${totaluser.overAllTotalCount} </button> </strong>
											</td>
											
											<td ><button type="button" class="btn btn-link bySalesPersonTodayUsersBtn" data-toggle="modal" data-target="#onTotalReferalUsers" >
												<strong> ${totaluser.totalCurrentDateCount} </button> </strong>
											</td>
												
											<td ><button type="button" class="btn btn-link bySalesPersonYesterdayUsersBtn" data-toggle="modal" data-target="#onTotalReferalUsers" >
												<strong> ${totaluser.totalYesterdayDateCount} </button> </strong>
											</td>
											
											<td ><button type="button" class="btn btn-link bySalesPersonInWeekRangeUsersBtn" data-toggle="modal" data-target="#onTotalReferalUsers" >
												<strong> ${totaluser.totalWeekCount} </button> </strong>
											</td>
											
											<td ><button type="button" class="btn btn-link bySalesPersonInDateRangeUsersBtn" data-toggle="modal" data-target="#onTotalReferalUsers" >
												<strong> ${totaluser.totalUptoDateCount}</button> </strong>
											</td>
											
											
											 <tr class="break"></tr>
											
											 <td  class="text-danger"><strong> NAP USER </strong></td>
										<td > <button type="button" class="btn btn-link bySalesPersonTotalUsersBtnNAP" data-toggle="modal" data-target="#onTotalReferalUsers" >
												<strong> ${totaluser.overAllTotalCountNAP} </button> </strong>
											</td>
											
											<td ><button type="button" class="btn btn-link bySalesPersonTodayUsersBtnNAP" data-toggle="modal" data-target="#onTotalReferalUsers" >
												<strong> ${totaluser.totalCurrentDateCountNAP} </button> </strong>
											</td>
												
											<td><button type="button" class="btn btn-link bySalesPersonYesterdayUsersBtnNAP" data-toggle="modal" data-target="#onTotalReferalUsers" >
												<strong> ${totaluser.totalYesterdayDateCountNAP} </button> </strong>
											</td>
											
											<td ><button type="button" class="btn btn-link bySalesPersonInWeekRangeUsersBtnNAP" data-toggle="modal" data-target="#onTotalReferalUsers" >
												<strong> ${totaluser.totalWeekCountNAP} </button> </strong>
											</td>
											
											<td><button type="button" class="btn btn-link bySalesPersonInDateRangeUsersBtnNAP" data-toggle="modal" data-target="#onTotalReferalUsers" >
												<strong> ${totaluser.totalUptoDateCountNAP}</button> </strong>
											</td>
											
										</tr>
									</tbody>
								</table>
							</div>
							<!-- /.table-responsive -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
			</div>
			
			
			


			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading" id="tablePanelHeading" > <strong class="text-success">Sales Man Details With Registered User</strong> </div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="table-responsive" id="tableArea">
								<table id="salesPersonP" width="100%"
									class="table table-striped table-bordered table-hover"
									 >
							      <thead>
										<tr>
											
											<th>Sr No</th>
											
											<th>SalesPerson Name</th>
											
				                            <th>SalesPerson Mobile</th>
				                            
				                            <th>Invite Code</th>
				                            
				                             <th>Last User SignUp</th>
				                             
				                             <th> AV Count</th>
				                           
											
											<th>Total so far<br>(since
												${dateParams.startingDate})
											</th>
											<th>Today<br>(${dateParams.currentDate})
											</th>
											<th>Yesterday<br>(${dateParams.yesterdayDate})
											</th>
											<th>Last 7 days<br>(${dateParams.startingDateOfWeek}-${dateParams.endDateOfWeek})
											</th>
											<th>Upto <br>${dateParams.uptoDate}</th>
										</tr>
									</thead>
									<c:forEach var="salesRefDetails" items="${salesRefDetails}"  varStatus="loop">


										<tr class="odd gradeX">
										     
										     <td>${loop.index+1}</td>
											<td >${salesRefDetails.salesPersonName}</td>
											<td >${salesRefDetails.salesPersonMobileNumber}</td>
											<td  class="inviteCode">${salesRefDetails.slaesPersonInviteCode}</td>
											<td  class="text-danger"> ${salesRefDetails.lastSingupTime}</td>
											<td class="text-center" > ${salesRefDetails.salesPersonSignUp}</td>
											<td ><button invitecode="${salesRefDetails.slaesPersonInviteCode}" salesPersonName="${salesRefDetails.salesPersonName}" dateRange="all"  type="button" class="btn btn-link totalUsersBtn" data-toggle="modal" data-target="#onReferalUsers">${salesRefDetails.totalCount}</button></td>
											<td ><button invitecode="${salesRefDetails.slaesPersonInviteCode}" salesPersonName="${salesRefDetails.salesPersonName}" dateRange="today"  type="button"class="btn btn-link todayUsersBtn" data-toggle="modal"data-target="#onReferalUsers"><span style="color:${salesRefDetails.colorCode}">${salesRefDetails.currentDateCount}</span></button></td>
											<td ><button invitecode="${salesRefDetails.slaesPersonInviteCode}" salesPersonName="${salesRefDetails.salesPersonName}" dateRange="yest" type="button"class="btn btn-link yesterdayUsersBtn" data-toggle="modal"data-target="#onReferalUsers">${salesRefDetails.yesterdayDateCount}</button></td>
											<td ><button invitecode="${salesRefDetails.slaesPersonInviteCode}" salesPersonName="${salesRefDetails.salesPersonName}" dateRange="week"  type="button"class="btn btn-link usersInWeekDateRangeBtn" data-toggle="modal"data-target="#onReferalUsers">${salesRefDetails.weekCount}</button></td>
											<td ><button invitecode="${salesRefDetails.slaesPersonInviteCode}" salesPersonName="${salesRefDetails.salesPersonName}" dateRange="start" type="button"class="btn btn-link usersInDateRangeBtn" data-toggle="modal"data-target="#onReferalUsers">${salesRefDetails.uptoDateCount}</button></td>
										   
										    
										    
										    
											<!-- <td><button type="button" class="btn btn-link"
												data-toggle="modal" data-target="">Edit</button>
												/ <button type="button" class="btn btn-link"
												data-toggle="modal" data-target="">Delete</button></td> -->
										</tr>
										
										
										
									</c:forEach>
									
									
									
									

								</table>



							</div>
							
							
		
	
							<!-- /.table-responsive -->
			


						</div>
					</div>
				</div>
			</div>
			
			
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading" id="tablePanelHeading"><strong class="text-danger">Sales Man Details with NAP USER</strong> </div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="table-responsive" id="tableArea">
								<table id="salesPersonT" width="100%"
									class="table table-striped table-bordered table-hover"
									 >
							      <thead>
										<tr>
											
											<th>Sr No.</th>
											<th>SalesPerson Name</th>
											
				                            <th>SalesPerson Mobile</th>
				                            
				                            <th>Invite Code</th>
				                            
				                             <th>Last NAP Created</th>
				                            
											
											<th>Total so far<br>(since
												${dateParams.startingDate})
											</th>
											<th>Today<br>(${dateParams.currentDate})
											</th>
											<th>Yesterday<br>(${dateParams.yesterdayDate})
											</th>
											<th>Last 7 days<br>(${dateParams.startingDateOfWeek}-${dateParams.endDateOfWeek})
											</th>
											<th>Upto <br>${dateParams.uptoDate}</th>
										</tr>
									</thead>
									<c:forEach var="salesRefDetails" items="${salesRefDetails}" varStatus="loop">


										<tr class="odd gradeX">
										
										    <td >${loop.index+1}</td>
										    <td >${salesRefDetails.salesPersonName}</td>
											<td>${salesRefDetails.salesPersonMobileNumber}</td>
											<td class="inviteCode">${salesRefDetails.slaesPersonInviteCode}</td>
											<td  class="text-danger"> ${salesRefDetails.lastSingupTimeNap}</td>
											<td><button invitecode="${salesRefDetails.slaesPersonInviteCode}" salesPersonName="${salesRefDetails.salesPersonName}"   dateRange="all"  type="button" class="btn btn-link totalUsersBtnNAP" data-toggle="modal" data-target="#onReferalUsers">${salesRefDetails.totalCountNAP}</button></td>
											<td><button invitecode="${salesRefDetails.slaesPersonInviteCode}" salesPersonName="${salesRefDetails.salesPersonName}"  dateRange="today"   type="button"class="btn btn-link todayUsersBtnNAP" data-toggle="modal"data-target="#onReferalUsers">${salesRefDetails.currentDateCountNAP}</button></td>
											<td><button invitecode="${salesRefDetails.slaesPersonInviteCode}" salesPersonName="${salesRefDetails.salesPersonName}"  dateRange="yest"  type="button"class="btn btn-link yesterdayUsersBtnNAP" data-toggle="modal"data-target="#onReferalUsers">${salesRefDetails.yesterdayDateCountNAP}</button></td>
											<td><button invitecode="${salesRefDetails.slaesPersonInviteCode}" salesPersonName="${salesRefDetails.salesPersonName}"  dateRange="week"  type="button"class="btn btn-link usersInWeekDateRangeBtnNAP" data-toggle="modal"data-target="#onReferalUsers">${salesRefDetails.weekCountNAP}</button></td>
											<td><button invitecode="${salesRefDetails.slaesPersonInviteCode}" salesPersonName="${salesRefDetails.salesPersonName}"  dateRange="start"  type="button"class="btn btn-link usersInDateRangeBtnNAP" data-toggle="modal"data-target="#onReferalUsers">${salesRefDetails.uptoDateCountNAP}</button></td>
										    
										    
										    
											<!-- <td><button type="button" class="btn btn-link"
												data-toggle="modal" data-target="">Edit</button>
												/ <button type="button" class="btn btn-link"
												data-toggle="modal" data-target="">Delete</button></td> -->
										</tr>
										
										
										
									</c:forEach>
									
									
									
									

								</table>



							</div>
							
							
		
	
							<!-- /.table-responsive -->
			


						</div>
					</div>
				</div>
			</div>
			
			
				<div class="modal fade" id="onReferalUsers" tabindex="-1"
				role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close"  data-dismiss="modal"
								aria-hidden="true">�</button>
								
								<button type="button"  class="btn" style="float: right; margin-right: 10px;"
										id="viwonMap">View All Users on Map</button>
							<h4 class="modal-title" id="onReferalUsersLabel" type="text"> Referee Name
								</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
									<div class="table-responsive">
										<table class="table table-striped table-bordered table-hover"
											id="onReferalUsersTable" style="width: 100%;">
											<thead>
												<tr>
													<th>SN</th>
													<th>User Name</th>
													<th>Mobile No.</th>
													<th>Created On</th>
													<th>Created On Location</th>
													
												
												</tr>
											</thead>

										</table>
										
									</div>
								</div>

							</div>

						</div>
					</div>

				</div>

			</div>
			
			<div class="modal fade" id="onTotalReferalUsers" tabindex="-1"
				role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">�</button>
							<h4 class="modal-title1" id="onTotalReferalUsersLabel" type="text"> <strong> Total Referal Users By SP</strong>
								</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
									<div class="table-responsive">
										<table class="table table-striped table-bordered table-hover"
											id="onTotalReferalUsersTable" style="width: 100%;">
											<thead>
												<tr>
													<th>SN</th>
													<th> Referee Name</th>
													<th>User Name</th>
													<th>Mobile No.</th>
													<th>Created On</th>
													<th>Created On Location</th>
												</tr>
											</thead>

										</table>
									</div>
								</div>

							</div>

						</div>
					</div>

				</div>

			</div>
			
							
						
			
			
		
		<!-- /.panel-body -->

	</div>
	<!-- /.panel -->
	</div>


	<!-- jQuery -->
	<script src="../resources/vendor/jquery/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="../resources/vendor/bootstrap/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="../resources/vendor/metisMenu/metisMenu.min.js"></script>

	<!-- DataTables JavaScript -->
	<script
		src="../resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
	<script
		src="../resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
	<script
		src="../resources/vendor/datatables-responsive/dataTables.responsive.js"></script>
		
	<!-- export table javaScript -->
	<script src="../resources/dist/js/jquery.table2excel.js"></script>	
	
		<script
			src="https://cdn.datatables.net/buttons/1.5.1/js/dataTables.buttons.min.js"></script>
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/pdfmake.min.js"></script>
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/vfs_fonts.js"></script>
		<script
			src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.html5.min.js"></script>
			
		<script>
		
		var table = $('#salesPersonP').DataTable({
			responsive : true,
			"destroy" : true,
			dom : 'lBfrtip',							
				  buttons: [
				       {
				           extend: 'pdf',
				           footer: true,
				           exportOptions: {
				                columns: [0,1,2,3,4,5,6,7,8]
				            }
				       },
				       {
				           extend: 'csv',
				           footer: false,
				           exportOptions: {
				                columns: [0,1,2,3,4,5,6,7,8]
				            }
				          
				       },
				       {
				           extend: 'excel',
				           footer: false,
				           exportOptions: {
				                columns: [0,1,2,3,4,5,6,7,8]
				            }
				       }         
				    ]  
				     
		
		});
		
		
		var table = $('#salesPersonT').DataTable({
			responsive : true,
			"destroy" : true,
			dom : 'lBfrtip',							
				  buttons: [
				       {
				           extend: 'pdf',
				           footer: true,
				           exportOptions: {
				                columns: [0,1,2,3,4,5,6,7,8]
				            }
				       },
				       {
				           extend: 'csv',
				           footer: false,
				           exportOptions: {
				                columns: [0,1,2,3,4,5,6,7,8]
				            }
				          
				       },
				       {
				           extend: 'excel',
				           footer: false,
				           exportOptions: {
				                columns: [0,1,2,3,4,5,6,7,8]
				            }
				       }         
				    ]  
				     
		
		});
		
		</script>	

	<!-- Custom Theme JavaScript -->
	<script src="../resources/dist/js/sb-admin-2.js"></script>
	<script src="../resources/js/salesInfo.js"></script>
	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<!-- <script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
            responsive: true
        });
    });
    </script> -->
</body>

</html>
