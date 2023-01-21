<%-- 
    Document   : header
    Created on : 05 12, 21, 4:45:41 PM
    Author     : Justin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="header.css">
    </head>
    <body>
        <input type="hidden" id="status" name="status "value="${status}">  
        
        <div class="topnav1">
            <a class="logo" href="Home.jsp"><img src="img/logo.png" alt="Homepage"></a>
            <h4 id="greeting">Hello ${userName}!</h4>
            <form class="UserBtn" id="logout" method="GET" action="Logout" >
                
                <input type="hidden" name="loginCart">
                <input type="submit" value="Logout">          
            </form>
            <form id="login" method="GET" action="login.jsp" class="UserBtn">
               
                <input type="hidden" name="loginCart">
                <input type="submit" value="Login">
            </form>
             <form id="register" method="GET" action="register.jsp" class="UserBtn">
                
                <input type="hidden" name="loginCart">
                <input type="submit" value="register">
            </form>
        </div>

        <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
        <script>
            var x = document.getElementById("status");
            var login = document.getElementById("login");
            var logout = document.getElementById("logout");
            var register = document.getElementById("register");
            var greeting = document.getElementById("greeting");
            if (x.value === "True") {
                logout.style.display = "block";
                greeting.style.display = "block";
                login.style.display = "none";
                register.style.display = "none";
            }
            if (x.value !== "True") {
                login.style.display = "block";
                register.style.display = "block";
                logout.style.display = "none";
                greeting.style.display = "none";
                
            }
        </script>

    </body>
</html>
