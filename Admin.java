package org.aims;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Admin {
    public void functions()
    {
        DB_connection db=new DB_connection();
        Connection conn=db.connect_to_db("aims","postgres","1234");

        try {
            System.out.println("Hello Admin");
            System.out.println("Press 0 to edit Course Catalog , \n" +
                    "Press 1 to view grades of students , \n" +
                    "Press 2 to generate transcript of students,\n" +
                    "Press 3 to change phase of Semester\n" +
                    "Press 4 to introduce new cycle \n" +
                    "Press 5 for graduation check\n" +
                    "Press 6 to add users " +
                    "Press 7 to logout\n");

            Scanner input = new Scanner(System.in);
            int no = input.nextInt();
            System.out.println("Digit Pressed " + no);

            if (no == 0) {

                System.out.println("Press 0 to drop a course \n" +
                        "Press 1 to edit the course related info ");

                int x=input.nextInt();
                System.out.println("Enter the course_id");
                String c_id=input.next();
                Admin_db adminDb=new Admin_db();
                if(x==0)
                {
                    System.out.println("Enter the faculty name");
                    String prof=input.nextLine();
                    input.nextLine();
                    boolean f=adminDb.drop(conn,c_id,prof);
                }
                else
                {
                    System.out.println("Enter the LTS");
                    String lts=input.nextLine();
                    input.nextLine();
                    boolean f=adminDb.update_lts(conn,c_id,lts);
                }

            } else if (no == 1) {
                System.out.println("Press 1 to view grade of any student\n" +
                        "Press 2 to view grade of every student");
                int x=input.nextInt();
                if(x==1) {
                    System.out.println("Enter roll no. of student ");
                    String roll = input.next();
                    Student_DB st = new Student_DB();
                    st.view_grade(conn, roll);
                } else if (x==2) {
                    System.out.println("Here are the list of grades of students...");
                    Faculty_db faculty_db=new Faculty_db();
                    faculty_db.view_grades(conn);
                }

            } else if (no == 2) {
                Admin_db adminDb=new Admin_db();
                transcript(conn);
            } else if(no==3){
                Cycle cycle=new Cycle();
                int phase=cycle.Phase(conn);
                System.out.println("Current phase is "+ phase);
                System.out.println("Press the respective key to change the phase ");
                System.out.println("Press 0 to switch phase 0 course register by faculty\n" +
                        "Press 1 to switch phase 1 courses open for students to enroll\n" +
                        "Press 2 to switch phase 2 grading by faculty\n" +
                        "Press 3 to switch phase 3 updating student's TABLE\n");
                int newphase=input.nextInt();
                boolean f=cycle.Update_phase(conn,newphase);
            }
            else if(no==4)
            {
                Cycle cycle=new Cycle();
                int c_yr= cycle.yrsem(conn,"year");
                int c_sem= cycle.yrsem(conn,"sem");

                Grade_filling gradeFilling=new Grade_filling();
                boolean f1=gradeFilling.functions(conn,c_yr,c_sem);

                System.out.println("Enter the year");
                int yr=input.nextInt();
                System.out.println("Enter the sem");
                int sem=input.nextInt();
                System.out.println("Enter the phase");
                int phase= input.nextInt();
                boolean f=cycle.insert(conn,yr,sem,phase);
            } else if (no==5) {
                Cycle cycle=new Cycle();
                int phase=cycle.Phase(conn);
                if(phase==3) {
                    System.out.println("Press 1 to check graduation of a specific student\n" +
                            "Press 2 to check graduation of all students");
                    int y=input.nextInt();
                    if(y==1) {
                        System.out.println("Enter the roll number ");
                        String roll = input.next();
                        Admin_db adminDb = new Admin_db();
                        boolean x = adminDb.grad_check(conn, roll);
//                        if (x)
//                            System.out.println("BTECH Graduation of " + roll + " is completed");
//                        else
//                            System.out.println("BTECH Graduation of " + roll + " is not completed");
                    } else if (y==2) {
                        Admin_db adminDb=new Admin_db();
                        boolean f=adminDb.grad_check_all(conn);
                    }else {
                        System.out.println("Invalid Input");
                    }
                }
                else
                {
                    System.out.println("Grading of this sem is still left ");
                }

            } else if (no==6) {
                System.out.println("Press 1 to enter a student\n" +
                        "Press 2 to enter a faculty");
                int x=input.nextInt();
                if(x==1)
                {
                    System.out.println("Enter the student details");
                    System.out.println("Enter Name");
                    String name=input.nextLine();
                    input.nextLine();
                    System.out.println("Enter email");
                    String email=input.nextLine();
                    input.nextLine();
                    System.out.println("Enter year");
                    int yr=input.nextInt();
                    User_add userAdd=new User_add();
                    boolean f=userAdd.functions1(conn,name,email,yr);
                }
                else if(x==2)
                {
                    System.out.println("Enter the faculty details");
                    System.out.println("Enter Name");
                    String name=input.nextLine();
                    input.nextLine();
                    System.out.println("Enter email");
                    String email=input.nextLine();
                    input.nextLine();
                    System.out.println("Enter dept");
                    String dept=input.nextLine();
                    input.nextLine();
                    User_add userAdd=new User_add();
                    boolean f=userAdd.functions2(conn,name,email,dept);
                }

            } else if (no==7) {
                System.out.println("Logout Successful");
                Home home=new Home();
                home.home();
                return;
            }
            else {
                System.out.println("Invalid Input");
            }
            Admin admin=new Admin();
            admin.functions();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    public void transcript(Connection conn)
    {
        System.out.println("Enter the roll number of student");
        Scanner input = new Scanner(System.in);
        String roll=input.next();
        try
        {
            String name="transcript_"+roll+".txt";
            File fw=new File(name);

            if(!fw.exists())
            {
                fw.createNewFile();
            }
            FileWriter fileWriter=new FileWriter(name);
            fileWriter.write("Report of "+roll+"\n");

            fileWriter.write("Course Credit Grade\n");
            Statement statement;
            ResultSet rs=null;
            String out="";
            try
            {
                String query=String.format("Select * from student_%s",roll);
                statement=conn.createStatement();
                rs=statement.executeQuery(query);

                while (rs.next())
                {
                    out+=rs.getString("course")+"  "+rs.getDouble("credit")+"    "+rs.getInt("grade");
                    out+="\n";
                }

            }
            catch (Exception e)
            {
                System.out.println(e);
            }
            fileWriter.write(out);


            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
