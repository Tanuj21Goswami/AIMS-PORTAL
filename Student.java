package org.aims;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.util.Scanner;

public class Student {
    public void functions(String email)
    {

        //Search for email id in student record database
        DB_connection db=new DB_connection();
        Connection conn=db.connect_to_db("aims","postgres","1234");

        try {
            Scanner input=new Scanner(System.in);

            System.out.println("Hello "+email);
            System.out.println("Press 0 to view course offered \n" +
                    "Press 1 to register / deregister a course ,\n" +
                    "Press 2 to view your grades, \n" +
                    "Press 3 to compute your CGPA\n" +
                    "Press 4 to logout..");

            Student_DB st=new Student_DB();
            int no = input.nextInt();
            System.out.println("Digit Pressed " + no);
            String roll="";
            for (int i=0;i<email.length();i++)
            {
                if(email.charAt(i)=='@')
                    break;
                roll=roll+email.charAt(i);
            }
            if(no==0)
            {
                Student_DB studentDb=new Student_DB();
                boolean f=studentDb.view_course(conn);
            }
            else if (no == 1)
            {
                Cycle cycle=new Cycle();
                int phase=cycle.Phase(conn);
                if(phase!=1)
                {
                    System.out.println("Sorry this window is closed now !");
                }
                else {
                    System.out.println("Press 0 to register for a course ,\nPress 1 to deregister a course");
                    int x = input.nextInt();
                    Student_DB studentDb = new Student_DB();
                    if (x == 0) {


                        System.out.println("Enter the course id you want to register");
                        String c_id=input.next();
                        input.nextLine();
                        boolean f=studentDb.register(conn, roll,c_id);
                    }
                    else {
                        System.out.println("Enter the course_id you want to drop");
//                        Scanner input = new Scanner(System.in);
                        String c_id=input.next();
                        boolean f=studentDb.deregister(conn, roll,c_id);
                    }
                }
            } else if (no == 2)
            {
                boolean f=st.view_grade(conn,roll);

            } else if (no == 3) {

//                System.out.println(roll);
                double cgpa=st.cgpa(conn,roll);
                System.out.println("CGPA : "+cgpa);

            } else if (no==4) {
                System.out.println("Logout successful");
                Home home=new Home();
                home.home();
                return;
            }
            Student student=new Student();
            student.functions(email);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

}
