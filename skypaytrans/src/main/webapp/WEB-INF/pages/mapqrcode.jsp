<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>QR Generation</title>
<link rel="icon" href="../resources/images/favicon.ico" type="image/x-icon">
<!-- Bootstrap Core CSS -->
<link href="../resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="../resources/vendor/metisMenu/metisMenu.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="../resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="../resources/vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

	<div id="wrapper">

		<!-- Navigation -->
		<jsp:include page="header-nav.jsp"></jsp:include>

		<!-- Page Content -->
		<div id="page-wrapper">
			<!-- <div class="container-fluid"> -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">QR Mapping</h1>
					</div>
					
					
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
				<!-- Sending global notification to users -->
				<div class="panel panel-default" style="position: relative;">
				<div class="panel-heading">
                            
            </div>
				<div class="row">
						
                                <div class="col-md-12">
                                <form id="qrMapForm">
                                    <div class="form-group has-error has-danger">                                    
                                        <label for="form_message">Choose a csv file to map qr codes</label>
                                        <input type="file" name="files" id="qrFile" class="form-control">
                                        </div>
                                  </form>
                                </div>
                              
						
					
                                <div>
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
								</div>
                                <div class="col-md-12">
                                    <input type="button" id="sendButton" class="btn btn-success" value="Map">
                                     <input type="button" id="viewCSV" class="btn btn-success" value="View CSV Format" data-toggle="modal"
													data-target="#csvModel">
                                </div>
                                
                            
                </div>
               
                
                
		<!-- Sending global notification to users --- end -->
			<!-- </div> -->
			<!-- /.container-fluid -->
	<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading" id="userListHeadingDiv">Mapped
							Users List</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<table width="100%"
								class="table table-striped table-bordered table-hover"
								id="mapQrCodesDetail">
								<thead>
									<tr>
										<th>Name</th>
										<th>Mobile Number</th>
										<th>QRSeries</th>
										<th>MappedOn</th>
									</tr>
								</thead>

							</table>
	
	</div>
	</div>
	</div>
	</div>
			
			
		<div class="modal fade" id="csvModel" tabindex="-1"
				role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">×</button>
							<h4 class="modal-title" id="onBoardedUsersListModalDivLabel">CSV file format</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
									<img src="../resources/images/csv.JPG" style="height:300px;width:100%;"/>
								</div>

							</div>

						</div>
					</div>

				</div>

			</div>
			
			
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="../resources/vendor/jquery/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="../resources/vendor/bootstrap/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="../resources/vendor/metisMenu/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="../resources/dist/js/sb-admin-2.js"></script>
	
	<script src="../resources/js/mapqrcodes.js"></script>
<script
				src="../resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
			<script
				src="../resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
			<script
				src="../resources/vendor/datatables-responsive/dataTables.responsive.js"></script>

</body>

</html>