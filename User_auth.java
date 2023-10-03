package org.aims;

import java.io.Console;
import java.sql.Connection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User_auth {

    public void authentication()
    {
        DB_connection db=new DB_connection();
        Connection conn=db.connect_to_db("aims","postgres","1234");
        try
        {
            System.out.println("To sign in , confirm your role");
            System.out.println("Press 0 if you are Student...");
            System.out.println("Press 1 if you are Faculty...");
            System.out.println("Press 2 if you are Admin...");

            Scanner input=new Scanner(System.in);
            int no=input.nextInt();
            System.out.println("Digit Pressed "+no);

            if(no==0)
            {
                System.out.println("Enter your assigned email address");
                String email=input.next();
                System.out.println("Email entered : "+email);
                boolean x=useRegexS(email,0);
                if(x)
                {
                    User_db user=new User_db();

                    if(!user.search(conn,"user_record",email))
                    {
                        System.out.println("User Not Registered");
                        Home home=new Home();
                        home.home();
                        return;
                    }
                    System.out.println("Enter your password to login");
//                    Console cnsl=System.console();
                    String pass=input.next();
//                    String pass=cnsl.readLine();
                    boolean y=user.pass_check(conn,email,pass);
                    if(!y)
                    {
                        System.out.println("Entered Password is incorrect");
//                        return;
                        Home home=new Home();
                        home.home();
                        return;
                    }
                    System.out.println("Login Successful");
                    System.out.println("If you want to update your password press 0 else press any other digit");
                    int p=input.nextInt();
                    if(p==0)
                    {
                        System.out.println("Enter your new Password");
                        String newpass=input.next();
//                        String newpass=cnsl.readLine();
                        boolean f=user.update_pass(conn,email,pass,newpass);
                    }

                    Student st=new Student();
                    st.functions(email);
                }
                else
                {
                    System.out.println("Invalid Email");
                    Home home=new Home();
                    home.home();
                    return;
                }
            }
            else if (no==1)
            {
                System.out.println("Enter your assigned email address");
                String email=input.next();
                System.out.println("Email entered : "+email);
                boolean x=useRegexS(email,1);
                if(x)
                {
                    User_db user=new User_db();
                    if(!user.search(conn,"user_record",email))
                    {
                        System.out.println("User Not Registered");
                        Home home=new Home();
                        home.home();
                        return;
                    }
                    System.out.println("Enter your password to login");
                    String pass=input.next();
                    boolean y=user.pass_check(conn,email,pass);
                    if(!y)
                    {
                        System.out.println("Entered Password is incorrect");
                        Home home=new Home();
                        home.home();
                        return;
                    }
                    System.out.println("Login Successful");
                    System.out.println("If you want to update your password press 0");
                    int p=input.nextInt();
                    if(p==0)
                    {
                        System.out.println("Enter the new password");
                        String newpass=input.next();
                        boolean f=user.update_pass(conn,email,pass,newpass);
                    }
                    Faculty fc=new Faculty();
                    fc.functions(conn,email);
                }
                else
                {
                    System.out.println("Invalid Email");
                    Home home=new Home();
                    home.home();
                    return;
                }
            }
            else if (no==2)
            {
                System.out.println("Enter your assigned email address");
                String email=input.next();
                System.out.println("Email entered : "+email);
                boolean x=useRegexS(email,1);
                if(x)
                {
                    User_db user=new User_db();
                    if(!user.search(conn,"user_record",email))
                    {
                        System.out.println("User Not Registered");
                        Home home=new Home();
                        home.home();
                        return;
                    }
                    System.out.println("Enter your password to login");
                    String pass=input.next();
                    boolean y=user.pass_check(conn,email,pass);
                    if(!y)
                    {
                        System.out.println("Entered Password is incorrect");
                        Home home=new Home();
                        home.home();
                        return;
                    }
                    System.out.println("Login Successful");
                    System.out.println("If you want to update your password press 0");
                    int p=input.nextInt();
                    if(p==0)
                    {
                        System.out.println("Enter the new password");
                        String newpass=input.next();
                        boolean f=user.update_pass(conn,email,pass,newpass);
                    }
                    else
                    {
                        Admin admin=new Admin();
                        admin.functions();
                    }


                }
                else
                {
                    System.out.println("Invalid Email");
                    Home home=new Home();
                    home.home();
                    return;
                }
            }
            else {
                System.out.println("Invalid Input..");
                Home home=new Home();
                home.home();
                return;
            }

        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }
    public static boolean useRegexS(String input,int x) {

        if(input==null || input.isEmpty())
            return false;
        System.out.println(input);
        String regex="";
        if(x==0) {
            regex="^\\d\\d\\d\\d(?:(?:(?:(?:(?:csb|mcb)|meb)|mmb)|eeb)|ceb)\\d\\d\\d\\d@iitrpr\\.ac\\.in$";
        } else if (x==1) {
            regex="[a-z]+@iitrpr.ac.in";
        }
        Pattern pattern=Pattern.compile(regex);
        if(pattern.matcher(input).matches())
            return true;
        else
            return false;

    }
}
