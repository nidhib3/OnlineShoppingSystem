package OnlineShoppingSystem;
import java.util.*;
import java.sql.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.*;

class phonenumberException extends Exception
{
	public phonenumberException(String m)
{
super(m);
}
}

class passwordException extends Exception
{
	public passwordException(String m)
{
super(m);
}
}
class customer 
{

	String customerName;
	long customerPhoneNo;
	String customerEmail;
	String customerAddress;
	long customerCredits;
	String customerPassword;
	long customerTotalBill;
	Vector<product> prevOrders=new Vector<product>();	
	Vector<product> customerOrders=new Vector<product>();
	Vector<product> customerCart = new Vector<product>();
	Vector<product> customerWishList = new Vector<product>();
	deliveredThread delivered_thread ; //= new deliveredThread(0);
	Scanner scanner;
	File file;
	FileWriter fileWriter;
    FileReader fileReader;
	String pathOfCD="C:\\Users\\Nidhi\\Desktop\\OnlineShoppingSystem\\OnlineShoppingSystem\\customerData.csv";

	public void login()
	{
		System.out.println("Enter the following details:\n");
		System.out.println("Email ID:");
		String email=s.next();
		System.out.println("Password:");
		String pass=s.next();
		int ch=-1;

		try
		{
			file= new File(pathOfCD);
			scanner = new Scanner(file);

			while(scanner.hasNext())
			{
                String customerDetails[] = scanner.nextLine().split(",");
                /*System.out.println(customerDetails.length+" length");
                for(int i=0;i<customerDetails.length;i++)
                {
                    System.out.print(customerDetails[i]+" ");
                }
                System.out.println("");
                */
				if(email.equalsIgnoreCase(customerDetails[4]) && pass.equals(customerDetails[7]))
				{
                    System.out.println("SUCCESSFULL LOGIN !!");
					delivered_thread = new deliveredThread(0);
					customer loggedInCustomer = new customer(customerDetails[2],Long.parseLong(customerDetails[3]),Long.parseLong(customerDetails[6]),customerDetails[5],customerDetails[4],customerDetails[7],0);
					//delivered_thread.thisCustomer=new customer(customerDetails[2],Long.parseLong(customerDetails[3]),Long.parseLong(customerDetails[6]),customerDetails[5],customerDetails[4],customerDetails[7],0);
					delivered_thread.thisCustomer = loggedInCustomer;
					//accountSettings aS= new accountSettings(aS, email, pass);
					customerDashBoard cDB = new customerDashBoard(loggedInCustomer);
					cDB.displayCustomerDashBoard(loggedInCustomer);
					//aS.settingsmenu(validInfo);
					//store menu
					//SrNo0,ID1,Name2,Phone No3,Email4,Address5,Credits6,Password7,
				}
				else
				{
					while(ch!=1 || ch!=2 || ch!=0)
					{
						System.out.println("Wrong email ID/Password\n1) Try Again\n2) Sign Up\n0) exit".toUpperCase());
						ch=s.nextInt();
						switch(ch)
						{
							case 1:
								login();
							break;
							case 2:
								signup();
							break;
							case 0:
								System.out.println("Exiting...");
								System.exit(0);
							break;
							default:
								System.out.println("Please enter a valid option".toUpperCase());
						}
					}
				}
			}
			scanner.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

	}
	public void signup()
    {
        //String Name="ABC XYZ",address="P Q R",pass;
        //long phone,credits=0;
        s = new Scanner(System.in);
		System.out.println("Enter the following details:\n");
		System.out.println("Email ID:");
		String email=s.next();
		//System.out.println("Password:");
        //pass=s.next();
        int accFound=0;
        int i=0;
        try
		{
			file= new File(pathOfCD);
			scanner = new Scanner(file);

			while(scanner.hasNextLine())
			{
                String customerDetails[] = scanner.nextLine().split(",");
                /*System.out.println(customerDetails.length+" length");
                for(int i=0;i<customerDetails.length;i++)
                {
                    System.out.print(customerDetails[i]+" ");
                }
                System.out.println("");
                */
				if(email.equalsIgnoreCase(customerDetails[4]))
				{
                    System.out.println(" ACCOUNT EXISTS !!");
                    accFound=1;
                    break;
                }
                i++;
            }
            if(accFound==1)
            {
                System.out.println("\n1) Log in?\n2) Create new account with different Email ID\n0) EXIT\n".toUpperCase());
				//op=s.nextInt();
				switch(s.nextInt())
				{
					case 1:
					login();
					break;
					case 2:
					signup();
					break;
					case 0:
					break;
					default:
					System.out.println("Please enter a valid option");
				}
            }
            else
            {
				customer thisCustomer = create(email,i);
                fileWriter = new FileWriter(pathOfCD,true);
                fileWriter.write("\n"+Integer.toString(i)+","+Integer.toString(i)+","+ thisCustomer.customerName+","+Long.toString(thisCustomer.customerPhoneNo)+","+thisCustomer.customerEmail+","+thisCustomer.customerAddress+","+thisCustomer.customerCredits+","+thisCustomer.customerPassword+",");
				accountSettings aS= new accountSettings(thisCustomer);
				fileWriter.close();
				delivered_thread = new deliveredThread(0);
				delivered_thread.thisCustomer=thisCustomer;
				aS.displayCustomerDashBoard(thisCustomer);
				aS.settingsmenu(thisCustomer);
            }
			scanner.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
    }
	customer(String customerName,long customerPhoneNo,long customerCredits,String customerAddress,String customerEmail,String customerPassword, long customerTotalBill)
	{
	
		this.customerPhoneNo=customerPhoneNo;
		this.customerName=customerName;
		this.customerAddress=customerAddress;
		this.customerCredits=customerCredits;
		this.customerEmail=customerEmail;
		this.customerPassword=customerPassword;
		this.customerTotalBill=customerTotalBill;
		this.delivered_thread = new deliveredThread(0);
	}
	customer()
	{
		
		customerEmail="";
		customerName="";
		customerPhoneNo=0;
		customerAddress="";
		customerCredits=0;
		customerPassword="";
		customerTotalBill=0;
		customerCredits=((long)(customerTotalBill*0.01));
	}
	
	Vector<customer> cust=new Vector<customer>();
	
	static Scanner s=new Scanner(System.in);
	public static void main(String args[]) 
	{
		customer C = new customer();
		C.customMenu();
    }
	
	public void customMenu() 
	{
		customer c=new customer();
		cust.add(c);

		int op=0;
		
		
		while(op!=3)
		{
			
			System.out.println("\n\tMENU\n1) Sign Up\n2) Log In\n0) Exit\n".toUpperCase());
			op=s.nextInt();

			switch(op)
			{
				case 1:
				//signUp();
				signup();
				break;
				case 2:
				//logIn();
				login();
				//break loop1;
				case 0:
				System.exit(0);
				break;
				default:
				System.out.println("INVALID OPTION");
			}
		}
		s.close();
	}

	/*
	public void signUp()
	{
		//s.nextLine();
		System.out.println("Enter Your Details:\n");
		
		System.out.println("Email ID:");
		s.nextLine();
		String email=s.nextLine();

		customer accountExists=check(email,"");
		int op=0;
		if(accountExists.customerEmail!="")
		{
			
				System.out.println("You already have an account with this email Id\n1)Log in?\n2)Create new account with different Email ID\n");
				op=s.nextInt();
				switch(op)
				{
					case 1:
					logIn();
					break;
					case 2:
					signUp();
					break;
					default:
					System.out.println("Please enter a valid option");
				}
					
		}
		else
		{
			create(email,0);
		}
	}
	*/
	public customer create(String email,int customerNo)
	{
		s.nextLine();
		long phone=0;
		String pass="";
		String regexStr = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"; 
		Pattern pattern = Pattern.compile(regexStr); 
		System.out.println("Name:");
		String name=s.nextLine();
		System.out.println("Address:");
		String add=s.nextLine();
		boolean validity=false;
		while(!validity){
		try{		
		System.out.println("Phone Number:");
		phone=s.nextLong();
		if(phone<1000000000)throw new phonenumberException("Please enter a valid phone number(10 digits)");
		else validity=true;

		}
		catch(phonenumberException e)
		{
			System.out.println(e);			
		}
		
		}
		validity=false;
		s.nextLine();
		while(!validity){
		try{
		System.out.println("Password(valid password should contain min 8 characters, atleast one uppercase and one lowercase letter, atleast one special character and one number):");
		pass=s.nextLine();
		Matcher matcher = pattern.matcher(pass);
		if(!matcher.matches())throw new passwordException("Please enter a valid password(min 8 characters, atleast one uppercase and one lowercase letter, atleast one special character and one number)");
		else validity=true;
		}
		catch(passwordException e)
		{
			System.out.println(e);			
		}
		}
		
		customer c=new customer(name,phone,0,add,email,pass,0);
		cust.add(c);
		return c;
		/*customer thisCustomer=c;
		System.out.println("Account created successfully\n");//go to store menu
		
		accountSettings aS= new accountSettings(thisCustomer);
		delivered_thread = new deliveredThread(0);
		delivered_thread.thisCustomer=thisCustomer;
		aS.displayCustomerDashBoard(thisCustomer);
		aS.settingsmenu(thisCustomer);
		*/
		
	}

	/*
	public void logIn()
	{
		int op=0;
		System.out.println("Enter the following details:\n");
		System.out.println("Email ID:");
		String email=s.next();
		System.out.println("Password:");
		String pass=s.next();
		customer validInfo=check(email,pass);
		if(validInfo.customerEmail!="")
		{
			System.out.println("You are logged in");
			delivered_thread = new deliveredThread(0);
			delivered_thread.thisCustomer=validInfo;
			//accountSettings aS= new accountSettings(aS, email, pass);
			customerDashBoard cDB = new customerDashBoard(validInfo);
			cDB.displayCustomerDashBoard(validInfo);
			//aS.settingsmenu(validInfo);
			//store menu
		}
		else
		{
			
			while(op!=1 || op!=2)
			{
				System.out.println("Wrong email ID/Password\n1)Try Again\n2)Sign Up\n");
				op=s.nextInt();
				switch(op)
				{
					case 1:logIn();
					break;
					case 2:
					signUp();
					break;
					default:
					System.out.println("Please enter a valid option");
				}
			}
		}
	}
	*/

	public customer check(String e,String n)
	{
		customer b;
		customer x;
		b=new customer();
		if(cust.size()==0)
		return b;
		for(int i=0;i<cust.size();i++)
		{
			x=cust.get(i);
			
			if(e.equals(x.customerEmail))
			{			
				
				if(n!="")
				{
					if(n.equals(x.customerPassword))
					{
						b=x;						
						return b;
					}
					else
					{
						b=new customer();						
					}					
				}
				else
				{
					b=x;					
					return b;
				}
			}
			else
			{
				b=new customer();				
			}
		}
		
		return b;
	}

	public void customerPayment(customer thisCustomer)
	{
		//buy()
		buyProducts buyedProd = new buyProducts(thisCustomer);
		buyedProd.buymenu(thisCustomer);

	}
}