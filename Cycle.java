package org.aims;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Cycle {
    public int Phase (Connection conn) throws SQLException {
        ResultSet rs=null;
            String query=String.format("Select phase from cycle ;");
            Statement statement=conn.createStatement();
            rs=statement.executeQuery(query);
            int x=-1;
            while(rs.next())
                x=rs.getInt("Phase");
            return x;}

    public boolean Update_phase(Connection conn,int phase) throws SQLException {
            String query=String.format("Update cycle set phase=%d ;",phase);
            Statement statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Phase updated");
    return true;}

    public boolean insert(Connection conn, int year, int sem ,int phase) throws SQLException {
            String query=String.format("Delete from cycle;",phase);
            Statement statement=conn.createStatement();
            statement.executeUpdate(query);
            Statement statement1;
            String query1=String.format("Insert into cycle values(%d,%d,%d);",year,sem,phase);
            System.out.println(query1);
            statement1=conn.createStatement();
            statement1.executeUpdate(query1);
            return true;}

    public int yrsem (Connection conn,String yrsem) throws SQLException {
        ResultSet rs=null;
            String query=String.format("Select %s from cycle ;",yrsem);
            Statement statement=conn.createStatement();
            rs=statement.executeQuery(query);
            int x=-1;
            while(rs.next())
                x=rs.getInt(yrsem);
            return x;}
}
