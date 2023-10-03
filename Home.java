package org.aims;

import java.util.Scanner;

public class Home {
    public void home()
    {
        try
        {
            System.out.println("Welcome To the Aims Portal........");
            System.out.println("To Continue further to the portal Press 0 else to exit press 1");
            Scanner input=new Scanner(System.in);
            int no=input.nextInt();
            System.out.println("Digit Pressed "+no);
            if(no==0)
            {
//                System.out.println("Aja bhai aja");
                User_auth ua=new User_auth();
                ua.authentication();
            }
            else
            {
//                System.out.println("Jaa rha hun");
                return;
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }
}
