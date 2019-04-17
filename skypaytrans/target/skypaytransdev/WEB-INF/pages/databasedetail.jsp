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

<title>EzKhata DataBase Details</title>
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
<link href="../resources/dist/css/master-table-modal.css"
	rel="stylesheet">
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
		<jsp:include page="header-nav.jsp"></jsp:include>
		<input type="hidden" id="flag" value=${flag}>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">DataBase Details</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>



			<div class="row" id="rolestable">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading" id="tablePanelHeading">DataBase</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="table-responsive" id="tableArea" >
								<table width="100%"
									class="table table-striped table-bordered table-hover"
									id="rolesTable">
									<thead>
										<tr>
											<th>Name</th>
											<th>Size(MB)</th>

										</tr>
									</thead>
									


										<tr class="odd gradeX">
											<td>${databaseDetail.databaseName}</td>
											<td>${databaseDetail.size}</td>
											
											</tr>
								

								</table>



							</div>

						</div>
						<!-- /.panel-body -->

					</div>
					<!-- /.panel -->
				</div>
			</div>

			<!-- users table details -->
			<div class="row" id="usersdatabase">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading" id="tablePanelHeading">Users Table</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="table-responsive" id="tableArea">
								<table width="100%"
									class="table table-striped table-bordered table-hover"
									id="rolesTable">
									<thead>
										<tr>
											<th>Table Name</th>
											<th>Total rows</th>
											<th>Size (MB)</th>

										</tr>
									</thead>
									


										<tr class="odd gradeX">
											<td>${usersTableDetail.tableName}</td>
											<td>${usersTableDetail.rowCount}</td>
											<td>${usersTableDetail.size}</td>
											
											</tr>
									

								</table>



							</div>

						</div>
						<!-- /.panel-body -->

					</div>
					<!-- /.panel -->
				</div>
			</div>


			<!-- Transactions table detail -->

			<div class="row" id="rolestable">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading" id="tablePanelHeading">Transactions Table</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="table-responsive" id="tableArea">
								<table width="100%"
									class="table table-striped table-bordered table-hover"
									id="rolesTable">
									<thead>
										<tr>
											<th>Table Name</th>
											<th>Row Count</th>
											<th>Size (MB)</th>
										</tr>
									</thead>
									


										<tr class="odd gradeX">
											<td>${transactionTableDetail.tableName}</td>
											<td>${transactionTableDetail.rowCount}</td>
											<td>${transactionTableDetail.size}</td></tr>
									


								</table>



							</div>

						</div>
						<!-- /.panel-body -->

					</div>
					<!-- /.panel -->
				</div>
			</div>



			<!-- All Table details -->


			<div class="row" id="rolestable">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading" id="tablePanelHeading">All Table Details</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="table-responsive table-fixed" id="tableArea" style="max-height:300px;">
								<table width="100%"
									class="table table-striped table-bordered table-hover"
									id="rolesTable">
									<thead>
										<tr>
											<th>Table Name</th>
											<th>Row Count</th>
											<th>Size (MB)</th>

										</tr>
									</thead>
									<c:forEach var="list" items="${tablesList}">


										<tr class="odd gradeX">
											<td>${list.tableName}</td>
											<td>${list.rowCount}</td>
											<td>${list.size}</td>
										</tr>
									</c:forEach>


								</table>



							</div>

						</div>
						<!-- /.panel-body -->

					</div>
					<!-- /.panel -->
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

	<!-- Custom Theme JavaScript -->
	<script src="../resources/dist/js/sb-admin-2.js"></script>
	<script src="../resources/js/master-table.js"></script>
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
