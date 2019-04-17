<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Send Notification to Blupay Users</title>
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


<style>
#bluePayUsersTable_filter{
text-align: right;
}
</style>
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
						<h1 class="page-header">Notification</h1>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
				<!-- Sending global notification to users -->
				<div class="panel panel-default" style="position: relative;">
            <div class="panel-heading">
                            Send Notification To All Users.
            </div>
				<div class="row">
                                <div class="col-md-12">
                                    <div class="form-group has-error has-danger">                                    
                                        <label for="form_message">Message *</label>
                                        <textarea id="form_message" name="message" class="form-control" placeholder="Message for users" rows="4" required="required" data-error="Please,leave us a message."></textarea>
                                        <div class="help-block with-errors"><ul class="list-unstyled"><li>Please,Write a message.</li></ul></div>
                                    </div>
                                </div>
                                <div>
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
								</div>
                                <div class="col-md-12">
                                    <input type="button" id="sendButton" class="btn btn-success" value="Send message">
                                </div>
                </div>
                
                
                
                
                
                </div>
                
          <div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
					<div class="col-lg-12">
						<div class=" col-lg-6 panel-heading" id="userListHeadingDiv">Ezkhata
							Users List</div>
						<div class="col-lg-6" id="userListHeadingDiv">
						<button id="notifyAll" class="btn btn-success pull-right">Notify Selected</button>
						</div>
					</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<table width="100%"
								class="table table-striped table-bordered table-hover"
								id="bluePayUsersTable">
								<thead>
									<tr>
										<th>#</th>
										<th>Name</th>
										<th>Mobile No.</th>
										<th>User Type</th>
										<th>Signup Date Time</th>
										<th>Check To Notify</th>
									</tr>
								</thead>

							</table>

						</div>
					</div>
				</div>
			</div>
		<!-- Sending global notification to users --- end -->
			<!-- </div> -->
			<!-- /.container-fluid -->
			
			
			
			
			<div class="modal fade" id="notifyALlModel" tabindex="-1"
				role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">×</button>
							<h4 class="modal-title" id="notifyALlModel1">Send Notification</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-md-12">
                                    <div class="form-group has-error has-danger">                                    
                                        <label for="form_message">Message *</label>
                                        <textarea id="form_message_all" name="message" class="form-control" placeholder="Message for users" rows="4" required="required" data-error="Please,leave us a message."></textarea>
                                        <div class="help-block with-errors"><ul class="list-unstyled"><li>Please,Write a message.</li></ul></div>
                                    </div>
                                </div>
                                <div>
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
								</div>
                                <div class="col-md-12">
                                    <input type="button" id="sendMessageToAll" class="btn btn-md btn-success" value="Send message">
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
	<script src="../resources/js/notification.js"></script>
	
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
	
	<script type="text/javascript">
	$(document).ready(function() {		
		bluPay.init();
		
	});
	</script>

</body>

</html>