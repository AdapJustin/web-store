package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.CartObjects;
import models.InventoryObject;

public class Login extends HttpServlet {

    private static final long serialVersionUID = -6506682026701304964L;

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

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        response.setContentType("text/html;charset=UTF-8");
        String user = request.getParameter("username").trim();
        String pass = request.getParameter("password").trim();
        String userName = "";
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        System.out.println(gRecaptchaResponse);
        boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
        if (user.isEmpty() && pass.isEmpty()) {
            request.setAttribute("error", "Please Enter a Username and Password");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        } else {
            try {
                PreparedStatement ps = con.prepareStatement("select * from USER_LIST where EMAIL=?");
                ps.setString(1, user);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if (!CryptoSecurity.decrypt(rs.getString("PASSWORD")).trim().equals(pass)) {
                        request.setAttribute("error", "Incorrect Password");
                        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                        rd.forward(request, response);
                    }
                    if (CryptoSecurity.decrypt(rs.getString("PASSWORD")).trim().equals(pass) && !verify) {
                        request.setAttribute("error", "Please Complete the Captcha");
                        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                        rd.forward(request, response);
                    }
                    if (CryptoSecurity.decrypt(rs.getString("PASSWORD")).trim().equals(pass) && verify) {
                        if (rs.getString("ROLE").equals("Admin")) {
                            HttpSession session = request.getSession();
                            session.setAttribute("userEmail", user);
                            response.sendRedirect("admin.jsp");
                        } else {
                            PreparedStatement ps2 = con.prepareStatement("select FIRSTNAME from USER_LIST where EMAIL=?");
                            ps2.setString(1, user);
                            ResultSet rs2 = ps2.executeQuery();
                            if (rs2.next()) {
                                userName = rs.getString("FIRSTNAME");
                            }
                            HttpSession session = request.getSession();
                            //fill shop
                            PreparedStatement ps3 = con.prepareStatement("select * from INVENTORY");
                            ResultSet rs3 = ps3.executeQuery();
                            String itemSort = "All";
                            ArrayList<InventoryObject> items = FillInventory(rs3, itemSort);

                            //fill cart
                            ps3 = con.prepareStatement("select * from CART where EMAIL=?");
                            ps3.setString(1, user);
                            rs3 = ps3.executeQuery();
                            ArrayList<CartObjects> cart = FillCart(rs3);

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

                            session.setAttribute("ItemSort", itemSort);
                            session.setAttribute("total", total);
                            session.setAttribute("CartItems", CartItemsCtr);

                            session.setAttribute("cart", cart);
                            session.setAttribute("items", items);
               

                            session.setAttribute("status", "True");
                            session.setAttribute("userEmail", user);
                            session.setAttribute("userName", userName);
                            session.setAttribute("display", "Shop.jsp");
                            response.sendRedirect("index.jsp");
                        }

                    }
                } else {
                    if (pass.isEmpty()) {
                        request.setAttribute("error", "Username Does Not Exist and Password is Blank");
                        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                        rd.forward(request, response);

                    } else {
                        request.setAttribute("error", "User Does Not Exist");
                        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                        rd.forward(request, response);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("login.jsp");
            }
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
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList FillInventory(ResultSet rs, String ItemSort) {
        try {
            System.out.println("Item sort value is " + ItemSort);
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

    public ArrayList displayItems() {
        try {
            PreparedStatement ps = con.prepareStatement("select * from INVENTORY");
            ResultSet rs = ps.executeQuery();
            ArrayList<InventoryObject> items = new ArrayList<InventoryObject>();
            while (rs.next()) {
                InventoryObject obj = new InventoryObject(rs.getString("PRODUCT"), rs.getString("TYPE"), rs.getString("IMAGE"),
                        rs.getString("PRICE"), rs.getString("STOCK"), rs.getString("BRAND"));
                items.add(obj);
            }
            return items;
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateTable() {
        try {
            PreparedStatement ps = con.prepareStatement("select * from USER_LIST");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String EncryptedPass = CryptoSecurity.encrypt(rs.getString("PASSWORD"));
                ps = con.prepareStatement("UPDATE USER_LIST SET PASSWORD = ? WHERE EMAIL = ?");
                ps.setString(1, EncryptedPass);
                ps.setString(2, rs.getString("EMAIL"));
                System.out.println(rs.getString("EMAIL") + "------" + EncryptedPass);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void DecryptTable() {
        try {
            PreparedStatement ps = con.prepareStatement("select * from USER_LIST");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String DecryptedPass = CryptoSecurity.decrypt(rs.getString("PASSWORD"));
                System.out.println(rs.getString("EMAIL") + "------" + DecryptedPass);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
