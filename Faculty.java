package org.aims;

import java.sql.Connection;
import java.util.Scanner;

public class Faculty {
    public void functions(Connection conn,String email)
    {
        try {
            System.out.println("Hello "+email);
            System.out.println("Press 0 to view grade of students ,\n" +
                    "Press 1 to register/deregister any of your course  ,\n" +
                    "Press 2 to update course grades\n" +
                    "Press 3 to logout\n");
            Scanner input = new Scanner(System.in);
            int no = input.nextInt();
            System.out.println("Digit Pressed " + no);

            if (no == 0) {
                System.out.println("Press 1 to view grade of any student\n" +
                        "Press 2 to view grade of every student");
                int x=input.nextInt();
                if(x==1) {
                    System.out.println("Enter roll no. of student ");
                    String roll = input.next();
                    Student_DB st = new Student_DB();
                    st.view_grade(conn, roll);
                } else if (x==2) {
                    System.out.println("Here are the list of grades os students...");
                    Faculty_db faculty_db=new Faculty_db();
                    boolean f=faculty_db.view_grades(conn);
                }

            } else if (no == 1) {
                Cycle cycle=new Cycle();
                int phase=cycle.Phase(conn);
                if(phase!=0)
                {
                    System.out.println("Sorry this window is closed now !");
                }
                else {
                    System.out.println("Press 0 if you want to register a course\n" +
                            "Press 1 to deregister a course ");

                    int in = input.nextInt();
                    Faculty_db faculty_db = new Faculty_db();
                    if (in == 0) {
//                        Scanner input=new Scanner(System.in);
                        String name=null;
                        name=faculty_db.search(conn,"Prof",email);
                        if(name==null)
                        {
                            System.out.println("Instructor not registered");
                            return;
                        }
                        System.out.println("Enter the Course Name..");
                        String c_name= input.nextLine();
                        System.out.println("Enter the Course id");
                        String c_id=input.next();
                        System.out.println("Enter the credits");
                        float cred=input.nextFloat();
                        input.nextLine();
                        System.out.println("Enter the LTS structure");
                        String lts=input.next();
                        input.nextLine();
                        System.out.println("Enter the Prerequisite Courses ID else write null");
                        String prec= input.nextLine();
                        System.out.println("Enter the eligible CG ");
                        float cg=input.nextFloat();
                        input.nextLine();
//                        System.out.println("Enter the sem 1 for odd 2 for even");
//                        int sem=input.nextInt();
//                        input.nextLine();
                        System.out.println("Enter the course type");
                        String depts=input.next();

                        boolean f=faculty_db.registration(conn,name,c_name,c_id,cred,lts,prec,cg,depts);
                    } else {
                        String name=null;
                        name=faculty_db.search(conn,"Prof",email);
                        if(name==null)
                        {
                            System.out.println("Instructor not registered");
                            return;
                        }
                        System.out.println("Enter the Course id");
                        String c_id=input.next();
//                        System.out.println("Enter the sem 1 for odd 2 for even");
//                        int sem=input.nextInt();
                        boolean f=faculty_db.deregistration(conn,c_id,name);
                    }
                }

            } else if (no == 2) {
                Cycle cycle=new Cycle();
                int phase=cycle.Phase(conn);
                if(phase!=2)
                {
                    System.out.println("Sorry this window is closed now !");
                }
                else
                {
                    Faculty_db faculty_db=new Faculty_db();
                    System.out.println("Press 1 if you want to add course grades manually \n" +
                            "Press 2 if you want to to add course grades from a file");
                    int x = input.nextInt();
                    System.out.println("Enter the course_id");
                    String c_id=input.next();
                    String name=null;
                    name=faculty_db.search(conn,"Prof",email);
                    if(name==null)
                    {
                        System.out.println("Instructor not registered");
                        return;
                    }
                    if (x == 1) {

                        System.out.println("Enter the roll no and grade");
                        String line=input.next();
                        input.nextLine();
                        String[] arr=null;
                        arr=line.split(" ");
                        boolean f=faculty_db.Grade_upgrade_manually(conn,name,c_id,arr);

                    } else {
                        String file="grading.csv";
                        boolean f=faculty_db.Grade_upgrade_via_file(conn, name,c_id,file);
                    }
                }

            } else if (no==3){
                System.out.println("Logout Successful");
                Home home=new Home();
                home.home();
                return;
            }
            else {
                System.out.println("Invalid Input");
            }
            Faculty faculty=new Faculty();
            faculty.functions(conn, email);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
