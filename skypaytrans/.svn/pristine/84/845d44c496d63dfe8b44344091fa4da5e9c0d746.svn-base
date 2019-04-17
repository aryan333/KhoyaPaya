<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Login page</title>
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

	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Please Sign In</h3>
					</div>
					<div class="panel-body">
						<form role="form" action="login" method="POST" id="loginForm">
							<fieldset>
								<div class="form-group">
									<input class="form-control" placeholder="User Login"
										id="userLogin" name="username" type="text" maxlength="30"
										autocomplete="off" autofocus>
									<div>
										<span id="mobInputError" class="text-danger"></span>
									</div>
								</div>
								<br>
								<div class="form-group">
									<input class="form-control" placeholder="Password"
										id="password" name="password" type="password" maxlength="20">
									<div>
										<span id="passInputError" class="text-danger"></span>
									</div>
								</div>
								<div>
									<span style="color: red">${notValid}</span>
								</div>
								<br>
								<div class="form-group">
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
								</div>
								<!-- Change this to a button or input when using this as a form -->
								<button id="submitForm" type="button"
									class="btn btn-lg btn-success btn-block">Login</button>
							</fieldset>
						</form>
					</div>
				</div>
				<div>
					<a href="./forgotPassword.htm"><span style="color: blue">Forgot
							Password ?</span></a>
				</div>
				<c:choose>
					<c:when test="${not empty error}">
						<div class="error">
							<span style="color: red">Your login attempt was not
								successful, try again.<br /> Reason:
								${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
							</span>
						</div>
					</c:when>
					<c:when test="${not empty logout}">
						<div class="error">
							<span style="color: green"> You are successfully logged
								out. </span>
						</div>
					</c:when>
					<c:when test="${not empty resetPass}">
						<div class="error">
							<span style="color: green" id="passReset"> password is
								successfully reset.<br> now you can login with new password
							</span>
						</div>
					</c:when>
					<c:when test="${not empty sessionOut}">
						<div class="error">
							<span style="color: red" id="sessionOut">session expired.Please login again to continue.
							</span>
						</div>
					</c:when>
				</c:choose>
			</div>
		</div>
	</div>

	<!-- jQuery -->
	<script src="../resources/vendor/jquery/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="../resources/vendor/bootstrap/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="../resources/vendor/metisMenu/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="../resources/dist/js/sb-admin-2.js"></script>

	<script type="text/javascript" src="../resources/js/validatelogin.js"></script>

	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/jquery.validate.min.js"></script>
</body>

</html>
