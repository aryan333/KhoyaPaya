<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Find a User</title>
<link rel="icon" href="../resources/images/favicon.ico"
	type="image/x-icon">
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
					<h1 class="page-header">Find User</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="form-group">
				<p>
					<input id="mobileNumber" value="${mob}" type="text" placeholder="Enter Phone No."
						class="form-control" style="width: 30%;">
				<div id="mobileInputErrorDiv" style="display: none;">
					<span id="mobileInputError" style="color: red"></span>
				</div>
				<br>
				<button type="button" class="btn btn-primary" id="searchUser">Search</button>
				</p>
			</div>
			
			  
				<div class="row" id="userSendNotifyDiv" style="display:none;">
                                <div class="col-md-12">
                                    <div class="form-group has-error has-danger">                                    
                                        <label for="form_message">Message *</label>
                                        <textarea id="form_message" name="message" class="form-control" placeholder="Message for users" rows="4" required="required" data-error="Please,leave us a message."></textarea>
                                        <div class="help-block with-errors"><ul class="list-unstyled"><li>Please,Write a message.</li></ul></div>
                                    </div>
                                </div>
                                <div>
                                     <input type="hidden" id="userMobile" value="">
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
								</div>
                                <div class="col-md-12">
                                    <input type="button" id="sendButton" class="btn btn-success" value="Send message">
                                </div>
                </div>
                

			<div class="row" id="afterDeactivationDiv" style="display: none;">
				<div class="col-lg-12">
					<div class="form-group">
						<label style="color: green">User is Successfully
							Deactivated. Reason : <span id="afterDeactivationReason"
							style="font-weight: bolder; color: red"></span>
						</label>
					</div>
				</div>
				<!-- /.col-lg-12 -->
			</div>

			<div class="row" id="dropDownActionDiv" style="display: none;">
				<div class="col-lg-12">
					<div class="form-group">
						<label>Want to deactivate user ? Choose from options below
							:</label> <select class="form-control" id="action">
							<option value="disable">Disable User</option>
							<option value="lock">Lock Account</option>
							<option value="expAcc">Expire Account</option>
							<option value="expCred">Expire Credentials</option>
						</select>
					</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
					<button type="button" class="btn btn-primary" id="deactivateBtn">Take
						Action</button>

				</div>
				<!-- /.col-lg-12 -->
			</div>

			<div class="row" id="userDeactivationReasonDiv" style="display: none;">
				<div class="col-lg-12">
					<div class="form-group">
						<label style="color: red" id="deactivationLabel">
							Currently user is deactivated. Reason : <span
							id="deactivationReason" style="font-weight: bolder; color: red"></span>
						</label> <br>Want to Activate ?
						<button type="button" class="btn btn-primary" id="activateBtn">Click
							here</button>

					</div>
					<div>
						<span id="userDeactivationMsg" style="color: green"></span>
					</div>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<br>
			<div class="row" id="userInfoDiv" style="display: none;">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">User Information</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="table-responsive" id="tableArea"></div>
							<!-- /.table-responsive -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
			</div>
			<div class="row" id="transInfoDiv" style="display: none;">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Transaction Statistics</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="table-responsive" id="tableTxnArea"></div>
							<!-- /.table-responsive -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
			</div>



			<!-- /.row -->
			<div class="row" id="associatedUsersDiv" style="display: none;">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading" id="userListHeadingDiv">Associated
							Users</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<table width="100%"
								class="table table-striped table-bordered table-hover"
								id="associatedUsers">
								<thead>
									<tr>
										<th>Sr No.</th>
										<th>EZKhata Name</th>
										<th>Mapped Name</th>
										<th>Phone Number</th>
										<th>Balance</th>
										<th>is Active</th>
										<th>Relation</th>
										<th>User Type</th>
										<th>Mapped Date</th>

									</tr>
								</thead>

							</table>
							<!-- /.table-responsive -->
							<!--  <div class="well">
                                <h4>DataTables Usage Information</h4>
                                <p>DataTables is a very flexible, advanced tables plugin for jQuery. In SB Admin, we are using a specialized version of DataTables built for Bootstrap 3. We have also customized the table headings to use Font Awesome icons in place of images. For complete documentation on DataTables, visit their website at <a target="_blank" href="https://datatables.net/">https://datatables.net/</a>.</p>
                                <a class="btn btn-default btn-lg btn-block" target="_blank" href="https://datatables.net/">View DataTables Documentation</a>
                            </div> -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->











			<!-- </div> -->
			<!-- /.container-fluid -->
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

	<script
		src="../resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
	<script
		src="../resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
	<script
		src="../resources/vendor/datatables-responsive/dataTables.responsive.js"></script>

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

	<!-- Custom Theme JavaScript -->
	<script src="../resources/dist/js/sb-admin-2.js"></script>
	<script src="../resources/js/find-user.js"></script>
	<script>
		$(document).ready(function() {
			hideDivs();
			findUser.init();
		});
	</script>
</body>

</html>