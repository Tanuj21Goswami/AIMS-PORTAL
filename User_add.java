package org.aims;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class User_add {
    public boolean functions1(Connection conn,String name,String email, int yr) throws SQLException {

            String pass="iitrpr";
            String query=String.format("insert into user_record values('%s','%s','%s');",name,email,pass);
            String query1=String.format("insert into student values('%s','%s','%s');",name,email,yr);
            String roll="";
            for (int i=0;i<email.length();i++)
            {
                if(email.charAt(i)=='@')
                    break;
                roll=roll+email.charAt(i);
            }
            String query2="create table student_"+roll+"(Course VARCHAR(50)," +
                    "    Credit FLOAT," +
                    "    Grade INTEGER," +
                    "    Type VARCHAR(50)," +
                    "    YEAR_comp INTEGER," +
                    "    Sem_comp INTEGER," +
                    "    PRIMARY KEY (Course));";

            String query3="create table curr_"+roll+"(Course_id VARCHAR(50)," +
                    "    Credit FLOAT," +
                    "    Grade INT," +
                    "    Type VARCHAR(50)," +
                    "    PRIMARY KEY(Course_id));";

            Statement statement= conn.createStatement();
            statement.executeUpdate(query);
            statement.executeUpdate(query1);
            statement.executeUpdate(query2);
            statement.executeUpdate(query3);
        return true;
    }

    public boolean functions2(Connection conn,String name,String email,String dept) throws SQLException{
        String pass="iitrpr";
        String query=String.format("insert into user_record values('%s','%s','%s');",name,email,pass);
        String query1=String.format("insert into prof values('%s','%s','%s');",name,email,dept);
        Statement statement= conn.createStatement();
        statement.executeUpdate(query);
        statement.executeUpdate(query1);
        return true;
    }
}

