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
						<h1 class="page-header">QR Generation</h1>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
				<!-- Sending global notification to users -->
				<div class="panel panel-default" style="position: relative;">
            <div class="panel-heading">
                            How Many QR Codes?.
            </div>
				<div class="row">
                                <div class="col-md-12">
                                    <div class="form-group has-error has-danger">                                    
                                        <label for="form_message">Enter number of QR codes to generate</label>
                                        <input type="text" id="numbersOfQRCodes" class="form-control">
                                        </div>
                                </div>
                                <div>
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
								</div>
                                <div class="col-md-12">
                                    <input type="button" id="sendButton" class="btn btn-success" value="Generate">
                                </div>
                </div>
                </div>
                
                
		<!-- Sending global notification to users --- end -->
			<!-- </div> -->
			<!-- /.container-fluid -->
	<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading" id="userListHeadingDiv">Ezkhata
							Users List</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<table width="100%"
								class="table table-striped table-bordered table-hover"
								id="qrCodesDetail">
								<thead>
									<tr>
										<th>QR ID</th>
										<th>Folder Name</th>
										<th>File Name.</th>
										<th>Created On</th>
										<th>Download</th>
										<th>Send Email</th>
									</tr>
								</thead>

							</table>
	
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
	
	<script src="../resources/js/qrcodes.js"></script>
<script
				src="../resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
			<script
				src="../resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
			<script
				src="../resources/vendor/datatables-responsive/dataTables.responsive.js"></script>

</body>

</html>