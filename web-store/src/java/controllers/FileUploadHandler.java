/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadHandler extends HttpServlet {

    private static final long serialVersionUID = 1;

 

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String file_name = null;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
        if (!isMultipartContent) {
            return;
        }
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List< FileItem> fields = upload.parseRequest(request);
            Iterator< FileItem> it = fields.iterator();
            if (!it.hasNext()) {
                return;
            }
            while (it.hasNext()) {
                FileItem fileItem = it.next();
                boolean isFormField = fileItem.isFormField();
                if (isFormField) {
                    if (file_name == null) {
                        if (fileItem.getFieldName().equals("file_name")) {
                            file_name = fileItem.getString();
                        }
                    }
                } else {            
                    if (fileItem.getSize() > 0) {
                        URL url = FileUploadHandler.class.getResource("FileUploadHandler.class");
                        String path = url.getPath();
                        System.out.println(path);
                        System.out.println(path.substring(0, path.length() - 61) );
                        String finalPath = path.substring(0, path.length() - 61) ;
                        fileItem.write(new File(finalPath  + "web/img/"+ fileItem.getName()));
                        HttpSession session = request.getSession();
                        session.setAttribute("imageLink", fileItem.getName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
             out.println("<script type='text/javascript'>");
            out.println("window.location.href='admin.jsp?filename=" + file_name + "'");
            out.println("</script>");
            out.close();
            
        }
         
    }
    
}
        