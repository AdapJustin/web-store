<%-- 
    Document   : login
    Created on : 05 11, 21, 3:00:41 PM
    Author     : Justin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Login</title>
        <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,700' rel='stylesheet' type='text/css'>
        <link href="style1.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="LoginSignUpStyle.css">
        <link rel="stylesheet" href="landing_style.css">
        <link rel="stylesheet" href="aos.css">
        <script src="https://www.google.com/recaptcha/api.js"></script>
    </head>
    <body>
         <div class="container">
            <div class="user"><jsp:include page="header.jsp" /></div>
            <div class="topnav">
                <button class="cart" id="cart"><i style="font-size:24px" class="fa">&#xf07a;</i> ${CartItems}</button>
                <form class="shop" action="Home.jsp">
                    <input type="submit" value="Home">
                </form>
                <form class="shop" action="ItemsServlet">
                    <input type="hidden" name="ItemSort" value="All">
                    <input type="submit" value="Build-A-PC">
                </form>
                <form class="shop" action="ItemsServlet">
                    <input type="hidden" name="ItemSort" value="All">
                    <input type="submit" value="Shop" id="shopBtn">
                </form>
            </div>
        </div>
        <div class="InputContainer" data-aos="zoom-in-up">
            
            <form class="inputForm" method="POST" action="Login">
                <h4>WELCOME!</h4>
                <input type="text" name="username" placeholder="email"/>
                <input type="password" name="password" placeholder="password"/>
                <div class="g-recaptcha" data-sitekey="6LcXOJYaAAAAAOvL4hPKH46NuhpxEG3XgQOCtZ0k">
                </div> 
                <input type="hidden" name="ItemSort" value="All">
                <span class="error">${error}</span>
                <input type="submit" value="Login">
                
                <a href="register.jsp">Don't have an account? Sign up here!</a>
            </form>
        </div>
        <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script src="aos.js"></script>
        <script>
            AOS.init({duration: 2000,
            });
        </script>
        <script>
            (function () {
                $("#cart").on("click", function () {
                    $(".shopping-cart").fadeToggle("fast");
                });

            })();

        </script>
    </body>
</html>
