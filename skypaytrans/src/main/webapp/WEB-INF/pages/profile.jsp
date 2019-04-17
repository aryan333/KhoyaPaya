<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>My Profile</title>
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
					<h1 class="page-header">Profile</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">My Profile Settings</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<!-- Nav tabs -->
							<ul class="nav nav-tabs">
								<li class="active"><a href="#profileDetails"
									data-toggle="tab">Profile Details</a></li>
								<li><a href="#editProfile" data-toggle="tab">Edit
										Profile</a></li>
								<li><a href="#changePassword" data-toggle="tab">Change
										Password </a></li>
							</ul>

							<!-- Tab panes -->
							<div class="tab-content">
								<div class="tab-pane fade in active" id="profileDetails">
									<br>
									<div>
										<c:if test="${not empty update}">
											<span style="color: green">profile successfully
												updated</span>
										</c:if>
										<c:if test="${not empty passUpdate}">
											<span style="color: green">password successfully updated</span>
										</c:if>
									</div>
									<br>
									<div class="row">
										<div class="col-xs-12 col-sm-6 col-md-6">
											<div class="well well-sm">

												<div class="row">
													<div class="col-xs-12 col-sm-8 col-md-8">
														<dl class="dl-horizontal">
															<dt>NAME :</dt>
															<dd>${sessionScope.user.firstName}
																${sessionScope.user.lastName}</dd>
															<br>
															<dt>GENDER :</dt>
															<dd>${sessionScope.user.userDetailDTO.gender}</dd>
															<br>
															<dt>ADDRESS :</dt>
															<dd>${sessionScope.user.userDetailDTO.address1}</dd>
															<br>
															<dt>EMAIL ID :</dt>
															<dd>${sessionScope.user.userDetailDTO.emailId}</dd>
															<br>
															<dt>Role :</dt>
															<c:forEach items="${sessionScope.user.rolesDTO}"
																var="roles">
																<dd>${roles.role}</dd>
															</c:forEach>
															<br>
															<dt>USER LOGIN :</dt>
															<dd>${sessionScope.user.userLogin}</dd>
														</dl>
													</div>

												</div>
											</div>
										</div>

									</div>
								</div>
								<div class="tab-pane fade" id="editProfile">
									<div class="col-md-8 col-sm-6 col-xs-12 personal-info">
										<br>
										<form class="form-horizontal" role="form" id="updateProfile"
											method="post" action="profile">
											<fieldset>
												<div class="form-group">
													<label class="col-lg-3 control-label">First name:</label>
													<div class="col-lg-8">
														<input class="form-control"
															value="${sessionScope.user.firstName}" id="firstName"
															name="firstName" type="text" maxlength="15" autofocus>
														<div>
															<span id="firstNameInputError" class="text-danger"></span>
														</div>
													</div>
												</div>
												<div class="form-group">
													<label class="col-lg-3 control-label">Last name:</label>
													<div class="col-lg-8">
														<input class="form-control"
															value="${sessionScope.user.lastName}" id="lastName"
															name="lastName" maxlength="15" type="text">
														<div>
															<span id="lastNameInputError" class="text-danger"></span>
														</div>
													</div>
												</div>

												<div class="form-group">
													<label class="col-lg-3 control-label">Gender:</label>
													<div class="col-lg-8">
														<label class="radio-inline"> <c:set var="gender"
																value="${sessionScope.user.userDetailDTO.gender}" /> <input
															type="radio" name="userDetail.gender" id="genderM"
															value="M"
															${sessionScope.user.userDetailDTO.gender =='M'?'checked':''}>
															Male
														</label> <label class="radio-inline"> <input type="radio"
															name="userDetail.gender" id="genderF" value="F"
															${sessionScope.user.userDetailDTO.gender =='F'?'checked':''}>Female
														</label>
													</div>
												</div>
												<div class="form-group">
													<label class="col-lg-3 control-label">Address:</label>
													<div class="col-lg-8">
														<input class="form-control"
															value="${sessionScope.user.userDetailDTO.address1}"
															name="userDetail.address1" id="address1" type="text"
															maxlength="30">
													</div>
												</div>
												<div class="form-group">
													<label class="col-lg-3 control-label">Email:</label>
													<div class="col-lg-8">
														<input class="form-control"
															value="${sessionScope.user.userDetailDTO.emailId}"
															name="userDetail.emailId" id="emailId" type="text">
														<div>
															<span id="emailIdInputError" class="text-danger"></span>
														</div>
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label">User Login:</label>
													<div class="col-md-8">
														<input class="form-control"
															value=" ${sessionScope.user.userLogin}" type="text"
															readonly="readonly" id="userName">
													</div>
												</div>


												<div class="form-group" style="display: none">
													<label class="col-md-3 control-label">UserId:</label>
													<div class="col-md-8">
														<input class="form-control"
															value="${sessionScope.user.webUserId}" name="webUserId"
															type="hidden" id="userId">
													</div>
												</div>
												<div class="form-group">
													<input type="hidden" name="${_csrf.parameterName}"
														value="${_csrf.token}" />
												</div>
												<div>
													<span style="color: red">${notValid}</span>
												</div>

												<div class="form-group">
													<label class="col-md-3 control-label"></label>
													<div class="col-md-8">
														<input class="btn btn-primary" value="Update Profile"
															type="button" id="btn1"> <span></span> <input
															class="btn btn-default" value="Cancel" type="reset">
													</div>

													<div>
														<span style="color: red" id="notExistSpan">${notExist}</span>
													</div>

												</div>
											</fieldset>
										</form>

									</div>
								</div>
								<div class="tab-pane fade" id="changePassword">
									<div class="col-md-8 col-sm-6 col-xs-12 personal-info">
										<br>
										<form class="form-horizontal" role="form" id="updatePassword">
											<fieldset>
												<div class="form-group">
													<label class="col-lg-3 control-label">Current
														Password:</label>
													<div class="col-lg-8">
														<input class="form-control" id="curPassField"
															name="currentPassword" type="password" autofocus>
														<div>
															<!-- <span id="firstNameInputError" class="text-danger"></span> -->
														</div>
													</div>
												</div>
												<div class="form-group">
													<label class="col-lg-3 control-label">New Password:</label>
													<div class="col-lg-8">
														<input class="form-control" id="newPassField"
															name="newPassword" type="password">

													</div>
												</div>
												<div class="form-group">
													<label class="col-lg-3 control-label">Confirm
														Password:</label>
													<div class="col-lg-8">
														<input class="form-control" name="confPassword"
															id="confPassField" type="password" maxlength="20">
														<div>
															<!-- <span id="lastNameInputError" class="text-danger"></span> -->
															<span class="text-danger" id="responseMessage"></span>
														</div>
													</div>
												</div>
												<div class="form-group">
													<input type="hidden" name="${_csrf.parameterName}"
														value="${_csrf.token}" />
												</div>					

												<div class="form-group">
													<label class="col-md-3 control-label"></label>
													<div class="col-md-8">
														<input class="btn btn-primary" value="Change Password"
															type="button" id="changePassBtn">
													</div>
												</div>
											</fieldset>
										</form>

									</div>
								</div>


							</div>



						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>

		</div>
		<!-- /.container-fluid -->
	</div>
	<!-- /#page-wrapper -->

	<!-- </div> -->
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="../resources/vendor/jquery/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="../resources/vendor/bootstrap/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="../resources/vendor/metisMenu/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="../resources/dist/js/sb-admin-2.js"></script>
	<script src="../resources/js/update-profile.js"></script>

</body>

</html>