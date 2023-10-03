package org.aims;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Faculty_db {

    public boolean registration( Connection conn,String name,String c_name,String c_id,float cred,String lts,String prec,float cg,String depts) throws SQLException {
        Cycle cycle=new Cycle();
        int yr=cycle.yrsem(conn,"year");
        int sem=cycle.yrsem(conn,"sem");
        add_catalog(conn,c_id,name,c_name,cred);
        add_pre(conn,c_id,name,cred,prec,cg,sem,yr,depts);
        add_off(conn,c_id,c_name,name,lts,cred);
        return true;
    };

    public boolean deregistration(Connection conn,String c_id,String name) throws SQLException {
        Cycle cycle=new Cycle();
        int yr=cycle.yrsem(conn,"year");
        int sem=cycle.yrsem(conn,"sem");
            String query=String.format("Delete from Course_Catalog where course_id='%s'and prof='%s';",c_id,name);
            String query1=String.format("Delete from prer where course_id='%s'and prof='%s'and sem=%d and year=%d;",c_id,name,sem,yr);
            String query2=String.format("Delete from Course_offered where course_id='%s'and prof='%s';",c_id,name);
            Statement statement= conn.createStatement();
            statement.executeUpdate(query1);
            statement.executeUpdate(query);
            statement.executeUpdate(query2);
            return true;
    }

    public void add_catalog(Connection conn,String c_id,String name, String c_name,float credit) throws SQLException {
            String query=String.format("Insert into Course_catalog values ('%s','%s','%s',%.2f);",c_id,name,c_name,credit);
            Statement statement= conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Course Added");}
    public void add_pre(Connection conn,String c_id,String name,float credit,String pc,float cg,int sem,int yr,String dept) throws SQLException {
            String query=String.format("Insert into prer values ('%s','%s',%.2f,'%s',%.2f,%d,%d,'%s');",c_id,name,credit,pc,cg,sem,yr,dept);
            Statement statement= conn.createStatement();
            statement.executeUpdate(query);}
    public void add_off(Connection conn,String c_id,String c_name,String name,String lts,float credit) throws SQLException {
            String query=String.format("Insert into course_offered values ('%s','%s','%s','%s',%.2f);",c_id,c_name,name,lts,credit);
            Statement statement= conn.createStatement();
            statement.executeUpdate(query);}
    public String search(Connection conn, String table_name,String email) throws SQLException {
        ResultSet rs=null;
            String query="select name from "+table_name+" where email= '"+email +"';";
            Statement statement=conn.createStatement();
            rs= statement.executeQuery(query);
            String out=null;
            while(rs.next())
                out=rs.getString("name");
            if(out==null)
                return null;
            return out;}

    public boolean Grade_upgrade_manually(Connection conn,String name,String c_id,String[] arr) throws SQLException {
        ResultSet rs=null;
            String query=String.format("Select prof from course_offered where course_id ='%s';",c_id);
            System.out.println(query);
            Statement statement= conn.createStatement();
            rs=statement.executeQuery(query);
            String out="";
            while(rs.next())
            {
                out=rs.getString("prof");
            }
            System.out.println(name);
            if(name.equals(out))
            {
                            String query1=String.format("Update curr_%s set grade=%s where course_id='%s';",arr[0],arr[1],c_id);
                            Statement statement1= conn.createStatement();
                            statement1.executeUpdate(query1);
            }
            else {
                System.out.println("Sorry , this course is not taken by you");
                return false;}
            return true;}
    public boolean Grade_upgrade_via_file(Connection conn,String name,String c_id,String file) throws SQLException, IOException {
        ResultSet rs=null;
            String query=String.format("Select prof from course_offered where course_id ='%s';",c_id);
            System.out.println(query);
            Statement statement= conn.createStatement();
            rs=statement.executeQuery(query);
            String out="";
            while(rs.next())
            {
                out=rs.getString("prof");
            }
            System.out.println(name);
            if(name.equals(out))
            {
                BufferedReader reader=null;
                String line="";
                    reader = new BufferedReader(new FileReader(file));
                    while((line = reader.readLine()) != null) {

                        String[] arr = line.split(",");
                        Statement statement1;

                            String query1=String.format("Update curr_%s set grade=%s where course_id='%s';",arr[0],arr[1],c_id);
                            statement1= conn.createStatement();
                            statement1.executeUpdate(query1);
                    }
                reader.close();
                return true;
            }
            else
            {
                System.out.println("Sorry , this course is not taken by you");
                return false;
            }
    }

    public boolean view_grades(Connection conn) throws SQLException {
        ResultSet rs=null;
        String query=String.format("Select email from student ;");
        Statement statement= conn.createStatement();
        rs=statement.executeQuery(query);
        while(rs.next())
        {
            String email=rs.getString("email");
            String roll="";
            for (int i=0;i<email.length();i++) {
                if(email.charAt(i)=='@')
                    break;
                roll=roll+email.charAt(i);}
            Student_DB studentDb=new Student_DB();
            studentDb.view_grade(conn,roll);
        }
        return true;}
}
