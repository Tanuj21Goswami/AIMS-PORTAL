package org.aims;

import functionalTests.component.migration.C;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Student_DB {


    public boolean register(Connection conn, String roll,String c_id) throws SQLException {
        double cgpa=cgpa(conn,roll);

        Statement statement;
        ResultSet rs=null;
        String cp="";
        double cg=11;
        double cred=0;
        String type="";

        Cycle cycle=new Cycle();
        int year=cycle.yrsem(conn,"year");
        int sem=cycle.yrsem(conn,"sem");
            String query=String.format("Select pc,cg,credit,type from prer where course_id='%s' and sem=%d and year=%d;",c_id,sem,year);
            System.out.println(query);
            statement=conn.createStatement();
            rs= statement.executeQuery(query);
            while (rs.next())
            {
                cp=cp+rs.getString("pc");
                cg=rs.getFloat("cg");
                cred=rs.getFloat("credit");
                type=rs.getString("type");
            }

        if(cg==11)
        {
            System.out.println("Course Not Offered");
//            break;
            return false;
        }
        else {

            String[] arr=null;
            arr=cp.split(" ");
            int f=1;
            if(cp.equals("null"))
            {
            }
            else {
                for (int i = 0; i < arr.length; i++) {
                    String query1 = String.format("Select course from student_%s where course='%s';", roll, arr[i]);
//                System.out.println(query1);
                    Statement statement1;
                    ResultSet rs1 = null;
                    try {
                        statement1 = conn.createStatement();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    rs1 = statement1.executeQuery(query1);
                    String out = "";
                    while (rs1.next()) {
                        out += rs1.getString("course");
                    }
                    if (out.isEmpty()) {
                        System.out.println("Prerequisites not matched!!");
                        return false;
                    }
                    System.out.println(out);
                }
            }
            if(cgpa<cg)
            {
                System.out.println("Prerequisites not matched");
                return false;
            }

            double allowance=allowance(conn,roll);

            if(cred+sum(conn,roll)>allowance)
            {
                System.out.println("Credit limit exceeded!!");
                return false;
            }
            else {
                    int x=-1;
//                    System.out.println("hello");
                    String query2=String.format("Insert into curr_%s values('%s',%.2f,%d,'%s');",roll,c_id,cred,x,type);
//                    System.out.println(query2);
                    Statement statement2= conn.createStatement();
                    statement2.executeUpdate(query2);
                    System.out.println("Course Registered");
                    return true;
            }
        }

    }

    public boolean deregister(Connection conn, String roll,String c_id) throws SQLException {
            String query1=String.format("Delete from curr_%s where course_id='%s';",roll,c_id);
            Statement statement= conn.createStatement();
            statement.executeUpdate(query1);
            System.out.println("Course Dropped");
            return true;}
    public boolean view_grade(Connection conn,String roll) throws SQLException {
            ResultSet rs=null;
            String query=String.format("Select course,grade from Student_%s",roll);
            Statement statement =conn.createStatement();
            rs= statement.executeQuery(query);
            System.out.println("Grades of Student "+roll+" are as follows:");
            while(rs.next())
                System.out.println("Course: "+ rs.getString("course")+" Grade: "+rs.getInt("grade"));
            System.out.println("\n");
            return true;}
    public double cgpa(Connection conn,String roll) throws SQLException {
            ResultSet rs=null;
            String query=String.format("Select * from Student_%s;",roll);
            Statement statement=conn.createStatement();
            rs=statement.executeQuery(query);
            double st=0;
            double tot=0;
            while(rs.next()) {
                st=st+rs.getDouble("Credit")*rs.getInt("Grade");
                tot=tot+rs.getDouble("Credit");}
            double cgpa=(st*1.00)/tot;
            System.out.println(cgpa);
            return cgpa;}
    public double sum(Connection conn, String roll) throws SQLException {
            ResultSet rs=null;
            String query=String.format("Select * from curr_%s;",roll);
            Statement statement= conn.createStatement();
            rs=statement.executeQuery(query);
            double ans=0;
            while(rs.next())
                ans+=rs.getDouble("credit");
            return ans;}

    public double allowance(Connection conn , String roll) throws SQLException {
        Cycle cycle=new Cycle();
        int yr=cycle.yrsem(conn,"year");
        int sem=cycle.yrsem(conn,"sem");


        Statement statement;
        ResultSet rs=null;
        double allowance=0;
        String query="";
        if(sem==1)
        {
            query=String.format("Select * from student_%s where year_comp=%d;",roll,yr-1);
        }
        else
        {
            query=String.format("Select * from student_%s where (year_comp=%d and sem_comp=2) or (year_comp=%d and sem_comp=1);",roll,yr-1,yr);
        }
        statement=conn.createStatement();
        rs=statement.executeQuery(query);
        while(rs.next()) {
            allowance+=rs.getDouble("credit");}
        if(allowance==0)
            return 18;
        return allowance;
    }

    public boolean view_course(Connection conn) throws SQLException {
        ResultSet rs=null;
        String query=String.format("Select * from course_offered;");
        Statement statement= conn.createStatement();
        rs=statement.executeQuery(query);
        while (rs.next()) {
            System.out.println(
                    "Course_id: "+rs.getString("course_id")+" Course: "+rs.getString("course")+
                    " Professor: "+ rs.getString("prof")+" LTS: "+rs.getString("lts")+ " Credit: "+rs.getDouble("credit"));}
        return true;}
}
