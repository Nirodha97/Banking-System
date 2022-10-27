import java.lang.String;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Calendar;
import java.util.Scanner;


public class Isuru extends Account{

	private double bonus;
	private double withdrawalCharge;
	
     	
	
	public Isuru() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public double getBonus() {
		return bonus;
	}
	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
	public double getWithdrawalCharge() {
		return withdrawalCharge;
	}
	public void setWithdrawalCharge(double withdrawalCharge) {
		this.withdrawalCharge = withdrawalCharge;
	}

    public void account(String type,int accNo,int cusId){
        Scanner scanner= new Scanner(System.in);
        //Choose operation type
        System.out.println(" 1 - Deposite");
        System.out.println(" 2 - Withdraw"); 
        System.out.println(" 3 - Check Balance"); 
        System.out.println(" 4 - Transfer Money");
        System.out.println(" 0 - Exit");
        System.out.print("Enter your choise (1/2/3/0) : ");
       
       int operationType = scanner.nextInt();
       switch(operationType){
           case 1 : System.out.print("Deposite Ammount : ");
                    double depositeAmount= scanner.nextDouble();
                    deposite(depositeAmount,type,accNo,cusId);
                    break;

           case 2 : System.out.print("Withdraw Ammount : ");
                    double withdrawAmount= scanner.nextDouble();
                    withdraw(withdrawAmount,type,accNo,cusId);
                    break;
           case 3 : System.out.println("");
                    checkbalance(type,accNo,cusId);
                    break;
           case 4 : System.out.println("");
                   // transferMoney(type,accNo,cusId);
                    break;                   
           case 0 : System.out.println("");
                    break;
           default: System.out.println("***************Invalid selection***************"); 
       }
    }

    public void dbConnection(){
        Connection conn = null;
        Statement stmt = null;
        try {
            try {
               Class.forName("com.mysql.jdbc.Driver");
            } catch (Exception e) {
               System.out.println(e);
         }
         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/banksystem?characterEncoding=latin1&autoReconnect=true&useSSL=false&useTimezone=true&serverTimezone=UTC", "root", "Nirodha@225");
         System.out.println("Connection is created successfully:");
         } catch (SQLException excep) {
            excep.printStackTrace();
         } catch (Exception excep) {
            excep.printStackTrace();
         } finally {
            try {
               if (stmt != null)
                  conn.close();
            } catch (SQLException se) {}
            try {
               if (conn != null)
                  conn.close();
            } catch (SQLException se) {
               se.printStackTrace();
            }  
         }
    }

    //Deposite money
    public void deposite(double depositeAmount,String type,int accNo,int cusId) {
      Connection conn = null;
      Statement stmt = null;
      double balance=0;
      double bonus = (depositeAmount*5.00)/100;
      setBonus(bonus);
      try {
          try {
             Class.forName("com.mysql.jdbc.Driver");
          } catch (Exception e) {
             System.out.println(e);
       }
       conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/banksystem?characterEncoding=latin1&autoReconnect=true&useSSL=false&useTimezone=true&serverTimezone=UTC", "root", "Nirodha@225");
       //System.out.println("Connection is created successfully:");
       stmt = conn.createStatement();
       int accountNo=accNo;
       String transactionType="deposite";
       double amount=depositeAmount+getBonus();
       String description="";
       Calendar calendar = Calendar.getInstance();
       Date date = new Date(calendar.getTime().getTime());
       Time time = new Time(calendar.getTime().getTime());

       //Set bonus

       //System.out.println(date);

       String query1 = "insert into transaction (accountNo, type, amount, description, date,time)"
       + " values (?, ?, ?, ?, ?,?);";
       
       // create the mysql insert preparedstatement
      PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
      preparedStmt1.setInt (1, accountNo);
      preparedStmt1.setString(2, transactionType);
      preparedStmt1.setDouble(3, amount);
      preparedStmt1.setString(4, description);
      preparedStmt1.setDate (5, date);
      preparedStmt1.setTime(6, time);

      // execute the preparedstatement
      preparedStmt1.execute();
      
      //System.out.println(query1);

      //////////////////////////////////////Get balance////////////////////////////////////////

         stmt = conn.createStatement();
         String query2 = "select balance from account where id ='"+accNo+"';";
       //  System.out.println(query2);
         ResultSet resultSet = stmt.executeQuery(query2);
         while (resultSet.next()) {
            balance=resultSet.getDouble("balance");
            //System.out.println(balance);
         }


      balance=balance+amount+getBonus();
      ////////////////////////////////////////Update account balance////////////////////////////////
      String query3 = "update account set balance = ? where id = ? ;";
     // System.out.println(query3);
      PreparedStatement preparedStmt2 = conn.prepareStatement(query3);
      preparedStmt2.setDouble(1, balance);
      preparedStmt2.setInt(2, accNo);

      // execute the java preparedstatement
      preparedStmt2.executeUpdate();

      //System.out.println("Current Balance : "+balance+"\n");
       } catch (SQLException excep) {
          excep.printStackTrace();
       } catch (Exception excep) {
          excep.printStackTrace();
       } finally {
          try {
             if (stmt != null)
                conn.close();
          } catch (SQLException se) {}
          try {
             if (conn != null)
                conn.close();
          } catch (SQLException se) {
             se.printStackTrace();
          }  
         
       }



               

            System.out.println("\n|--------------------------------|");
            System.out.println("|                                |");
            System.out.println("|        Bank Statement          |");
            System.out.println("|                                |");
            System.out.println("|   Account Type : Isuru         |");
            System.out.println("|   Transaction Type : Deposite  |");
            System.out.println("|   Deposite Ammount : "+depositeAmount+"     |");
            System.out.println("|   Bonus : "+getBonus()+"                 |");
            //System.out.println("|   Account Balance : "+(balance+depositeAmount+getBonus())+"     |");
            System.out.println("|                                |");
            System.out.println("|--------------------------------|\n");
            account("isuru",accNo,cusId);	
	}
	

