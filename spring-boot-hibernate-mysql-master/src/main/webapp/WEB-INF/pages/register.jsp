<!doctype html>
<html class="no-js" lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Sign up - srtdash</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/png" href="../resources/assets/images/icon/favicon.ico">
    <link rel="stylesheet" href="../resources/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="../resources/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="../resources/assets/css/themify-icons.css">
    <link rel="stylesheet" href="../resources/assets/css/metisMenu.css">
    <link rel="stylesheet" href="../resources/assets/css/owl.carousel.min.css">
    <link rel="stylesheet" href="../resources/assets/css/slicknav.min.css">
    <!-- amchart css -->
    <link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css" type="text/css" media="all" />
    <!-- others css -->
    <link rel="stylesheet" href="../resources/assets/css/typography.css">
    <link rel="stylesheet" href="../resources/assets/css/default-css.css">
    <link rel="stylesheet" href="../resources/assets/css/styles.css">
    <link rel="stylesheet" href="../resources/assets/css/responsive.css">
    <!-- modernizr css -->
    <script src="../resources/assets/js/vendor/modernizr-2.8.3.min.js"></script>
</head>

<body>
    <!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
    <!-- preloader area start -->
    <div id="preloader">
        <div class="loader"></div>
    </div>
    <!-- preloader area end -->
    <!-- login area start -->
    <div class="login-area">
        <div class="container">
            <div class="login-box ptb--100">
                <form  method="post" action="addUser">
                    <div class="login-form-head">
                        <h4>Sign up</h4>
                        <p>Hello there, Sign up and Join with Us</p>
                    </div>
                    <div class="login-form-body">
                        <div class="form-gp">
                            <label for="exampleInputName1">Full Name</label>
                            <input type="text" name="name">
                            <i class="ti-user"></i>
                        </div>
                        
                         <div class="form-gp">
                            <label for="exampleInputName1">Mobile</label>
                            <input type="text" name="mobile">
                        </div>
                        
                        <div class="form-gp">
                            <label for="exampleInputEmail1">Email address</label>
                            <input type="email" name="emailId">
                            <i class="ti-email"></i>
                        </div>
                        <div class="form-gp">
                            <label for="exampleInputPassword1">Password</label>
                            <input type="password" name="password">
                            <i class="ti-lock"></i>
                        </div>
                        
                        <div class="submit-btn-area">
                            <button type="submit">Submit <i class="ti-arrow-right"></i></button>
                            
                            <div>
					            <span style="color: red" id='paramsNotValid'>${paramsNotValid}</span>
				             </div>
                            <div class="login-other row mt-4">
                                <div class="col-6">
                                    <a class="fb-login" href="#">Sign up with <i class="fa fa-facebook"></i></a>
                                </div>
                                <div class="col-6">
                                    <a class="google-login" href="#">Sign up with <i class="fa fa-google"></i></a>
                                </div>
                            </div>
                        </div>
                        <div class="form-footer text-center mt-5">
                            <p class="text-muted">Don't have an account? <a href="login.html">Sign in</a></p>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- login area end -->

    <!-- jquery latest version -->
    <script src="../resources/assets/js/vendor/jquery-2.2.4.min.js"></script>
    <!-- bootstrap 4 js -->
    <script src="../resources/assets/js/popper.min.js"></script>
    <script src="../resources/assets/js/bootstrap.min.js"></script>
    <script src="../resources/assets/js/owl.carousel.min.js"></script>
    <script src="../resources/assets/js/metisMenu.min.js"></script>
    <script src="../resources/assets/js/jquery.slimscroll.min.js"></script>
    <script src="../resources/assets/js/jquery.slicknav.min.js"></script>
    
    <!-- others plugins -->
    <script src="../resources/assets/js/plugins.js"></script>
    <script src="../resources/assets/js/scripts.js"></script>
</body>

</html>