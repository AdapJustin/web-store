<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%

    ServletContext context = getServletContext();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String image=null;
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

%>