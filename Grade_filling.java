package org.aims;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Grade_filling {
    public boolean functions(Connection conn,int yr,int sem) throws SQLException {
        ResultSet rs=null;
        String query=String.format("Select email from student;");
        Statement statement=conn.createStatement();
        rs=statement.executeQuery(query);
        while(rs.next()) {
            String roll="";
            System.out.println(rs.getString("email"));
            String email=rs.getString("email");
            for (int i=0;i<email.length();i++) {
                if(email.charAt(i)=='@')
                    break;
                roll=roll+email.charAt(i);}
            grade_read(conn,roll,yr,sem);}
        return true;
    }


    public void grade_read(Connection conn, String roll, int yr,int sem) throws SQLException {
        ResultSet rs=null;
        System.out.println(roll+"!!");
        String query=String.format("Select * from curr_%s;",roll);
        Statement statement=conn.createStatement();
        rs=statement.executeQuery(query);
        while (rs.next()) {
            String course=rs.getString("course_id");
            double credit=rs.getDouble("credit");
            int grade=rs.getInt("grade");
            String type=rs.getString("type");
            grade_update(conn,roll,course,credit,grade,type,yr,sem);}
        query=String.format("Delete from curr_%s;",roll);
        statement=conn.createStatement();
        statement.executeUpdate(query);
    }

    public void grade_update(Connection conn, String roll,String course,double credit,int grade,String type, int yr,int sem) throws SQLException {
        ResultSet rs=null;
        String query=String.format("Insert into student_%s values('%s',%.2f,%d,'%s',%d,%d); ",roll,course,credit,
                grade,type,yr,sem);
        Statement statement=conn.createStatement();
        statement.executeUpdate(query);
    }
}


