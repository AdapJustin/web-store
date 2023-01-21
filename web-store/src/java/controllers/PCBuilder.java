/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
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
 * @author Godwin Sabigan
 */
public class PCBuilder extends HttpServlet {

    Connection con;

    int total = 0;

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
            response.setContentType("text/html;charset=UTF-8");
            String action = request.getParameter("Action");
            System.out.println(action);
            
            switch (action) {
                case "PCBuild":
                    String type = request.getParameter("pick");
                    issaMain(request, response, type);
                    break;
                case "PCRemover":
                    HttpSession session = request.getSession();
                    
                    String current = request.getParameter("current");
                    Integer total = (Integer) session.getAttribute("totalAmount");
                    String price = request.getParameter("price");
                    
                    int priceSubtract = Integer.parseInt(price);
                    total = total - priceSubtract;
                    session.setAttribute("totalAmount", total);
                    session.removeAttribute(current);
                    
                    session.setAttribute("display", "PcBuild.jsp");
                    response.sendRedirect("index.jsp");
                    
                    break;
                case "PCBuildAddToCart":
                    AddToCart(request, response);
                    break;
                default:
                    break;
                    
            }
        } catch (SQLException ex) {
            Logger.getLogger(PCBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void AddToCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
HttpSession session = request.getSession();
        String status = (String) session.getAttribute("status");
         if ("True".equals(status)){
        String user = (String) session.getAttribute("userEmail");

        if (session.getAttribute("currCPU") != null) {
            createObj((String) session.getAttribute("currCPU"), user);
        }

        if (session.getAttribute("currMobo") != null) {
            createObj((String) session.getAttribute("currMobo"), user);
        }

        if (session.getAttribute("currStorage") != null) {
            createObj((String) session.getAttribute("currStorage"), user);
        }

        if (session.getAttribute("currMemory") != null) {
            createObj((String) session.getAttribute("currMemory"), user);
        }

        if (session.getAttribute("currGPU") != null) {
            createObj((String) session.getAttribute("currGPU"), user);
        }

        if (session.getAttribute("currPSU") != null) {
            createObj((String) session.getAttribute("currPSU"), user);
        }

        if (session.getAttribute("currCase") != null) {
            createObj((String) session.getAttribute("currCase"), user);
        }

        PreparedStatement ps3 = con.prepareStatement("select * from CART where EMAIL=?");
        ps3.setString(1, user);
        ResultSet rs3 = ps3.executeQuery();
        ArrayList<CartObjects> cart = FillCart(rs3);

        int CartItemsCtr = 0;

        int total = 0;

        ps3 = con.prepareStatement("select PRICE from CART where EMAIL=?");
        ps3.setString(1, user);
        rs3 = ps3.executeQuery();
        while (rs3.next()) {
            String temp = rs3.getString("PRICE");
            total += Integer.parseInt(temp);
            CartItemsCtr++;
        }

        session.setAttribute("total", total);
        session.setAttribute("CartItems", CartItemsCtr);

        session.setAttribute("cart", cart);

        session.setAttribute("display", "PcBuild.jsp");
        response.sendRedirect("index.jsp");
         }
         else{
             response.sendRedirect("login.jsp");
         }

    }
    public void createObj(String comp, String user) throws SQLException {

        PreparedStatement ps = con.prepareStatement("SELECT * from INVENTORY where IMAGE =? ");
        ps.setString(1, comp);
        ResultSet rs = ps.executeQuery();
        InventoryObject io = new InventoryObject();
        if (rs.next()) {
            io = new InventoryObject(rs.getString("PRODUCT"), rs.getString("TYPE"), rs.getString("IMAGE"), rs.getString("PRICE"), rs.getString("STOCK"), rs.getString("BRAND"));
        }
        ps = con.prepareStatement("INSERT INTO CART (EMAIL, PRODUCT, TYPE, PRICE, STOCK, IMAGE) VALUES (?,?,?,?,?,?)");
        ps.setString(1, user);
        ps.setString(2, io.getProduct());
        ps.setString(3, io.getType());
        ps.setString(4, io.getPrice());
        ps.setString(5, "1");
        ps.setString(6, io.getImage());
        ps.executeUpdate();

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
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void displaySelected(String component, String attName, HttpSession session, HttpServletRequest request, HttpServletResponse response, RequestDispatcher rd) throws ServletException, IOException {
        if (component == null) {
            response.sendRedirect("PcBuild.jsp");
        }
        try {

            PreparedStatement ps = con.prepareStatement("select * from INVENTORY where PRODUCT=?");
            ps.setString(1, component);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String set = (rs.getString("IMAGE"));
                session.setAttribute(attName, set);

                total = (Integer) session.getAttribute("totalAmount");
                total = total + rs.getInt("PRICE");
                session.setAttribute("totalAmount", total);

                session.setAttribute("display", "PcBuild.jsp");
                response.sendRedirect("index.jsp");
            }

        } catch (SQLException ex) {
            Logger.getLogger(PCBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void issaMain(HttpServletRequest request, HttpServletResponse response, String type)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String destination = "/PcBuild.jsp";
        RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);
        HttpSession session = request.getSession();

        String CPU = request.getParameter("CPU");
        String motherBoard = request.getParameter("MOBO");
        String storage = request.getParameter("STOR");
        String graphicsCard = request.getParameter("GPU");
        String powerSupply = request.getParameter("PSU");
        String chassis = request.getParameter("CASE");
        String memory = request.getParameter("MEMO");

        switch (type) {
            case "CASE":
                displaySelected(chassis, "currCase", session, request, response, rd);
                break;
            case "CPU":
                displaySelected(CPU, "currCPU", session, request, response, rd);
                break;

            case "MOBO":
                displaySelected(motherBoard, "currMobo", session, request, response, rd);
                break;

            case "STORAGE":
                displaySelected(storage, "currStorage", session, request, response, rd);
                break;
            case "GPU":
                displaySelected(graphicsCard, "currGPU", session, request, response, rd);
                break;
            case "PSU":
                displaySelected(powerSupply, "currPSU", session, request, response, rd);
                break;
            case "MEMORY":
                displaySelected(memory, "currMemory", session, request, response, rd);
                break;
            case "none":
                session.setAttribute("display", "PcBuild.jsp");
                response.sendRedirect("index.jsp");
                break;

            default:

                session.setAttribute("display", "PcBuild.jsp");
                response.sendRedirect("index.jsp");
        }

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
