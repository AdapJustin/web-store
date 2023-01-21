/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Justin
 */
public class AdminControl extends HttpServlet {

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
        String submit = "";
        if (request.getParameter("submit").trim() != null) {
            submit = request.getParameter("submit").trim();
        }     
        switch (submit) {
            case "Search Product":
                searchProduct(request, response);
                break;
            case "Update Stock":
                updateStock(request, response);
                break;
            case "Update Price":
                updatePrice(request, response);
                break;
            case "Add Product":
                addProduct(request, response);
                break;
            case "Logout":
                logOut(request, response);
                break;
            case "Download Inventory":               
                try {
                    downloadPdf(request, response);
                } catch (DocumentException ex) {
                    Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
                }break; 
            default:
                response.sendRedirect("admin.jsp");
        }   
    }

    public void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession();
            String product = request.getParameter("Product").trim();
            String type = request.getParameter("Type").trim();
            String stock = request.getParameter("Stock").trim();
            String price = request.getParameter("Price").trim();
            String image = (String) session.getAttribute("imageLink");
            String brand = request.getParameter("Brand").trim();
            
            PreparedStatement ps = con.prepareStatement("INSERT INTO INVENTORY (PRODUCT, TYPE, PRICE, STOCK, IMAGE, BRAND) VALUES (?,?,?,?,?,?)");
            ps.setString(1, product);
            ps.setString(2, type);
            ps.setString(3, price);
            ps.setString(4, stock);
            ps.setString(5, "img/" + image);
            ps.setString(6, brand);
            ps.executeUpdate();
            response.sendRedirect("admin.jsp");
        } catch (SQLException ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void searchProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession();
            String product = request.getParameter("Product").trim();
            PreparedStatement ps;
            ps = con.prepareStatement("Select * from INVENTORY where PRODUCT=?");
            ps.setString(1, product);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                session.setAttribute("adminProduct", rs.getString("PRODUCT"));
                session.setAttribute("adminStock", rs.getString("STOCK"));
                session.setAttribute("adminPrice", rs.getString("PRICE"));

            } else {

                session.setAttribute("adminProduct", "");
                session.setAttribute("adminStock", "");
                session.setAttribute("adminPrice", "");
            }
            response.sendRedirect("admin.jsp");

        } catch (SQLException ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateStock(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession();
            String stock = request.getParameter("Stock").trim();
            System.out.println(stock);
            PreparedStatement ps;
            ps = con.prepareStatement("UPDATE INVENTORY set STOCK=? where PRODUCT=?");
            ps.setString(1, stock);
            ps.setString(2, (String) session.getAttribute("adminProduct"));
            ps.executeUpdate();
            session.setAttribute("adminStock", stock);
            response.sendRedirect("admin.jsp");
        } catch (SQLException ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updatePrice(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession();
            String stock = request.getParameter("Price").trim();
            System.out.println(stock);
            PreparedStatement ps;
            ps = con.prepareStatement("UPDATE INVENTORY set PRICE=? where PRODUCT=?");
            ps.setString(1, stock);
            ps.setString(2, (String) session.getAttribute("adminProduct"));
            ps.executeUpdate();
            session.setAttribute("adminPrice", stock);
            response.sendRedirect("admin.jsp");
        } catch (SQLException ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void logOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("Home.jsp");
    }

    public void downloadPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        try {
            HttpSession session = request.getSession();
            SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy/MM/dd");
            Date dateObject = new Date();
            String date = formatterDate.format(dateObject);
            SimpleDateFormat formatterTime = new SimpleDateFormat("h:mm aa");
            String time = formatterTime.format(dateObject);
            Document document = new Document();
            Rectangle rect = new Rectangle(PageSize.LETTER.rotate());
            document.setPageSize(rect);
            OutputStream fos = new FileOutputStream(new File("Inventory.pdf"));
            PdfWriter.getInstance(document, fos);
            Font font = new Font( Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
            Paragraph paragraph = new Paragraph(new Phrase("INVENTORY"));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            Paragraph paragraphTime = new Paragraph(new Phrase(time));
            paragraphTime.setAlignment(Element.ALIGN_CENTER);
            Paragraph paragraphDate = new Paragraph(new Phrase(date));
            paragraphDate.setAlignment(Element.ALIGN_CENTER);
            document.open();
            String admin = (String) session.getAttribute("userEmail");
            document.add(new Paragraph("Current Admin User: " + admin));
            document.add(paragraph);
            document.add(paragraphDate);
            document.add(paragraphTime);
            
            PreparedStatement ps;
            ps = con.prepareStatement("SELECT * from INVENTORY");
            ResultSet rs = ps.executeQuery();
            PdfPTable table = new PdfPTable(6);
            table.setSpacingBefore(20f);
            table.addCell("PRODUCT");
            table.addCell("TYPE");
            table.addCell("BRAND");
            table.addCell("STOCK");
            table.addCell("PRICE");
            table.addCell("IMAGE");
            
            while(rs.next()){
                table.addCell(new PdfPCell(new Phrase(rs.getString("PRODUCT"), font)));
                table.addCell(new PdfPCell(new Phrase(rs.getString("TYPE"), font)));
                table.addCell(new PdfPCell(new Phrase(rs.getString("BRAND"), font)));
                table.addCell(new PdfPCell(new Phrase(rs.getString("STOCK"), font)));
                table.addCell(new PdfPCell(new Phrase(rs.getString("PRICE"), font)));
                table.addCell(new PdfPCell(new Phrase(rs.getString("IMAGE"), font)));
            }
            document.add(table);
            document.close();
            fos.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + "Inventory.pdf");
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream("Inventory.pdf");
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.close();
    }

}
