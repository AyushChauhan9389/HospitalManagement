package EHMS;

import java.util.*;
public class Payment 
{
Scanner input=new Scanner(System.in);
public String CreditCardDetails(int fee)
{
	String Status;
	System.out.println("\t\tCARD-HOLDER Name: ");
	String cardHolderName=input.nextLine();
	System.out.println("\t\tCARD-NUMBER : ");
	String card_no=input.nextLine();
	System.out.println("\t\tEXPIRY DATE : ");
	String ExpiryDate=input.nextLine();
	System.out.println("\t\tCVC number: ");
	int cvc=input.nextInt();
	System.out.println("Please Enter 1 to confirm Payment---");
	int x=input.nextInt();
	if(x==1)
	{
		System.out.println("Your Payment is confirmed");
		return "Payed";
	}
	else
	{
		System.out.println("Your Appointment is cancelled");
		return "NotPayed";
	}
}
}