       //Withdraw money
       public void withdraw(double withdrawAmount,String type,int accNo, int cusId) {
         Connection conn = null;
         Statement stmt = null;
         double balance=0;
         setWithdrawalCharge(5.00);
         try {
             try {
                Class.forName("com.mysql.jdbc.Driver");
             } catch (Exception e) {
                System.out.println(e);
          }
          conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/banksystem?characterEncoding=latin1&autoReconnect=true&useSSL=false&useTimezone=true&serverTimezone=UTC", "root", "Nirodha@225");
          //System.out.println("Connection is created successfully:");
          stmt = conn.createStatement();
          int accountNo=accNo;
          String transactionType="withdraw";
          double amount=withdrawAmount+getBonus();
          String description="";
          Calendar calendar = Calendar.getInstance();
          Date date = new Date(calendar.getTime().getTime());
          Time time = new Time(calendar.getTime().getTime());
   
          //Set bonus
   
          //System.out.println(date);
         // System.out.println("accNo : "+accNo);
          String query1 = "insert into transaction (accountNo, type, amount, description, date,time)"
          + " values (?, ?, ?, ?, ?,?);";
          
          // create the mysql insert preparedstatement
         PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
         preparedStmt1.setInt (1, accountNo);
         preparedStmt1.setString(2, transactionType);
         preparedStmt1.setDouble(3, amount);
         preparedStmt1.setString(4, description);
         preparedStmt1.setDate (5, date);
         preparedStmt1.setTime(6, time);
   
         // execute the preparedstatement
         preparedStmt1.execute();
         
   
   
         //////////////////////////////////////Get balance////////////////////////////////////////
   
            stmt = conn.createStatement();
            String query2 = "select balance from account where id ='"+accNo+"';";
            ResultSet resultSet = stmt.executeQuery(query2);
            while (resultSet.next()) {
               balance=resultSet.getDouble("balance");
               //System.out.println(balance);
            }
           // System.out.println("accNo : "+accNo);
   
         balance=balance-amount-getWithdrawalCharge();
         ////////////////////////////////////////Update account balance////////////////////////////////
         String query3 = "update account set balance = ? where id = ? ;";
         PreparedStatement preparedStmt2 = conn.prepareStatement(query3);
         preparedStmt2.setDouble(1, balance);
         preparedStmt2.setInt(2, accNo);
   
         // execute the java preparedstatement
         preparedStmt2.executeUpdate();
   
         //System.out.println("Current Balance : "+balance+"\n");
          } catch (SQLException excep) {
             excep.printStackTrace();
          } catch (Exception excep) {
             excep.printStackTrace();
          } finally {
             try {
                if (stmt != null)
                   conn.close();
             } catch (SQLException se) {}
             try {
                if (conn != null)
                   conn.close();
             } catch (SQLException se) {
                se.printStackTrace();
             }  
            
          }
   
   
   
                  
   
               System.out.println("\n|--------------------------------|");
               System.out.println("|                                |");
               System.out.println("|        Bank Statement          |");
               System.out.println("|                                |");
               System.out.println("|   Account Type : Isuru         |");
               System.out.println("|   Transaction Type : Withdraw  |");
               System.out.println("|   Withdraw Ammount : "+withdrawAmount+"     |");
               System.out.println("|   Charge : "+getWithdrawalCharge()+"                 |");
               //System.out.println("|   Account Balance : "+(balance+depositeAmount+getBonus())+"     |");
               System.out.println("|                                |");
               System.out.println("|--------------------------------|\n");
               account("isuru",accNo,cusId);	
      }

    //Check balance
    public void checkbalance(String type,int accNo,int cusId){

        Connection conn = null;
        Statement stmt = null;
        double balance=0;
       
        try {
            try {
               Class.forName("com.mysql.jdbc.Driver");
            } catch (Exception e) {
               System.out.println(e);
         }
         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/banksystem?characterEncoding=latin1&autoReconnect=true&useSSL=false&useTimezone=true&serverTimezone=UTC", "root", "Nirodha@225");
         //System.out.println("Connection is created successfully:");
         stmt = conn.createStatement();
         //System.out.println("accNo : "+accNo);
         String query1 = "select balance from account where id ='"+accNo+"';";
        // System.out.println(query1);
         ResultSet resultSet = stmt.executeQuery(query1);
         while (resultSet.next()) {
            balance=resultSet.getDouble("balance");
            //System.out.println(balance);
         }
         System.out.println("\n|--------------------------------|");
         System.out.println("|                                |");
         System.out.println("|        Bank Statement          |");
         System.out.println("|                                |");
         System.out.println("|   #Account Type : Isuru        |");
         System.out.println("|   Current Balance : "+balance+"     |");
         System.out.println("|                                |");
         System.out.println("|--------------------------------|\n");

         } catch (SQLException excep) {
            excep.printStackTrace();
         } catch (Exception excep) {
            excep.printStackTrace();
         } finally {
            try {
               if (stmt != null)
                  conn.close();
            } catch (SQLException se) {}
            try {
               if (conn != null)
                  conn.close();
            } catch (SQLException se) {
               se.printStackTrace();
            }  
            account("isuru",accNo,cusId);
         }
         
    }



    
	

	
	
	
}
