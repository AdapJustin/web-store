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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Justin
 */
public class Register extends HttpServlet {

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        
        InputChecker ic = new InputChecker();
        
        String emailReg = request.getParameter("emailReg").trim();
         String passwordReg = request.getParameter("passwordReg").trim();
        //validates email
        /*
        if(!ic.validateEmail(emailReg)){
            sendError(request,response,"Invalid email");
            return;
        }
       
        if(!ic.validatePassword(passwordReg)){
            sendError(request,response, "Password must contain 8-16 characters and has Upper and lower characters and a number");
            return;
        }*/
        String passwordConfirm = request.getParameter("passwordConfirm").trim();
        if(!passwordConfirm.equals(passwordReg)) {
            sendError(request,response,"Password does not match");
           return;
        }
        String firstName = request.getParameter("firstName").trim();
        if(firstName.isEmpty()){
           sendError(request,response,"First name can't be empty");
           return;
        }
        String lastName = request.getParameter("lastName").trim();
        if(lastName.isEmpty()){
            sendError(request,response,"Last name can't be empty");
           return;
        }
        String address = request.getParameter("address").trim();
        if(address.isEmpty()){
            sendError(request,response,"Please enter your address");
           return;
        }
        String city = request.getParameter("city").trim();
        if(city.isEmpty()){
            sendError(request,response,"Please enter your city");
           return;
        }
       //String checkboxSub = request.getParameter("subscribe").trim();
        String subscribe = "Subscribed";
        if(request.getParameter("subscribe") == null)subscribe = "Regular";       
        
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
        try {
            //CHECK IF USERNAME ALREADY EXISTS
            PreparedStatement checkUser = con.prepareStatement("select * from USER_LIST where EMAIL=?");
            checkUser.setString(1, emailReg);
            ResultSet rs = checkUser.executeQuery();
            //forward error messsage user already exists
            if(rs.next()){
                sendError(request,response,"Email already taken");
            }else {
            //add to table and Verify captcha
                if(verify){
                    PreparedStatement ps = con.prepareStatement("INSERT INTO USER_LIST (EMAIL, PASSWORD, FIRSTNAME, LASTNAME, ADDRESS, CITY, ROLE) VALUES (?,?,?,?,?,?,?)");
                    ps.setString(1, emailReg);
                    ps.setString(2, CryptoSecurity.encrypt(passwordReg));
                    ps.setString(3, firstName);
                    ps.setString(4, lastName);
                    ps.setString(5, address);
                    ps.setString(6, city);
                    ps.setString(7, subscribe); 
                    ps.executeUpdate();
                    response.sendRedirect("login.jsp");
                }else{
                    sendError(request,response,"Invalid Captcha");
                }
            
            }
            
           
           
        } catch (SQLException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
public void sendError(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException{
            String emailReg = request.getParameter("emailReg").trim();
            String firstName = request.getParameter("firstName").trim();
            String lastName = request.getParameter("lastName").trim();
            String address = request.getParameter("address").trim();
            String city = request.getParameter("city").trim();
            request.setAttribute("error", errorMessage); 
            request.setAttribute("emailReg", emailReg);
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("address", address);
            request.setAttribute("city", city);
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
            rd.forward(request, response);
}


}
