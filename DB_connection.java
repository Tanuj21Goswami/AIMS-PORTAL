package org.aims;
import java.sql.Connection;
import java.sql.DriverManager;

public class DB_connection {
    public Connection connect_to_db(String DB_name,String user,String pass)
    {
        Connection conn=null;
        try {
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+DB_name,user,pass);

            if(conn!=null)
            {
                System.out.println("Connection Established!!");
            }
            else {
                System.out.println("Connection failed!!");
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return conn;
    }
}
