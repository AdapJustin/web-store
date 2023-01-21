<%@page import="models.CartObjects"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="models.InventoryObject"%>
<%@page import="java.util.*"%>


<!DOCTYPE html>
<html>
    <head>
        <title>HOME</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="stylesheet.css">
        <link rel="stylesheet" href="landing_style.css">
        <link rel="stylesheet" href="aos.css">
    </head>
    <body>
        <div class="container">
            <div class="user"><jsp:include page="header.jsp" /></div>
            <div class="topnav">
               
                <button class="cart" id="cart"><i style="font-size:24px" class="fa">&#xf07a;</i> ${CartItems}</button>
                <form class="shop" action="Home.jsp">
                    <input type="submit" value="Home">
                </form>
                <form class="shop" action="PCBuilder">
                    <input type="hidden" name="Action" value="PCBuild">
                    <input type="hidden" name="pick" value="none">
                    <input type="submit" value="Build-A-PC">
                </form>
                <form class="shop" action="ItemsServlet">
                    <input type="hidden" name="ItemSort" value="All">
                    <input type="submit" value="Shop" id="shopBtn">
                </form>
            </div>
        </div>

        <div class="shopping-cart" style="display: none;">
            <div class="shopping-cart-header">
                <i style="font-size:24px" class="fa">&#xf07a;</i></i><span class="badge"> ${CartItems}</span>
                <div class="shopping-cart-total">
                    <span class="lighter-text">Total:</span>
                    <span class="main-color-text">Php ${total}</span>
                </div>
            </div> <!--end shopping-cart-header -->

            <ul class="shopping-cart-items">
                <%
                    if (session.getAttribute("cart") == null) {

                    } else {
                        ArrayList<CartObjects> cart = (ArrayList) session.getAttribute("cart");
                        for (int i = 0; i < cart.size(); i++) {
                            out.print("<li class=\"clearfix\">");
                            out.print(" <img src=\"" + cart.get(i).getImage() + "\" alt=\"cpu\" width=\"100\" height=\"60\">");
                            out.print("<span class=\"item-name\">" + cart.get(i).getProduct() + "</span>");
                            out.print("<span class=\"item-price\">" + cart.get(i).getPrice() + "</span>");
                            out.print("<span class=\"item-quantity\">Quantity:" + cart.get(i).getStock() + "</span>");
                            out.print("</li>");
                        }
                    }

                %>
            </ul>
            <form class="cartBtn" action="CartServlet">
                <input type="hidden" name="Action" value="GoToCart">
                <input type="submit" value="Go to Cart">
            </form>
        </div> <!--end shopping-cart -->
        <div class="toDisplay">
            <jsp:include page="${display}" />
            
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