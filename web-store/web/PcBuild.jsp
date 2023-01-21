<%-- 
    Document   : PcBuild
    Created on : 05 17, 21, 3:59:03 PM
    Author     : Godwin Sabigan
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
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
        <link rel="stylesheet" href="PCBstyle.css">
        <title>PCBuilder</title>
    </head>
    <body>

        <%@include file="ConnectDB.jsp" %>

        <% if (session.getAttribute("totalAmount") == null) {
                session.setAttribute("totalAmount", 0);
            } %>
        <div class="builder">


            <div class ="Box" data-aos="zoom-in">
                <!--CPU CONTENTS HERE-->
                <p>CPU</p>

                <!--DISPLAY SELECTED HERE-->
                <div class ="Current">
                    <img src="${currCPU}" />
                    <%
                        image = (String) session.getAttribute("currCPU");
                        ps = con.prepareStatement("SELECT * from INVENTORY where IMAGE =? ");
                        ps.setString(1, image);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                    %>
                    <p><%=rs.getString("PRODUCT")%></p>
                    <p>Php <%=rs.getString("PRICE")%></p>
                    <%
                        }
                    %>
                </div>
                <!--OPTION TO REMOVE SELECTED COMPONENT-->            
                <%
                    if (session.getAttribute("currCPU") == null) {
                %> 
                <button onclick="myModal('CPUmodal')">Pick a CPU</button>

                <%
                } else {
                %>
                <div class="remove">
                    <form method="GET" action="PCBuilder">
                        <input type="hidden" name="Action" value="PCRemover">
                        <input type="hidden" name="current" value="currCPU">
                        <input type="hidden" name="price" value="<%=rs.getString("PRICE")%>">
                        <input type="submit" value="Remove">
                    </form>
                </div> <%
                    }
                %>
            </div> 
            <!--OPENS MODAL-->


            <!-- Modal content -->
            <div id="CPUmodal" class="modal">
                <div class="modal-content">
                    <span class="close">&times;</span>
                    <!--LOADS CPU FROM INVENTORY-->
                    <%
                        ps = con.prepareStatement("SELECT * from INVENTORY where TYPE = 'PROCESSOR'");
                        rs = ps.executeQuery();
                    %>
                    <form action="PCBuilder" class="ModalBuild">
                        <%
                            while (rs.next()) {

                        %>
                        <input type="radio" name="CPU" value="<%=rs.getString("PRODUCT")%>">
                        <img src="<%=rs.getString("IMAGE")%>" />
                        <p><%=rs.getString("PRODUCT")%></p>
                        <p>Php <%=rs.getString("PRICE")%></p>
                        <p>Available:<%=rs.getString("STOCK")%></p>
                        <%}%>
                        <input type="hidden" name="Action" value="PCBuild">
                        <input type="hidden" name="pick" value="CPU">
                        <input type="submit" value="Submit"> 
                    </form>

                </div>

            </div> 


            <div class ="Box" data-aos="zoom-in">
                <!--MOBO CONTENTS HERE-->
                <p> MotherBoard</p>

                <!--DISPLAY SELECTED HERE-->
                <div class ="Current">
                    <img src="${currMobo}" />
                    <%
                        image = (String) session.getAttribute("currMobo");
                        ps = con.prepareStatement("SELECT * from INVENTORY where IMAGE =? ");
                        ps.setString(1, image);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                    %><p><%=rs.getString("PRODUCT")%></p>
                    <p>Php <%=rs.getString("PRICE")%></p>
                    <%
                        }
                    %>
                </div>
                <%
                    if (session.getAttribute("currMobo") == null) {
                %> <button onclick="myModal('MOBOmodal')">Pick a MotherBoard</button>

                <%
                } else {
                %>
                <div class="remove">
                    <form method="GET" action="PCBuilder">
                        <input type="hidden" name="Action" value="PCRemover">
                        <input type="hidden" name="current" value="currMobo">
                        <input type="hidden" name="price" value="<%=rs.getString("PRICE")%>">
                        <input type="submit" value="Remove">
                    </form>
                </div> <%
                    }
                %>
            </div> 
            <!-- Modal content -->
            <div id="MOBOmodal" class="modal">


                <div class="modal-content">
                    <span class="close">&times;</span>


                    <%
                        ps = con.prepareStatement("SELECT * from INVENTORY where TYPE = 'MOTHERBOARD'");
                        rs = ps.executeQuery();
                    %>
                    <form action="PCBuilder" class="ModalBuild">
                        <%
                            while (rs.next()) {

                        %>


                        <input type="radio" name="MOBO" value="<%=rs.getString("PRODUCT")%>">
                        <img  src="<%=rs.getString("IMAGE")%>" />
                        <p><%=rs.getString("PRODUCT")%></p>
                        <p>Php <%=rs.getString("PRICE")%></p>
                        <p>Stocks:<%=rs.getString("STOCK")%></p>


                        <%}%>
                        <input type="hidden" name="Action" value="PCBuild">
                        <input type="hidden" name="pick" value="MOBO">
                        <input type="submit" value="Submit">
                    </form>
                </div>

            </div> 


            <div class ="Box" data-aos="zoom-in">
                <!--MEMORY CONTENTS HERE-->
                <p>Memory</p>

                <!--DISPLAY SELECTED HERE-->
                <div class ="Current">
                    <img src="${currMemory}" />
                    <%
                        image = (String) session.getAttribute("currMemory");
                        ps = con.prepareStatement("SELECT * from INVENTORY where IMAGE =? ");
                        ps.setString(1, image);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                    %><p><%=rs.getString("PRODUCT")%></p>
                    <p>Php <%=rs.getString("PRICE")%></p>
                    <%
                        }
                    %>
                </div>

                <%
                    if (session.getAttribute("currMemory") == null) {
                %>  <button onclick="myModal('MEMmodal')">Pick a Memory</button>

                <%
                } else {
                %>
                <div class="remove">
                    <form method="GET" action="PCBuilder">
                        <input type="hidden" name="Action" value="PCRemover">
                        <input type="hidden" name="current" value="currMemory">
                        <input type="hidden" name="price" value="<%=rs.getString("PRICE")%>">
                        <input type="submit" value="Remove">
                    </form>
                </div> <%
                    }
                %> 
            </div> 
            <!-- Modal content -->
            <div id="MEMmodal" class="modal">


                <div class="modal-content">
                    <span class="close">&times;</span>


                    <%
                        ps = con.prepareStatement("SELECT * from INVENTORY where TYPE = 'MEMORY'");

                        rs = ps.executeQuery();
                    %>

                    <form action="PCBuilder" class="ModalBuild">
                        <%
                            while (rs.next()) {
                        %>
                        <input type="radio" name="MEMO" value="<%=rs.getString("PRODUCT")%>">
                        <img  src="<%=rs.getString("IMAGE")%>" />
                        <p><%=rs.getString("PRODUCT")%></p>
                        <p>Php <%=rs.getString("PRICE")%></p>
                        <p>Stocks:<%=rs.getString("STOCK")%></p>
                        <%}%>
                        <input type="hidden" name="Action" value="PCBuild">
                        <input type="hidden" name="pick" value="MEMORY">
                        <input type="submit" value="Submit">  
                    </form>
                </div>
            </div> 



            <div class ="Box" data-aos="zoom-in">
                <!--GPU CONTENTS HERE-->             
                <p>GPU</p>

                <!--DISPLAY SELECTED HERE-->
                <div class ="Current">
                    <img src="${currGPU}" />
                    <%
                        image = (String) session.getAttribute("currGPU");
                        ps = con.prepareStatement("SELECT * from INVENTORY where IMAGE =? ");
                        ps.setString(1, image);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                    %><p><%=rs.getString("PRODUCT")%></p>
                    <p>Php <%=rs.getString("PRICE")%></p>
                    <%
                        }
                    %>
                </div>
                <%
                    if (session.getAttribute("currGPU") == null) {
                %> <button onclick="myModal('GPUmodal')">Pick a GPU</button>

                <%
                } else {
                %>
                <div class="remove">
                    <form method="GET" action="PCBuilder">
                        <input type="hidden" name="Action" value="PCRemover">
                        <input type="hidden" name="current" value="currGPU">
                        <input type="hidden" name="price" value="<%=rs.getString("PRICE")%>">
                        <input type="submit" value="Remove">
                    </form>
                </div> <%
                    }
                %>      
            </div>
            <div id="GPUmodal" class="modal">

                <div class="modal-content">
                    <span class="close">&times;</span>

                    <%
                        ps = con.prepareStatement("SELECT * from INVENTORY where TYPE ='GPU'");
                        rs = ps.executeQuery(); %>
                    <form action="PCBuilder" class="ModalBuild">
                        <%
                            while (rs.next()) {
                        %>             
                        <input type="radio" name="GPU" value="<%=rs.getString("PRODUCT")%>">
                        <img src="<%=rs.getString("IMAGE")%>" />
                        <p><%=rs.getString("PRODUCT")%></p>
                        <p>Php <%=rs.getString("PRICE")%></p>
                        <p>Stocks:<%=rs.getString("STOCK")%></p>
                        <%}%>
                        <input type="hidden" name="Action" value="PCBuild">
                        <input type="hidden" name="pick" value="GPU">
                        <input type="submit" value="Submit">  </form>
                </div>
            </div> 


            <div class ="Box" data-aos="zoom-in">
                <!--STORAGE CONTENTS HERE-->
                <p>Storage</p>

                <!--DISPLAY SELECTED HERE-->
                <div class ="Current">
                    <img src="${currStorage}" />
                    <%
                        image = (String) session.getAttribute("currStorage");
                        ps = con.prepareStatement("SELECT * from INVENTORY where IMAGE =? ");
                        ps.setString(1, image);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                    %><p><%=rs.getString("PRODUCT")%></p>
                    <p>Php <%=rs.getString("PRICE")%></p>
                    <%
                        }
                    %>
                </div>


                <%
                    if (session.getAttribute("currStorage") == null) {
                %> <button onclick="myModal('STORAGEmodal')">Pick a Storage</button>

                <%
                } else {
                %>
                <div class="remove">
                    <form method="GET" action="PCBuilder">
                        <input type="hidden" name="Action" value="PCRemover">
                        <input type="hidden" name="current" value="currStorage">
                        <input type="hidden" name="price" value="<%=rs.getString("PRICE")%>">
                        <input type="submit" value="Remove">
                    </form>
                </div> <%
                    }
                %>   
            </div> 
            <!-- Modal content -->
            <div id="STORAGEmodal" class="modal">


                <div class="modal-content">
                    <span class="close">&times;</span>


                    <%
                        ps = con.prepareStatement("SELECT * from INVENTORY where TYPE = 'STORAGE'");
                        rs = ps.executeQuery(); %>
                    <form action="PCBuilder" class="ModalBuild">
                        <%
                            while (rs.next()) {

                        %>
                        <input type="radio" name="STOR" value="<%=rs.getString("PRODUCT")%>">
                        <img  src="<%=rs.getString("IMAGE")%>" />
                        <p><%=rs.getString("PRODUCT")%></p>
                        <p>Php <%=rs.getString("PRICE")%></p>
                        <p>Stocks:<%=rs.getString("STOCK")%></p>
                        <%}%>
                        <input type="hidden" name="Action" value="PCBuild">
                        <input type="hidden" name="pick" value="STORAGE">
                        <input type="submit" value="Submit"> 
                    </form>
                </div>

            </div> 




            <div class ="Box" data-aos="zoom-in">
                <!--PSU CONTENTS HERE-->
                <p>PSU</p>

                <!--DISPLAY SELECTED HERE-->
                <div class ="Current">
                    <img src="${currPSU}" />
                    <%
                        image = (String) session.getAttribute("currPSU");
                        ps = con.prepareStatement("SELECT * from INVENTORY where IMAGE =? ");
                        ps.setString(1, image);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                    %><p><%=rs.getString("PRODUCT")%></p>
                    <p>Php <%=rs.getString("PRICE")%></p>
                    <%
                        }
                    %>
                </div>

                <%
                    if (session.getAttribute("currPSU") == null) {
                %> <button onclick="myModal('PSUmodal')">Pick a PSU</button>

                <%
                } else {
                %>
                <div class="remove">
                    <form method="GET" action="PCBuilder">
                        <input type="hidden" name="Action" value="PCRemover">
                        <input type="hidden" name="current" value="currPSU">
                        <input type="hidden" name="price" value="<%=rs.getString("PRICE")%>">
                        <input type="submit" value="Remove">
                    </form>
                </div> <%
                    }
                %>  
            </div>
            <!-- Modal content -->
            <div id="PSUmodal" class="modal">

                <div class="modal-content">
                    <span class="close">&times;</span>
                    <%
                        ps = con.prepareStatement("SELECT * from INVENTORY where TYPE = 'PSU'");
                        rs = ps.executeQuery(); %>
                    <form action="PCBuilder" class="ModalBuild">
                        <%
                            while (rs.next()) {

                        %>
                        <input type="radio" name="PSU" value="<%=rs.getString("PRODUCT")%>">
                        <img src="<%=rs.getString("IMAGE")%>" />
                        <p><%=rs.getString("PRODUCT")%></p>
                        <p>Php <%=rs.getString("PRICE")%></p>
                        <p>Stocks:<%=rs.getString("STOCK")%></p>
                        <%}%>
                        <input type="hidden" name="Action" value="PCBuild">
                        <input type="hidden" name="pick" value="PSU">
                        <input type="submit" value="Submit"> 
                    </form>
                </div>
            </div> 


            <div class ="Box" data-aos="zoom-in">
                <!--CASE CONTENTS HERE-->
                <p>Case</p>

                <!--DISPLAY SELECTED HERE-->
                <div class ="Current">
                    <img src="${currCase}" />
                    <%
                        image = (String) session.getAttribute("currCase");
                        ps = con.prepareStatement("SELECT * from INVENTORY where IMAGE =? ");
                        ps.setString(1, image);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                    %><p><%=rs.getString("PRODUCT")%></p>
                    <p>Php <%=rs.getString("PRICE")%></p>
                    <%
                        }
                    %>
                </div>

                <%
                    if (session.getAttribute("currCase") == null) {
                %> <button onclick="myModal('CASEmodal')">Pick a Case</button>

                <%
                } else {
                %>
                <div class="remove">
                    <form method="GET" action="PCBuilder">
                        <input type="hidden" name="Action" value="PCRemover">
                        <input type="hidden" name="current" value="currCase">
                        <input type="hidden" name="price" value="<%=rs.getString("PRICE")%>">
                        <input type="submit" value="Remove">
                    </form>
                </div> <%
                    }
                %>   
            </div>  
            <!-- Modal content -->
            <div id="CASEmodal" class="modal">
                <div class="modal-content">
                    <span class="close">&times;</span>

                    <%
                        ps = con.prepareStatement("SELECT * from INVENTORY where TYPE = 'CASE'");

                        rs = ps.executeQuery();
                    %>
                    <form action="PCBuilder" class="ModalBuild">
                        <%
                            while (rs.next()) {
                        %>
                        <input type="radio" name="CASE" value="<%=rs.getString("PRODUCT")%>">
                        <img src="<%=rs.getString("IMAGE")%>" />
                        <p><%=rs.getString("PRODUCT")%></p>
                        <p>Php <%=rs.getString("PRICE")%></p>
                        <p>Stocks:<%=rs.getString("STOCK")%></p>
                        <%}%>
                        <input type="hidden" name="Action" value="PCBuild">
                        <input type="hidden" name="pick" value="CASE">
                        <input type="submit" value="Submit">  
                    </form>
                </div>
            </div> 


        </div>
        <hr>
        <div class="TotalBox" >
            <span>Total: Php ${totalAmount}</span>  
            <hr>
            <form class="addtocart"  action="PCBuilder">
                <input type="hidden" name="Action" value="PCBuildAddToCart">
                <input type="submit" value="Add to Cart" >
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
            function myModal(data) {
                var modal = document.getElementById(data);
                // Get the button that opens the modal
                // Get the <span> element that closes the modal
                var span = document.getElementsByClassName("close")[0];
                var span1 = document.getElementsByClassName("close")[1];
                var span2 = document.getElementsByClassName("close")[2];
                var span3 = document.getElementsByClassName("close")[3];
                var span4 = document.getElementsByClassName("close")[4];
                var span5 = document.getElementsByClassName("close")[5];
                var span6 = document.getElementsByClassName("close")[6];
                
                // When the user clicks the button, open the modal 
                modal.style.display = "block";
                
                // When the user clicks on <span> (x), close the modal
                span.onclick = function () {
                    modal.style.display = "none";
                };
                span1.onclick = function () {
                    modal.style.display = "none";
                };
                span2.onclick = function () {
                    modal.style.display = "none";
                };
                span3.onclick = function () {
                    modal.style.display = "none";
                };
                span4.onclick = function () {
                    modal.style.display = "none";
                };
                span5.onclick = function () {
                    modal.style.display = "none";
                };
                span6.onclick = function () {
                    modal.style.display = "none";
                };
                // When the user clicks anywhere outside of the modal, close it
                window.onclick = function (event) {
                    if (event.target === modal) {
                        modal.style.display = "none";
                    }
                    ;
                };
            }

        </script>
</html>
