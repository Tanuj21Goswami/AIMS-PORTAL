package org.aims;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

public class Admin_db {


    public boolean update_lts(Connection conn, String c_id,String lts) throws SQLException {
            String query=String.format("Update Course_offered set lts='%s' where course_id='%s';",lts,c_id);
            Statement statement= conn.createStatement();
            statement.executeUpdate(query);
            return true;}

    public boolean drop(Connection conn , String c_id,String prof) throws SQLException {
            String query=String.format("Delete from Course_catalog where course_id='%s' and prof='%s';",c_id,prof);
            Statement statement= conn.createStatement();
            statement.executeUpdate(query);
            query=String.format("Delete from Course_offered where course_id='%s' and prof='%s';",c_id,prof);
            statement.executeUpdate(query);
            Cycle cycle=new Cycle();
            int yr=cycle.yrsem(conn,"year");
            int sem=cycle.yrsem(conn,"sem");
            query=String.format("Delete from prer where course_id='%s' and prof='%s' and sem=%d and year=%d;",c_id,prof,sem,yr);
            statement.executeUpdate(query);
            System.out.println("Course Dropped!");
            return true;}



    public boolean grad_check(Connection conn, String roll) throws SQLException {
        ResultSet rs=null;
            String query=String.format("Select * from student_%s;",roll);
            Statement statement= conn.createStatement();
            rs=statement.executeQuery(query);
            double pc=0,ge=0,ot=0,btp=0;
            while(rs.next()) {
                if(Objects.equals(rs.getString("type"), "PC"))
                    pc+=rs.getDouble("credit");
                else if(Objects.equals(rs.getString("type"), "BTP"))
                    btp+=rs.getDouble("credit");
                else if(Objects.equals(rs.getString("type"), "GE"))
                    ge+=rs.getDouble("credit");
                else
                    ot+=rs.getDouble("credit");
            }
        System.out.println(btp);
            if(pc>=60 && ge>=30 && ot>=30 && btp==6) {
                System.out.println("BTECH Graduation of " + roll + " is completed");
                return true;}
        System.out.println("BTECH Graduation of " + roll + " is not completed");
        return false;
    }

    public boolean grad_check_all(Connection conn) throws SQLException {
        ResultSet rs=null;
        String query=String.format("Select email from student where c_year=4;");
        Statement statement= conn.createStatement();
        rs=statement.executeQuery(query);
        while(rs.next()) {
            String email=rs.getString("email");
            String roll="";
            for (int i=0;i<email.length();i++) {
                if(email.charAt(i)=='@')
                    break;
                roll=roll+email.charAt(i);}
            Admin_db adminDb=new Admin_db();
            boolean f=adminDb.grad_check(conn,roll);}
        return true;}
}
