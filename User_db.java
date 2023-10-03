package org.aims;
import java.sql.*;
public class User_db {
    public boolean pass_check(Connection conn, String email, String pass) throws SQLException {
            ResultSet rs=null;
            String query="Select * from user_record where email_id= '"+email+"' and password='"+pass+"';";
            Statement statement=conn.createStatement();
            rs= statement.executeQuery(query);
            String out="";
            while(rs.next())
                out=out+rs.getString("password")+"\n";
            if(out.isEmpty())
                return false;
            return true;}
    public boolean update_pass(Connection conn, String email,String oldpass,String newpass) throws SQLException {
            String query=String.format("Update user_record set password='%s' where email_id='%s' and password='%s';",newpass,email,oldpass);
            Statement statement=conn.createStatement();
            System.out.println(query);
            statement.executeUpdate(query);
            System.out.println("Password updated");
            return true;}
    public boolean search(Connection conn, String table_name,String email) throws SQLException {
        ResultSet rs=null;
            String query="select * from "+table_name+" where email_id= '"+email +"';";
            Statement statement=conn.createStatement();
            rs= statement.executeQuery(query);
            String out="";
            while(rs.next())
            {   out=out+rs.getString("name_id")+" ";
                out=out+rs.getString("email_id")+" ";}
        System.out.println(out);
            if(out.isEmpty())
                return false;
            System.out.println(out);
            return true;}}
