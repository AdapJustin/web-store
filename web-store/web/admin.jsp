<%-- 
    Document   : admin.jsp
    Created on : 05 13, 21, 1:44:48 PM
    Author     : Justin
--%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Admin</title>
    </head>
    <body>
        <form method="POST" action="AdminControl">
            <input type="submit" name="submit" value="Logout">
        </form>
        <form method="POST" action="AdminControl">
            <label>Add Product:</label>
            <input type="text" name="Product" placeholder="Product">
            <select name="Type" placeholder="Type">
                <option value="PROCESSOR">PROCESSOR</option>
                <option value="MOTHERBOARD">MOTHERBOARD</option>
                <option value="MEMORY">MEMORY</option>
                <option value="STORAGE">STORAGE</option>
                <option value="GPU">GPU</option>
                <option value="PSU">PSU</option>
                <option value="CASE">CASE</option>
            </select>
            <input type="text" name="Stock" placeholder="Stock">
            <input type="text" name="Price" placeholder="Price">         
            <input type="text" name="Brand" placeholder="Brand">
            <label>Image Link: ${imageLink}</label>
            <input type="submit" name="submit" value="Add Product">
        </form>
             <form method="POST" enctype="multipart/form-data" action="FileUploadHandler">
            <input type="file" name="file2" ><br> 
            <input type="submit" name="submit" value="Upload Image">
        </form>
        <form method="POST" action="AdminControl">
            <input type="text" name="Product" placeholder="Product">
            <input type="submit" name="submit" value="Search Product">

        </form>
        <form method="POST" action="AdminControl">
            <label>Stocks: ${adminProduct}</label>
            <input type="number" name="Stock" placeholder="Product" value="${adminStock}">
            <input type="submit" name="submit" value="Update Stock">
            <label>${adminUpdated}</label>
        </form>
        <form method="POST" action="AdminControl">
            <label>Price: ${adminProduct}</label>
            <input type="number" name="Price" placeholder="Product" value="${adminPrice}">
            <input type="submit" name="submit" value="Update Price">
        </form>
       
        <%@include file="ConnectDB.jsp" %>   
        <%      try {
                ps = con.prepareStatement("SELECT * from INVENTORY");
                rs = ps.executeQuery();
                out.print("<table>");
                out.print("<tr>");
                out.print("<th>" + "PRODUCT" + "</th>");
                out.print("<th>" + "TYPE" + "</th>");
                out.print("<th>" + "BRAND" + "</th>");
                out.print("<th>" + "STOCK" + "</th>");
                out.print("<th>" + "PRICE" + "</th>");
                out.print("<th>" + "IMAGE" + "</th>");
                out.print("</tr>");
                while (rs.next()) {
                    out.print("<tr>");
                    out.print("<th>" + rs.getString("PRODUCT") + "</th>");
                    out.print("<th>" + rs.getString("TYPE") + "</th>");
                    out.print("<th>" + rs.getString("BRAND") + "</th>");
                    out.print("<th>" + rs.getString("STOCK") + "</th>");
                    out.print("<th>Php " + rs.getString("PRICE") + "</th>");
                    out.print("<th>" + rs.getString("IMAGE") + "</th>");
                    
                    out.print("</tr>");
                }
                out.print("</table>");
            } catch (SQLException ex) {
                out.print(ex);
            }
        %>
        <form method="POST" action="AdminControl">
            <input type="submit" name="submit" value="Download Inventory">
        </form>

    </body>
</html>
