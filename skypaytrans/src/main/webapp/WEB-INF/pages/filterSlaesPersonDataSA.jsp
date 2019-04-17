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
	
<link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
         rel = "stylesheet">	

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
					<h1 class="page-header">Filter Sales Person Information</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading" id="dateRangeHeadingDiv">Date
							Range Selection</div>
						<!-- /.panel-heading -->
						<div class="form-group">
							<br>
							<p class="text-center">
								<label class="text-left">Start Date</label> : <input
									name="startdatepicker" type="text" id="startdatepicker" readonly='true'>
								<label class="text-right">End Date</label> : <input
									name="enddatepicker" type="text" id="enddatepicker" readonly='true'>
								<button type="reset" class="btn btn-primary" id="findSalesInfoBtn">APPLY</button>
							</p>
							<br>
							
							<div class="table-responsive" id="tableArea">
								<table id="salesPersonFilter" width="100%"
									class="table table-striped table-bordered table-hover"
									 >
							      <thead>
										<tr>
											
											<th>Sr No</th>
											
											<th>SalesPerson Name</th>
											
				                            <th>SalesPerson Mobile</th>
				                            
				                            <th>Invite Code</th>
				                            
				                            <th> Last Activity</th>
				                             
				                             <th> AV Count</th>
				                             
				                             <th> Total Register User Count</th>
				                             
				                              <th> Total NAP User Count</th>
				                           
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
											<td><button invitecode="${salesRefDetails.slaesPersonInviteCode}" salesPersonName="${salesRefDetails.salesPersonName}"   dateRange="all"  type="button" class="btn btn-link totalUsersBtnNAP" data-toggle="modal" data-target="#onReferalUsers">${salesRefDetails.totalCountNAP}</button></td>
										   
										    
										    
										    
											<!-- <td><button type="button" class="btn btn-link"
												data-toggle="modal" data-target="">Edit</button>
												/ <button type="button" class="btn btn-link"
												data-toggle="modal" data-target="">Delete</button></td> -->
										</tr>
										
										
										
									</c:forEach>
									
									
									
									

								</table>



							</div>
						</div>
					</div>
				</div>
			</div>
			
</div>
</div>		







			<!-- /.row -->
			
			
			
			
			<div class="modal fade" id="onReferalUsers" tabindex="-1"
				role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close"  data-dismiss="modal"
								aria-hidden="true">×</button>
								
								<button type="button"  class="btn" style="float: right; margin-right: 10px;"
										id="viwonMap">View All Users on Map</button>
													
							<h4 class="modal-title" id="onReferalUsersLabel" type="text"> Referee Name
								</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
								
								<br>
							<p class="text-center">
								<label class="text-left">Start Date</label> : <input
									name="startdatepicker" type="text" id="startdatepicker1" readonly='true'>
								<label class="text-right">End Date</label> : <input
									name="enddatepicker" type="text" id="enddatepicker1" readonly='true'>
								<button type="reset" class="btn btn-primary" id="filterUsersInModalBtn">APPLY</button>
							</p>
							<br>
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
			
		<!--date picker jquery  -->
			<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		
			
    
				
			
			
			
			<script >
			
			var table = $('#salesPersonFilter').DataTable({
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
	<script src="../resources/js/filterSalesPersonData.js"></script>
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
