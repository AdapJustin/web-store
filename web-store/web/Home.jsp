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
        <div class="container" >
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
        <div class="toDisplay" data-aos="zoom-in-down">
            <div class ="buildNow">
                <h2>Build your PC</h2>
                <p>Planning on building your PC? </p>
                <p>Try our PC Builder and select from a variety of different components. </p>
                <p>Add it to your cart and make it yours today</p>
                <form action="PCBuilder">
                    <input type="hidden" name="Action" value="PCBuild">
                    <input type="hidden" name="pick" value="none">
                    <input type="submit" value="Build Now">
                </form>
            </div>
            <h2 style="padding:10px;">Recommended products</h2>
            <div class ="gallery">
                <div data-aos="flip-up" class="card">
                    <div class="imgBox">
                        <img src ="img/1.png">
                    </div>
                    <div data-aos="flip-up" class="recommended">
                        <h2>MSI 1660 Super</h2>
                        <p>Boost Clock / Memory Speed</p>
                        <p>1830 MHz / 14 Gbps</p>
                        <p>6GB GDDR6</p>
                        <p>DisplayPort x 3(v1.4a)</p>
                        <p>HDMI x 1(Supports 4K@60Hz as specified in HDMI 2.0b)</p>
                    </div>
                </div>
                <div data-aos="flip-up" class="card">
                    <div class="imgBox">
                        <img src ="img/2.png">
                    </div>
                    <div data-aos="flip-up" class="recommended">
                        <h2>INWIN 309</h2>
                        <p>IW-309-Black</p>
                        <p>Mid Tower</p>
                        <p>SECC, Tempered Glass, ABS</p>
                        <p>12" x 11" ATX, Micro-ATX, Mini-ITX</p>
                        <p>PCI-E x 7</p>
                    </div>
                </div>
                <div data-aos="flip-up" class="card">
                    <div class="imgBox">
                        <img src ="img/3.png">
                    </div>
                    <div data-aos="flip-up" class="recommended">
                        <h2>Intel Core i9 9900k</h2>
                        <p># of Cores: 8</p>
                        <p># of Threads: 16</p>
                        <p>Processor Base Frequency: 3.60 GHz</p>
                        <p>Max Turbo Frequency: 5.00 GHz</p>
                    </div>
                </div>
            </div>
            <div class="shopNow">
                <h2>Picking some parts?</h2>
                <p>Are you looking for PC parts? </p>
                <p>Browse through our selection of components and get one for your set-up now!</p>
                <form action="ItemsServlet">
                    <input type="hidden" name="ItemSort" value="All">
                    <input type="submit" value="Shop Now" id="shopBtn">
                </form>
            </div>

            <div class ="aboutUs">
                <p> All Rights Reserved</p>
            </div>

            <footer>

            </footer>
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