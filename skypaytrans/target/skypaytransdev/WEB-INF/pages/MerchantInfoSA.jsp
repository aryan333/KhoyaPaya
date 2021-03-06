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
					<h1 class="page-header">Merchant Info by Sales Person</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			           
			           
			<div class="row">
				<div class="col-md-3">
				
						<div class="form-group">
								<label>Sales Person : </label> <select id="select"
									class="form-control">
									<option value="0">All</option>
									<c:forEach var="salePersonInfo" items="${salePersonInfo}"  varStatus="loop">
									<option value="${salePersonInfo.salesPersonId}"> ${salePersonInfo.salesPersonName}</option>
									</c:forEach>
								</select>


							</div>
							
							</div>
							</div>
							
							<button type="button"  class="btn"  style="float: right; margin-right: 10px;"
										id="allMerchantsBySPMapBtn">View All Merchants By SP on Map</button>
							
			
			
			
			<div class="row"  >
				<div class="col-lg-12">
					<div class="panel panel-default">

						<!-- /.panel-heading -->
						<div class="form-group">
					
							<div class="table-responsive" id="tableArea">
								<table id="merchantInfo" width="100%"
									class="table table-striped table-bordered table-hover"
									 >
							      <thead>
										<tr>
											
											<th>Sr No</th>
											 <th>Merchant Name</th>
				                            
				                            <th>Merchant Mobile</th>
				                            <th>Is Merchant</th>
				                            <th>Created On</th>
				                             
				                             <th>Enterprise</th>
				                              <th>Nature Of Business</th>
				                             <th>Installation Medium</th>
				                             <th>Installation Status</th>
				                             <th>Sector/Locality Type</th>
				                              <th>City</th>
				                               <th>State</th>
				                               <th>Shop Photo</th>
											
											<th>SalesPerson Name</th>
											
				                            <th>SalesPerson Mobile</th>
				                            
				                            
				                            
				                           
				                             
				                            
				                           
										</tr>
									</thead>
									<c:forEach var="merchnatInfoBySp" items="${merchnatInfoBySp}"  varStatus="loop">


										<tr class="odd gradeX" >
										
										
										     
										     <td >${loop.index+1}</td>
										     <td >${merchnatInfoBySp.name}</td>
											<td >${merchnatInfoBySp.mobileNumber}</td>
											<td >${merchnatInfoBySp.merchant}</td>
											<td >${merchnatInfoBySp.createdOn}</td>
											<td >${merchnatInfoBySp.enterprise}</td>
											<td>${merchnatInfoBySp.natureOfBusiness}</td>
											<td >${merchnatInfoBySp.installationMedium}</td>
											<td >${merchnatInfoBySp.installationStatus}</td>
											<td >${merchnatInfoBySp.shopArea}</td>
											<td >${merchnatInfoBySp.city}</td>
											<td >${merchnatInfoBySp.state}</td>
											<td class="text-center">
											<c:choose>
											<c:when test="${merchnatInfoBySp.shopBlobId==null}">
											<span>  Not Available</span>
											</c:when>
											<c:otherwise >
											
											<button    type="button" class="btn btn-link merchantInfoBySP" src="${merchnatInfoBySp.shopBlobId}" data-toggle="modal" data-target="#onMerchantPic">View Photo</button>
											
											
											</c:otherwise>
											
											
											</c:choose>
											
											</td>
											<td class="p-3 mb-2 bg-success text-white" >${merchnatInfoBySp.salesPersonName}</td>
											<td class="p-3 mb-2 bg-success text-white" >${merchnatInfoBySp.salesPersonMobileNumber}</td>
											
											
											
											
											
										   
										    
										    
										    
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
			
			
			
			
			<div class="modal fade" id="onMerchantPic" tabindex="-1"
				role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">�</button>
							<h4 class="modal-title" id="onBoardedUsersListModalDivLabel">Merchant Shop Image</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div  class="col-md-12">
									<img id="merchantShopPic" src="" style="max-height:100%;max-width:100%;"/>
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
			
			var table = $('#merchantInfo').DataTable({
				responsive : true,
				"destroy" : true,
				dom : 'lBfrtip',
					  buttons: [
					       {
					           extend: 'pdf',
					           footer: true,
					           exportOptions: {
					                columns: [1,2,3,4,5,6,7,8,9,10,11,12,13,14]
					            }
					       },
					       {
					           extend: 'csv',
					           footer: false,
					           exportOptions: {
					                columns: [1,2,3,4,5,6,7,8,9,10,11,12,13,14]
					            }
					          
					       },
					       {
					           extend: 'excel',
					           footer: false,
					           exportOptions: {
					                columns: [1,2,3,4,5,6,7,8,9,10,11,12,13,14]
					            }
					       }         
					    ]  
					     
			
			});
			
			</script>
			
			
			
			

	<!-- Custom Theme JavaScript -->
	<script src="../resources/dist/js/sb-admin-2.js"></script>
	<script src="../resources/js/merchantInfoBySp.js"></script>
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
