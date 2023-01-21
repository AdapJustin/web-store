<%-- 
    Document   : Shop
    Created on : May 16, 2021, 10:56:38 PM
    Author     : Sam Louis AF
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="models.InventoryObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Shop</title>
        <link rel="stylesheet" href="shop.css">
    </head>
    <body>  
        <div class="SortMenu" data-aos="fade-right" >
            <form class="ItemSort" action="ItemsServlet">
                <input type="hidden" name="ItemSort" value="All">
                <input type="submit" value="ALL" >
            </form>
            <form class="ItemSort" action="ItemsServlet">
                <input type="hidden" name="ItemSort" value="Processor">
                <input type="submit" value="PROCESSOR" >
            </form>
            <form class="ItemSort" action="ItemsServlet">
                <input type="hidden" name="ItemSort" value="Motherboard">
                <input type="submit" value="MOTHERBOARD" >
            </form>
            <form class="ItemSort" action="ItemsServlet">
                <input type="hidden" name="ItemSort" value="Memory">
                <input type="submit" value="MEMORY" >
            </form>
            <form class="ItemSort" action="ItemsServlet">
                <input type="hidden" name="ItemSort" value="Storage">
                <input type="submit" value="STORAGE" >
            </form>
            <form class="ItemSort" action="ItemsServlet">
                <input type="hidden" name="ItemSort" value="GPU">
                <input type="submit" value="GPU" >
            </form>
            <form class="ItemSort" action="ItemsServlet">
                <input type="hidden" name="ItemSort" value="PSU">
                <input type="submit" value="PSU" >
            </form>
            <form class="ItemSort"  action="ItemsServlet">
                <input type="hidden" name="ItemSort" value="Case">
                <input type="submit" value="CASE" >
            </form>
        </div>
        <hr>
        <section class="tabcontent" >
            <%
                ArrayList<InventoryObject> items = (ArrayList) session.getAttribute("items");
                for (int i = 0; i < items.size(); i++) {
                    out.print("<div class =\"ShopItem\" data-aos=\"zoom-in\">");
                    out.print(" <img src=\"" + items.get(i).getImage() + "\" alt=\"cpu\" width=\"180\" height=\"120\">");
                    out.print("<Label>" + items.get(i).getBrand() + "</Label>");
                    out.print("<Label>" + items.get(i).getProduct() + "</Label>");
                    out.print("<Label>" + items.get(i).getType() + "</Label>");
                    out.print("<Label>" + "$" + items.get(i).getPrice() + "</Label>");
                    out.print("<Label>" + "Available: " + items.get(i).getStock() + "</Label>");
                    out.print("<form method=\"POST\" action=\"CartServlet\" class =\" ShopItemForm\">");

                    out.print("<input type=\"hidden\" name=\"Email\" value=\"" + session.getAttribute("userEmail") + "\">");
                    out.print("<input type=\"hidden\" name=\"Product\" value=\"" + items.get(i).getProduct() + "\">");
                    out.print("<input type=\"hidden\" name=\"Type\" value=\"" + items.get(i).getType() + "\">");
                    out.print("<input type=\"hidden\" name=\"Price\" value=\"" + items.get(i).getPrice() + "\">");
                    out.print("<input type=\"number\" name=\"Stock\" value=\"1\" min=\"1\" max =\"" + items.get(i).getStock() + "\">");
                    out.print("<input type=\"hidden\" name=\"Image\" value=\"" + items.get(i).getImage() + "\">");
                    out.print("<input type=\"hidden\" name=\"Action\" value=\"AddToCart\">");
                    out.print("<input type=\"Submit\" value=\"Add to Cart\" >");
                    out.print("</form>");
                    out.print("</div>");
                }
            %>
        </section>
        <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.touchswipe/1.6.19/jquery.touchSwipe.min.js" integrity="sha512-YYiD5ZhmJ0GCdJvx6Xe6HzHqHvMpJEPomXwPbsgcpMFPW+mQEeVBU6l9n+2Y+naq+CLbujk91vHyN18q6/RSYw==" crossorigin="anonymous"></script>
        <script src="aos.js"></script>
        <script>
            AOS.init({duration: 2000,
            });
        </script>
    </body>
</html>
