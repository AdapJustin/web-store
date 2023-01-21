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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.CartObjects;
import models.InventoryObject;

/**
 *
 * @author Sam Louis AF
 */
public class CartServlet extends HttpServlet {

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
        String action = request.getParameter("Action");
        System.out.println(action);
        switch (action) {
            case "AddToCart":
                AddToCart(request, response);
                break;
            case "DeleteCart":

                DeleteCart(request, response);
                break;
            case "GoToCart":
                DisplayCart(request, response);
                break;
            case "DeleteCartItem":
                DeleteCartItem(request, response);
                break;
            default:
                break;

        }

    }

    public void AddToCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String status = (String) session.getAttribute("status");
        String email = request.getParameter("Email");
        String product = request.getParameter("Product");
        String type = request.getParameter("Type");
        String price = request.getParameter("Price");
        String stock = request.getParameter("Stock");
        String image = request.getParameter("Image");
        
        int stockInt = Integer.parseInt(stock);
        int priceInt = Integer.parseInt(price);
        
        String priceStock = String.valueOf(stockInt * priceInt);
        
        String itemSort = (String) session.getAttribute("ItemSort");
        System.out.println("AddtoCart Item Sort is = " + itemSort);
        if ("True".equals(status)) {

            try {
                PreparedStatement ps = con.prepareStatement("INSERT INTO CART (EMAIL, PRODUCT, TYPE, PRICE, STOCK, IMAGE) VALUES (?,?,?,?,?,?)");
                ps.setString(1, email);
                ps.setString(2, product);
                ps.setString(3, type);
                ps.setString(4, priceStock);
                ps.setString(5, stock);
                ps.setString(6, image);
                ps.executeUpdate();
                ps = con.prepareStatement("select * from INVENTORY");
                ResultSet rs = ps.executeQuery();
                ArrayList<InventoryObject> items = FillInventory(rs,itemSort);

                ps = con.prepareStatement("select * from CART where EMAIL=?");
                ps.setString(1, email);
                rs = ps.executeQuery();
                ArrayList<CartObjects> cart = FillCart(rs);
                
                int CartItemsCtr = 0;
   
                int total = 0;

                ps = con.prepareStatement("select PRICE from CART where EMAIL=?");
                ps.setString(1, email);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String temp = rs.getString("PRICE");
                    total += Integer.parseInt(temp);
                    CartItemsCtr++;
                }
                
                session.setAttribute("ItemSort", itemSort);
                session.setAttribute("total", total);
                
                session.setAttribute("CartItems", CartItemsCtr);
                session.setAttribute("cart", cart);
                session.setAttribute("items", items);
                session.setAttribute("display", "Shop.jsp");
                response.sendRedirect("index.jsp");
            } catch (SQLException ex) {
                Logger.getLogger(CartServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            response.sendRedirect("login.jsp");
        }

    }
       public void DeleteCartItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("userEmail");
        String CartID = request.getParameter("CartID");

        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM CART WHERE CARTID=?");
            ps.setString(1, CartID);
            ps.executeUpdate();
            
            ps = con.prepareStatement("select * from CART where EMAIL=?");
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            ArrayList<CartObjects> cart = FillCart(rs);
            int CartItemsCtr = 0;
            int total = 0;

            ps = con.prepareStatement("select PRICE from CART where EMAIL=?");
            ps.setString(1, user);
            rs = ps.executeQuery();
            while (rs.next()) {
                String temp = rs.getString("PRICE");
                total += Integer.parseInt(temp);
                CartItemsCtr++;
            }
            
            session.setAttribute("CartItems", CartItemsCtr);
            session.setAttribute("total", total);
            session.setAttribute("cart", cart);
            session.setAttribute("display", "cart.jsp");
            //RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            //rd.forward(request, response);
            response.sendRedirect("index.jsp");
        } catch (SQLException ex) {
            Logger.getLogger(CartServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void DeleteCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("userEmail");
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM CART WHERE EMAIL=?");
            ps.setString(1, user);
            ps.executeUpdate();
            ps = con.prepareStatement("select * from CART where EMAIL=?");
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            ArrayList<CartObjects> cart = FillCart(rs);
            int CartItemsCtr = 0;
            int total = 0;

            ps = con.prepareStatement("select PRICE from CART where EMAIL=?");
            ps.setString(1, user);
            rs = ps.executeQuery();
            while (rs.next()) {
                String temp = rs.getString("PRICE");
                total += Integer.parseInt(temp);
                CartItemsCtr++;
            }
            
            session.setAttribute("CartItems", CartItemsCtr);
            session.setAttribute("total", total);
            session.setAttribute("cart", cart);
            session.setAttribute("display", "cart.jsp");
            //RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            //rd.forward(request, response);
            response.sendRedirect("index.jsp");
        } catch (SQLException ex) {
            Logger.getLogger(CartServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void DisplayCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("userEmail");
        try {
            PreparedStatement query = con.prepareStatement("select count(*) from CART where EMAIL=?");
            query.setString(1, user);
            ResultSet rs2 = query.executeQuery();
            rs2.next();
            int count = rs2.getInt(1);
            session.setAttribute("ctr", String.valueOf(count));
            PreparedStatement ps = con.prepareStatement("select * from CART where EMAIL=?");
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            ArrayList<CartObjects> cart = FillCart(rs);
            int CartItemsCtr = 0;
            int total = 0;

            ps = con.prepareStatement("select PRICE from CART where EMAIL=?");
            ps.setString(1, user);
            rs = ps.executeQuery();
            while (rs.next()) {
                String temp = rs.getString("PRICE");
                total += Integer.parseInt(temp);
                CartItemsCtr++;
            }
            session.setAttribute("ItemSort", "All");
            session.setAttribute("CartItems", CartItemsCtr);
            session.setAttribute("total", total);
            
            session.setAttribute("cart", cart);
        } catch (SQLException ex) {
            Logger.getLogger(CartServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        int total = 0;
        try {
            PreparedStatement ps = con.prepareStatement("select PRICE from CART where EMAIL=?");
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String temp = rs.getString("PRICE");
                total += Integer.parseInt(temp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        session.setAttribute("total", total);
        session.setAttribute("display", "cart.jsp");
        //RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        //rd.forward(request, response);
        response.sendRedirect("index.jsp");

    }

    public ArrayList FillCart(ResultSet rs) {
        try {
            ArrayList<CartObjects> cart = new ArrayList<CartObjects>();
            while (rs.next()) {
                CartObjects obj = new CartObjects(rs.getString("EMAIL"), rs.getString("PRODUCT"), rs.getString("TYPE"), rs.getString("PRICE"), rs.getString("STOCK")
                    , rs.getString("IMAGE"), rs.getString("CartID"));
                cart.add(obj);
            }
            return cart;
        } catch (SQLException ex) {
            Logger.getLogger(CartServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList FillInventory( ResultSet rs, String ItemSort) {
        try {
            ArrayList<InventoryObject> items = new ArrayList<InventoryObject>();
            while (rs.next()) {
                if (rs.getString("TYPE").equalsIgnoreCase(ItemSort)) {
                    InventoryObject obj = new InventoryObject(rs.getString("PRODUCT"), rs.getString("TYPE"), rs.getString("IMAGE"),
                            rs.getString("PRICE"), rs.getString("STOCK"), rs.getString("BRAND"));
                    items.add(obj);
                } else {
                    InventoryObject obj = new InventoryObject(rs.getString("PRODUCT"), rs.getString("TYPE"), rs.getString("IMAGE"),
                            rs.getString("PRICE"), rs.getString("STOCK"), rs.getString("BRAND"));
                    items.add(obj);
                }

            }
            return items;
        } catch (SQLException ex) {
            Logger.getLogger(ItemsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
