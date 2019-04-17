<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>App Preferences</title>
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
					<h1 class="page-header">App Preferences</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Set Application Preferences</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<form role="form" method="post" action="updateAppPreferences">
										<div class="form-group">
											<div>
												<label>Show Test Users</label><span
												style="color: maroon;">
												(Currently
												<c:choose>
											
											   <c:when test="${appPreference.showTestUsers ==true}">											
												Yes)
										    	</c:when>
													<c:otherwise>No)</c:otherwise>
												</c:choose> </span>
													</div>
											<div>
												<label class="radio-inline"> <input type="radio"
													name="showTestUsers" id="showTestUsersRadioYes" value="true" ${appPreference.showTestUsers==true?'checked':''}>Yes
												</label> <label class="radio-inline"> <input type="radio"
													name="showTestUsers" id="showTestUsersRadioNo" value="false" ${appPreference.showTestUsers==false?'checked':''}>No
												</label>
											</div>
										</div>
										<div class="form-group">
											<label>Referral Reward Scheme</label> <span
												style="color: maroon;">(Currently
												<c:choose>
											
											<c:when test="${appPreference.inviteBenefit ==true}">											
												Activated)
											</c:when>
													<c:otherwise>Deactivated)</c:otherwise>
												</c:choose> </span>
											<div>
												<label class="radio-inline"> <input type="radio"
													name="inviteBenefit" id="inviteBenefitYes" value="true" ${appPreference.inviteBenefit==true?'checked':''}>Activate
												</label> <label class="radio-inline"> <input type="radio"
													name="inviteBenefit" id="inviteBenefitNo" value="false" ${appPreference.inviteBenefit==false?'checked':''}>Deactivate
												</label>
											</div>
										</div>
										<div class="form-group">
											<label>Sign up Reward Scheme</label> <span
												style="color: maroon;">
												(Currently<c:choose>
											
											<c:when test="${appPreference.signupBenefit ==true}">											
												Activated)
											</c:when>
													<c:otherwise>Deactivated)</c:otherwise>
												</c:choose> </span>
											<div>
												<label class="radio-inline"> <input type="radio"
													name="signupBenefit" id="signupBenefitYes" value="true" ${appPreference.signupBenefit==true?'checked':''}>
													Activate
												</label> <label class="radio-inline"> <input type="radio"
													name="signupBenefit" id="signupBenefitNo" value="false" ${appPreference.signupBenefit==false?'checked':''}>Deactivate
												</label>
											</div>
										</div>
										<div class="form-group">
											<label>Max Points That Can Be Earned</label> <input
												class="form-control" type="text" name="maxPointsEarned"
												value="${appPreference.maxPointsEarned}">

										</div>
										<div class="form-group">
											<input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" />
										</div>
										<button type="submit" class="btn btn-default">Submit</button>

									</form>
								</div>

							</div>

							<!-- /.col-lg-6 (nested) -->
						</div>
						<!-- /.row (nested) -->
					</div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->
			</div>
			<!-- /.col-lg-12 -->
		</div>

	</div>
	<!-- /.container-fluid -->
	
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

</body>

</html>