<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Bugs</title>
<link rel="icon" href="../resources/images/favicon.ico" type="image/x-icon">
<!-- Bootstrap Core CSS -->
<link href="../resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="../resources/vendor/metisMenu/metisMenu.min.css"
	rel="stylesheet">

<!-- Jquery UI CSS -->
<link
	href="https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
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

			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Bug Reports</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="panel panel-default">
				<div class="panel-heading">Client and System Bug Reports</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<!-- Nav tabs -->
					<ul class="nav nav-tabs">
						<li class="active"><a href="#home" data-toggle="tab">Mobile
								Client Exceptions</a></li>
						<li><a href="#exception" data-toggle="tab">System
								Exceptions</a></li>						
					</ul>

					<!-- Tab panes -->
					<div class="tab-content">
						<div class="tab-pane fade in active" id="home"
							style="margin-top: 20px;">
							<div class="panel panel-default">
								<div class="panel-body">
									<div class="table table-responsive">
										<table class="table table-striped table-bordered table-hover"
											id="clientExceptions">
											<thead>
												<tr>
													<th>#</th>
													<th>User Id</th>
													<th>User Device</th>
													<th>Exception Type</th>
													<th>Exception Detail</th>
													<th>Client Type</th>
													<th>Exception Date Time</th>
												</tr>
											</thead>
										</table>
									</div>
									<!-- /.table-responsive -->
								</div>
								<!-- /.panel-body -->
							</div>
						</div>
						<div class="tab-pane fade" id="exception">
							<br>
							<div class="form-group">
								<p>
									<label>Enter Date</label> : <input name="datepicker"
										type="text" id="datepicker">
									<button type="reset" class="btn btn-default" id = "excLogBtn">Find Logs</button>
								</p>
							</div>
							<div class="panel panel-danger">
								<div class="panel-heading" id="exceptionLogHeading">Today's Logs</div>
								<div class="panel-body">
									<div class="scroll-div">
										<p id ="excepLog">${exceptions}</p>
									</div>
								</div>
							</div>

						</div>
						
					</div>
				</div>
			</div>
			<!-- /.panel-body -->
		</div>

	</div>
	<!-- /#page-wrapper -->


	<!-- /#wrapper -->

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

	<!-- <script src="https://code.jquery.com/jquery-1.12.1.js"></script> -->
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="../resources/dist/js/sb-admin-2.js"></script>
	<script src="../resources/js/bug-reports.js"></script>
	<script>
		$(document).ready(function() {
			bugReports.init();
		});
	</script>
</body>

</html>