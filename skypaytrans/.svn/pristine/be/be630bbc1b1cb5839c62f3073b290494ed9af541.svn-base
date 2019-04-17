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

<title>Ezkhata Master Tables</title>
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
		<jsp:include page="header-nav.jsp"></jsp:include>
		<input type="hidden" id="flag" value=${flag}>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Master Tables</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Ezkhata Master Table List</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="form-group">
								<label>Tables : </label> <select id="select"
									class="form-control">
									<option value="role">Roles</option>
									<option value="disputeReasons">Dispute Reasons</option>
									<option value="fiscalYear">Fiscal Year</option>
									<option value="itemCategory">Item Category</option>
									<option value="relations">Relations</option>
									<option value="rewardPoints">Reward Points</option>
								</select>


							</div>
							<!-- /.table-responsive -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
			</div>
			<!-- /.row -->


			<div class="row" id="rolestable">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading" id="tablePanelHeading">Roles Table</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="table-responsive" id="tableArea">
								<table width="100%"
									class="table table-striped table-bordered table-hover"
									id="rolesTable">
									<thead>
										<tr>
											<th>Role Id</th>
											<th>IsActive</th>
											<th>Role</th>
											<th>RoleDescription</th>
											<th>CreatedBy</th>
											<th>CreatedOn</th>
											<th>ModifiedBy</th>
											<th>ModifiedOn</th>
											<!-- <th>Edit/Delete</th> -->
										</tr>
									</thead>
									<c:forEach var="role" items="${roles}">


										<tr class="odd gradeX">
											<td>${role.id}</td>
											<td>${role.active}</td>
											<td>${role.role}</td>
											<td>${role.roleDescription}</td>
											<td>${role.createdBy}</td>
											<td>${role.createdOn}</td>
											<td>${role.modifiedBy}</td>
											<td>${role.modifiedOn}</td>
											<!-- <td><button type="button" class="btn btn-link"
												data-toggle="modal" data-target="">Edit</button>
												/ <button type="button" class="btn btn-link"
												data-toggle="modal" data-target="">Delete</button></td> -->
										</tr>
									</c:forEach>


								</table>



							</div>
							<!-- /.table-responsive -->
							<div class="col-lg-6">
								Click here to <button class="btn btn-link"
									data-toggle="modal" data-target="#rolesModal" id="addRecordBtn">Add New
									Record</button>
							</div>

							<div class="modal fade" id="rolesModal" tabindex="-1"
								role="dialog" aria-labelledby="myLargeModalLabel"
								aria-hidden="true">
								<div class="modal-dialog modal-md">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">×</button>
											<h4 class="modal-title" id="rolesModalLabel">Add New
												Record</h4>
										</div>
										<div class="modal-body">
											<div class="row">
												<div class="col-md-8">
													<form role="form" method="post" action="registerWebUser">
														<div class="form-group">
															<label>Role Id</label> <input class="form-control"
																name="id" id ="roleId" value="" disabled>
															<!--  <p class="help-block">Example block-level help text here.</p> -->
														</div>
														<div class="form-group">
															<label>Role</label> <input type=text class="form-control"
																name="role" id="role">
															<!-- placeholder="Enter text" -->
														</div>
														<div class="form-group">
															<label>Role Description</label> <input
																class="form-control" type="text" name="roleDescription" id="roleDescription">
														</div>

														<div class="form-group">
															<div>
																<label>Is Active</label>
															</div>
															<div>
																<label class="radio-inline"> <input type="radio"
																	name="roleactive"
																	value="true" checked>Yes
																</label> <label class="radio-inline"> <input
																	type="radio" name="roleactive"
																	 value="false">No
																</label>
															</div>
														</div>
														<div class="form-group">
															<input type="hidden" name="${_csrf.parameterName}"
																value="${_csrf.token}" />
														</div>
														<button type="button" class="btn btn-primary btn-sm" id="saveRoleBtn">
															Save & Continue</button>
														<button type="button" class="btn btn-default btn-sm">
															Cancel</button>


													</form>
												</div>

											</div>

										</div>
									</div>

								</div>

							</div>

							<div class="modal fade" id="disputeReasonsModal" tabindex="-1"
								role="dialog" aria-labelledby="myLargeModalLabel"
								aria-hidden="true">
								<div class="modal-dialog modal-md">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">×</button>
											<h4 class="modal-title" id="disputeReasonsModalLabel">Add
												New Record</h4>
										</div>
										<div class="modal-body">
											<div class="row">
												<div class="col-md-8">
													<form role="form" method="post">
														<div class="form-group">
															<label>Dispute Reason Id</label> <input
																class="form-control" name="disputeReasonId" id="disputeReasonId" value="" disabled>
															<!--  <p class="help-block">Example block-level help text here.</p> -->
														</div>
														<div class="form-group">
															<label>Dispute Reason</label> <input type=text
																class="form-control" name="disputeReason" id="disputeReason">
															<!-- placeholder="Enter text" -->
														</div>
														<div class="form-group">
															<label>Dispute Reason Description</label> <input
																class="form-control" type="text" name="disputeReasDes" id="disputeReasDes">
														</div>

														<div class="form-group">
															<div>
																<label>Is Active</label>
															</div>
															<div>
																<label class="radio-inline"> <input type="radio"
																	name="reasonactive" value="true"
																	 checked>Yes
																</label> <label class="radio-inline"> <input
																	type="radio" name="reasonactive"
																	 value="false">No
																</label>
															</div>
														</div>
														<div class="form-group">
															<input type="hidden" name="${_csrf.parameterName}"
																value="${_csrf.token}" />
														</div>
														<button type="button" class="btn btn-primary btn-sm" id="saveDisputeBtn">
															Save & Continue</button>
														<button type="button" class="btn btn-default btn-sm">
															Cancel</button>


													</form>
												</div>

											</div>

										</div>
									</div>

								</div>

							</div>

							<div class="modal fade" id="fiscalYearModal" tabindex="-1"
								role="dialog" aria-labelledby="myLargeModalLabel"
								aria-hidden="true">
								<div class="modal-dialog modal-md">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">×</button>
											<h4 class="modal-title" id="fiscalYearModalLabel">Add
												New Record</h4>
										</div>
										<div class="modal-body">
											<div class="row">
												<div class="col-md-8">
													<form role="form" method="post" action="registerWebUser">
														<div class="form-group">
															<label>Fiscal Year Id</label> <input class="form-control"
																name="id" id="fyId" value="" disabled>
															<!--  <p class="help-block">Example block-level help text here.</p> -->
														</div>
														<div class="form-group">
															<label>FY Start Date</label> <input type=text
																class="form-control" name="startDate" id="startDate">
															<!-- placeholder="Enter text" -->
														</div>
														<div class="form-group">
															<label>FY End Date</label> <input class="form-control"
																type="text" name="endDate" id="endDate">
														</div>

														<div class="form-group">
															<div>
																<label>Is Active</label>
															</div>
															<div>
																<label class="radio-inline"> <input type="radio"
																	name="fyactive"
																	value="true" checked>Yes
																</label> <label class="radio-inline"> <input
																	type="radio" name="fyactive"
																	value="false">No
																</label>
															</div>
														</div>
														<div class="form-group">
															<input type="hidden" name="${_csrf.parameterName}"
																value="${_csrf.token}" />
														</div>
														<button type="button" class="btn btn-primary btn-sm" id ="saveFYBtn">
															Save & Continue</button>
														<button type="button" class="btn btn-default btn-sm">
															Cancel</button>


													</form>
												</div>

											</div>

										</div>
									</div>

								</div>

							</div>

							<div class="modal fade" id="itemCategoryModal" tabindex="-1"
								role="dialog" aria-labelledby="myLargeModalLabel"
								aria-hidden="true">
								<div class="modal-dialog modal-md">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">×</button>
											<h4 class="modal-title" id="itemCategoryModalLabel">Add
												New Record</h4>
										</div>
										<div class="modal-body">
											<div class="row">
												<div class="col-md-8">
													<form role="form" method="post" action="registerWebUser">
														<div class="form-group">
															<label>Item Category Id</label> <input
																class="form-control" name="id" id="icId" value="" disabled>
															<!--  <p class="help-block">Example block-level help text here.</p> -->
														</div>
														<div class="form-group">
															<label>Category Name</label> <input type=text
																class="form-control" name="categoryName" id="categoryName">
															<!-- placeholder="Enter text" -->
														</div>
														<div class="form-group">
															<label>Category Type</label> <input class="form-control"
																type="text" name="categoryType" id="categoryType">
														</div>
														<div class="form-group">
															<input type="hidden" name="${_csrf.parameterName}"
																value="${_csrf.token}" />
														</div>
														<button type="button" class="btn btn-primary btn-sm" id="saveItemBtn">
															Save & Continue</button>
														<button type="button" class="btn btn-default btn-sm">
															Cancel</button>


													</form>
												</div>

											</div>

										</div>
									</div>

								</div>

							</div>

							<div class="modal fade" id="relationModal" tabindex="-1"
								role="dialog" aria-labelledby="myLargeModalLabel"
								aria-hidden="true">
								<div class="modal-dialog modal-md">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">×</button>
											<h4 class="modal-title" id="relationModalLabel">Add New Record</h4>
										</div>
										<div class="modal-body">
											<div class="row">
												<div class="col-md-8">
													<form role="form" method="post" action="registerWebUser">
														<div class="form-group">
															<label>Relation Id</label> <input class="form-control"
																name="id" id="relationId" value="" disabled>
															<!--  <p class="help-block">Example block-level help text here.</p> -->
														</div>
														<div class="form-group">
															<label>Relation</label> <input type=text class="form-control"
																name="relation" id="relation">
															<!-- placeholder="Enter text" -->
														</div>
														<div class="form-group">
															<label>Description</label> <input
																class="form-control" type="text" name="description" id="description">
														</div>

														<div class="form-group">
															<div>
																<label>Is Active</label>
															</div>
															<div>
																<label class="radio-inline"> <input type="radio"
																	name="relactive"
																	value="true" checked>Yes
																</label> <label class="radio-inline"> <input
																	type="radio" name="relactive"
																	 value="false">No
																</label>
															</div>
														</div>
														<div class="form-group">
															<input type="hidden" name="${_csrf.parameterName}"
																value="${_csrf.token}" />
														</div>
														<button type="button" class="btn btn-primary btn-sm" id="saveRelationBtn">
															Save & Continue</button>
														<button type="button" class="btn btn-default btn-sm">
															Cancel</button>


													</form>
												</div>

											</div>

										</div>
									</div>

								</div>

							</div>

							<div class="modal fade" id="rewardPointsModal" tabindex="-1"
								role="dialog" aria-labelledby="myLargeModalLabel"
								aria-hidden="true">
								<div class="modal-dialog modal-md">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">×</button>
											<h4 class="modal-title" id="rewardPointsModalLabel">Add New Record</h4>
										</div>
										<div class="modal-body">
											<div class="row">
												<div class="col-md-8">
													<form role="form" method="post" action="registerWebUser">
														<div class="form-group">
															<label>Reward Points Id</label> <input class="form-control"
																name="id" id= "rpId" value="" disabled>
															<!--  <p class="help-block">Example block-level help text here.</p> -->
														</div>
														<div class="form-group">
															<label>Reward Point Name</label> <input type=text class="form-control"
																name="name" id="name">
															<!-- placeholder="Enter text" -->
														</div>														
														<div class="form-group">
															<label>Description</label> <input
																class="form-control" type="text" name="desc" id="desc">
														</div>
														<div class="form-group">
															<label>Reward Point Value</label> <input type=text class="form-control"
																name="value" id="value">
															<!-- placeholder="Enter text" -->
														</div>
														<div class="form-group">
															<div>
																<label>Is Active</label>
															</div>
															<div>
																<label class="radio-inline"> <input type="radio"
																	name="rpactive"
																	value="true" checked>Yes
																</label> <label class="radio-inline"> <input
																	type="radio" name="rpactive"
																	value="false">No
																</label>
															</div>
														</div>
														<div class="form-group">
															<input type="hidden" name="${_csrf.parameterName}"
																value="${_csrf.token}" />
														</div>
														<button type="button" class="btn btn-primary btn-sm" id="saveRewardBtn">
															Save & Continue</button>
														<button type="button" class="btn btn-default btn-sm" id="cancelBtn" data-dismiss="modal">
															Cancel</button>


													</form>
												</div>

											</div>

										</div>
									</div>

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
