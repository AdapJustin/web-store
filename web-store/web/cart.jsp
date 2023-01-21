<%-- 
    Document   : cart
    Created on : 05 12, 21, 4:45:20 PM
    Author     : Justin
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="models.CartObjects"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="landing_style.css">
        <link rel="stylesheet" href="aos.css">
        <link rel="stylesheet" href="cart.css">
        <title>Cart</title>
    </head>
    <body>
        <div class="CartContainer">
            <%
                ArrayList<CartObjects> cart = (ArrayList) session.getAttribute("cart");
                for (int i = 0; i < cart.size(); i++) {
                    out.print("<div class=\"CartList\" data-aos=\"zoom-in\">");
                    out.print("<form method=\"POST\" action=\"CartServlet\">");
                    out.print("<img src=\"" + cart.get(i).getImage() + "\" alt=\"cpu\" width=\"180\" height=\"120\">");

                    out.print("<input type=\"hidden\" name=\"CartID\" value=\"" + cart.get(i).getCartId() + "\">");
                    out.print("<input type=\"hidden\" name=\"CartProduct\" value=\"" + cart.get(i).getProduct() + "\">");
                    out.print("<input type=\"hidden\" name=\"CartType\" value=\"" + cart.get(i).getType() + "\">");
                    out.print("<input type=\"hidden\" name=\"CartStock\" value=\"" + cart.get(i).getStock() + "\">");
                    out.print("<input type=\"hidden\" name=\"CartPrice\" value=\"" + cart.get(i).getPrice() + "\">");

                    out.print("<ul>");
                    out.print("<li>" + cart.get(i).getProduct() + "</li>");
                    out.print("<li>" + cart.get(i).getType() + "</li>");
                    out.print("<li>Qty: " + cart.get(i).getStock() + "</li>");
                    out.print("<li>" + cart.get(i).getPrice() + "</li>");
                    out.print("</ul>");
                    out.print("<input type=\"hidden\" name=\"Action\" value=\"DeleteCartItem\">");
                    out.print("<input type=\"submit\" id=\"removeBtn\" value=\"remove\">");
                    out.print("</form>");
                    out.print("</div>");
                }
            %>
        </div>
        <hr>  
        <div class="CartMenu">
            <div class="CheckOut">
                <span>Total:Php ${total}</span>
                <hr>
                <button>Checkout</button>
            </div>
            <form class="DeleteCartBtn" method="GET" action="CartServlet">
                <input type="hidden"  name="Action" value="DeleteCart">
                <input type="submit" value="Delete Cart">
            </form>

        </div>
        <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script src="aos.js"></script>
        <script>
            AOS.init({duration: 2000,
            });
        </script>     

    </body>
</html>
