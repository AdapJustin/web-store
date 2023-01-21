/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import models.CartObjects;
import models.InventoryObject;

/**
 *
 * @author Sam Louis AF
 */
public class ItemsServlet extends HttpServlet {

    Connection con;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = getServletContext();
        try {
            Class.forName(context.getInitParameter("jdbcClassName"));
            String username = context.getInitParameter("dbUserName");
            String password = context.getInitParameter("dbPassword");
            String url = context.getInitParameter("jdbcDriverURL");
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException sqle) {
            System.out.println("SQLException error occured - "
                    + sqle.getMessage());
        } catch (ClassNotFoundException nfe) {
            System.out.println("ClassNotFoundException error occured - "
                    + nfe.getMessage());
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String user = (String) session.getAttribute("userEmail");
            String itemSort = request.getParameter("ItemSort");
            if (itemSort == null) {
                itemSort = "All";
            }
            System.out.println("Item servlet itemsort = " + itemSort);
            PreparedStatement ps = con.prepareStatement("select * from INVENTORY");
            ResultSet rs = ps.executeQuery();
            
            ArrayList<InventoryObject> items = FillInventory(rs, itemSort);

            ps = con.prepareStatement("select * from CART where EMAIL=?");
            ps.setString(1, user);
            rs = ps.executeQuery();
            ArrayList<CartObjects> cart = FillCart(rs);
            
            session.setAttribute("ItemSort", itemSort);
            session.setAttribute("cart", cart);
            session.setAttribute("items", items);
            session.setAttribute("display", "Shop.jsp");

            response.sendRedirect("index.jsp");
            
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList FillCart(ResultSet rs) {
        try {
            ArrayList<CartObjects> cart = new ArrayList<CartObjects>();
            while (rs.next()) {
                CartObjects obj = new CartObjects(rs.getString("EMAIL"), rs.getString("PRODUCT"), rs.getString("TYPE"), rs.getString("PRICE"), rs.getString("STOCK"),
                         rs.getString("IMAGE"), rs.getString("CartID"));
                cart.add(obj);
            }
            return cart;
        } catch (SQLException ex) {
            Logger.getLogger(ItemsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList FillInventory(ResultSet rs, String ItemSort) {
        try {
            ArrayList<InventoryObject> items = new ArrayList<InventoryObject>();
            while (rs.next()) {
                if (rs.getString("TYPE").equalsIgnoreCase(ItemSort)) {
                    InventoryObject obj = new InventoryObject(rs.getString("PRODUCT"), rs.getString("TYPE"), rs.getString("IMAGE"),
                            rs.getString("PRICE"), rs.getString("STOCK"), rs.getString("BRAND"));
                    items.add(obj);
                } else if(ItemSort.equalsIgnoreCase("All")){
                    InventoryObject obj = new InventoryObject(rs.getString("PRODUCT"), rs.getString("TYPE"), rs.getString("IMAGE"),
                            rs.getString("PRICE"), rs.getString("STOCK"), rs.getString("BRAND"));
                    items.add(obj);
                }else{
                    continue;
                }

            }
            return items;
        } catch (SQLException ex) {
            Logger.getLogger(ItemsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
