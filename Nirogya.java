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

public class Nirogya extends Account {

   private double bonus;
   private double withdrawalCharge;

   public Nirogya() {
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

   public void account(String type, int accNo, int cusId) {
      Scanner scanner = new Scanner(System.in);
      // Choose operation type
      System.out.println(" 1 - Deposite");
      System.out.println(" 2 - Withdraw");
      System.out.println(" 3 - Check Balance");
      System.out.println(" 4 - Transfer Money");
      System.out.println(" 5 - View Activity Log");
      System.out.println(" 0 - Exit");
      System.out.print("Enter your choise (1/2/3/4/0) : ");

      int operationType = scanner.nextInt();
      switch (operationType) {
         case 1:
            System.out.print("Deposite Ammount : ");
            double depositeAmount = scanner.nextDouble();
            deposite(depositeAmount, type, accNo, cusId);
            break;

         case 2:
            System.out.print("Withdraw Ammount : ");
            double withdrawAmount = scanner.nextDouble();
            withdraw(withdrawAmount, type, accNo, cusId);
            break;
         case 3:
            System.out.println("");
            checkbalance(type, accNo, cusId);
            break;
         case 4:
            System.out.println("");
            transferMoney(type, accNo, cusId);
            break;
         case 5:
            System.out.println("");
            viewActivityLog(type, accNo, cusId);
            break;
         case 0:
            System.out.println("");
            break;
         default:
            System.out.println("***************Invalid selection***************");
      }
   }

   // Deposite money
   public void deposite(double depositeAmount, String type, int accNo, int cusId) {
      Connection conn = null;
      Statement stmt = null;
      double balance = 0;
      double bonus = (depositeAmount * 10.00) / 100;
      setBonus(bonus);
      try {
         try {
            Class.forName("com.mysql.jdbc.Driver");
         } catch (Exception e) {
            System.out.println(e);
         }
         conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/banksystem?characterEncoding=latin1&autoReconnect=true&useSSL=false&useTimezone=true&serverTimezone=UTC",
               "root", "Nirodha@225");
         // System.out.println("Connection is created successfully:");
         stmt = conn.createStatement();
         int accountNo = accNo;
         String transactionType = "deposite";
         double amount = depositeAmount + getBonus();
         String description = "";
         Calendar calendar = Calendar.getInstance();
         Date date = new Date(calendar.getTime().getTime());
         Time time = new Time(calendar.getTime().getTime());

         // Set bonus

         // System.out.println(date);

         String query1 = "insert into transaction (accountNo, type, amount, description, date,time)"
               + " values (?, ?, ?, ?, ?,?);";

         // create the mysql insert preparedstatement
         PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
         preparedStmt1.setInt(1, accountNo);
         preparedStmt1.setString(2, transactionType);
         preparedStmt1.setDouble(3, amount);
         preparedStmt1.setString(4, description);
         preparedStmt1.setDate(5, date);
         preparedStmt1.setTime(6, time);

         // execute the preparedstatement
         preparedStmt1.execute();

         ////////////////////////////////////// Get
         ////////////////////////////////////// balance////////////////////////////////////////

         stmt = conn.createStatement();
         String query2 = "select balance from account where id ='" + accNo + "';";
         ResultSet resultSet = stmt.executeQuery(query2);
         while (resultSet.next()) {
            balance = resultSet.getDouble("balance");
            // System.out.println(balance);
         }

         balance = balance + amount + getBonus();
         //////////////////////////////////////// Update account
         //////////////////////////////////////// balance////////////////////////////////
         String query3 = "update account set balance = ? where id = ? ;";
         PreparedStatement preparedStmt2 = conn.prepareStatement(query3);
         preparedStmt2.setDouble(1, balance);
         preparedStmt2.setInt(2, accNo);

         // execute the java preparedstatement
         preparedStmt2.executeUpdate();

         // System.out.println("Current Balance : "+balance+"\n");
      } catch (SQLException excep) {
         excep.printStackTrace();
      } catch (Exception excep) {
         excep.printStackTrace();
      } finally {
         try {
            if (stmt != null)
               conn.close();
         } catch (SQLException se) {
         }
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
      System.out.println("|   Account Type : Nirogya       |");
      System.out.println("|   Transaction Type : Deposite  |");
      System.out.println("|   Deposite Ammount : " + depositeAmount + "     |");
      System.out.println("|   Bonus : " + getBonus() + "                 |");
      // System.out.println("| Account Balance :
      // "+(balance+depositeAmount+getBonus())+" |");
      System.out.println("|                                |");
      System.out.println("|--------------------------------|\n");
      account("nirogya", accNo, cusId);
   }

   // Withdraw money
   public void withdraw(double withdrawAmount, String type, int accNo, int cusId) {
      Connection conn = null;
      Statement stmt = null;
      double balance = 0;
      setWithdrawalCharge(5.00);
      try {
         try {
            Class.forName("com.mysql.jdbc.Driver");
         } catch (Exception e) {
            System.out.println(e);
         }
         conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/banksystem?characterEncoding=latin1&autoReconnect=true&useSSL=false&useTimezone=true&serverTimezone=UTC",
               "root", "Nirodha@225");
         // System.out.println("Connection is created successfully:");
         stmt = conn.createStatement();
         int accountNo = accNo;
         String transactionType = "withdraw";
         double amount = withdrawAmount + getBonus();
         String description = "";
         Calendar calendar = Calendar.getInstance();
         Date date = new Date(calendar.getTime().getTime());
         Time time = new Time(calendar.getTime().getTime());

         // Set bonus

         // System.out.println(date);

         String query1 = "insert into transaction (accountNo, type, amount, description, date,time)"
               + " values (?, ?, ?, ?, ?,?);";

         // create the mysql insert preparedstatement
         PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
         preparedStmt1.setInt(1, accountNo);
         preparedStmt1.setString(2, transactionType);
         preparedStmt1.setDouble(3, amount);
         preparedStmt1.setString(4, description);
         preparedStmt1.setDate(5, date);
         preparedStmt1.setTime(6, time);

         // execute the preparedstatement
         preparedStmt1.execute();

         ////////////////////////////////////// Get
         ////////////////////////////////////// balance////////////////////////////////////////

         stmt = conn.createStatement();
         String query2 = "select balance from account where id ='" + accNo + "';";
         ResultSet resultSet = stmt.executeQuery(query2);
         while (resultSet.next()) {
            balance = resultSet.getDouble("balance");
            // System.out.println(balance);
         }

         balance = balance - amount - getWithdrawalCharge();
         //////////////////////////////////////// Update account
         //////////////////////////////////////// balance////////////////////////////////
         String query3 = "update account set balance = ? where id = ? ;";
         PreparedStatement preparedStmt2 = conn.prepareStatement(query3);
         preparedStmt2.setDouble(1, balance);
         preparedStmt2.setInt(2, accNo);

         // execute the java preparedstatement
         preparedStmt2.executeUpdate();

         // System.out.println("Current Balance : "+balance+"\n");
      } catch (SQLException excep) {
         excep.printStackTrace();
      } catch (Exception excep) {
         excep.printStackTrace();
      } finally {
         try {
            if (stmt != null)
               conn.close();
         } catch (SQLException se) {
         }
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
      System.out.println("|   Account Type : Nirogya       |");
      System.out.println("|   Transaction Type : Withdraw  |");
      System.out.println("|   Withdraw Ammount : " + withdrawAmount + "     |");
      System.out.println("|   Charge : " + getWithdrawalCharge() + "                 |");
      // System.out.println("| Account Balance :
      // "+(balance+depositeAmount+getBonus())+" |");
      System.out.println("|                                |");
      System.out.println("|--------------------------------|\n");
      account("nirogya", accNo, cusId);
   }

   // Check balance
   public void checkbalance(String type, int accNo, int cusId) {

      Connection conn = null;
      Statement stmt = null;
      double balance = 0;

      try {
         try {
            Class.forName("com.mysql.jdbc.Driver");
         } catch (Exception e) {
            System.out.println(e);
         }
         conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/banksystem?characterEncoding=latin1&autoReconnect=true&useSSL=false&useTimezone=true&serverTimezone=UTC",
               "root", "Nirodha@225");
         // System.out.println("Connection is created successfully:");
         stmt = conn.createStatement();
         String query1 = "select balance from account where id ='" + accNo + "';";
         ResultSet resultSet = stmt.executeQuery(query1);
         while (resultSet.next()) {
            balance = resultSet.getDouble("balance");
            // System.out.println(balance);
         }
         System.out.println("\n|--------------------------------|");
         System.out.println("|                                |");
         System.out.println("|        Bank Statement          |");
         System.out.println("|                                |");
         System.out.println("|   #Account Type : Nirogya      |");
         System.out.println("|   Current Balance : " + balance + "     |");
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
         } catch (SQLException se) {
         }
         try {
            if (conn != null)
               conn.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
         account("nirogya", accNo, cusId);
      }

   }

   public void transferMoney(String type, int accNo, int cusId) {

      Scanner scanner = new Scanner(System.in);
      // Choose money transfer type
      System.out.println("Select Tranfer Type ");
      System.out.println(" 1 - Own account transfer");
      System.out.println(" 2 - Third party transfer");
      System.out.println(" 0 - Exit");
      System.out.print("Enter your choise (1/2/0) : ");

      int transferType = scanner.nextInt();
      System.out.print("Enter the account Number : ");
      int transAccNo = scanner.nextInt();
      System.out.print("Enter the amount : ");
      int transAmount = scanner.nextInt();
      if (transferType == 1) {
         ownAccountTransfer(accNo, transAccNo, transAmount);
         account(type, accNo, cusId);
      } else if (transferType == 2) {
         thirdPartyAccountTransfer(accNo, transAccNo, transAmount);
         account(type, accNo, cusId);
      } else if (transferType == 0) {
         account(type, accNo, cusId);
      } else {
         System.out.println("Invalid selection");
      }

   }

   public void ownAccountTransfer(int myAcc, int transAccNo, double transAmount) {
      Connection conn = null;
      Statement stmt = null;
      double mybalance = 0;
      double transAccBalance = 0;
      try {
         try {
            Class.forName("com.mysql.jdbc.Driver");
         } catch (Exception e) {
            System.out.println(e);
         }
         conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/banksystem?characterEncoding=latin1&autoReconnect=true&useSSL=false&useTimezone=true&serverTimezone=UTC",
               "root", "Nirodha@225");
         // Get my account balance

         stmt = conn.createStatement();
         String query1 = "select balance from account where id ='" + myAcc + "';";
         ResultSet resultSet1 = stmt.executeQuery(query1);
         while (resultSet1.next()) {
            mybalance = resultSet1.getDouble("balance");
         }
         // System.out.println("My account balance: " + mybalance);

         // Get transfer account balance

         stmt = conn.createStatement();
         String query2 = "select balance from account where id ='" + transAccNo + "';";
         ResultSet resultSet2 = stmt.executeQuery(query2);
         while (resultSet2.next()) {
            transAccBalance = resultSet2.getDouble("balance");
         }
         // System.out.println("My account balance: " + transAccBalance);

         if (transAmount > mybalance) {
            System.out.println("You have not sufficent money..");
            System.out.println("Transaction declined...");
         } else {
            try {
               // Update my account balance
               mybalance = mybalance - transAmount;
               String query3 = "update account set balance = ? where id = ? ;";
               PreparedStatement preparedStmt1 = conn.prepareStatement(query3);
               preparedStmt1.setDouble(1, mybalance);
               preparedStmt1.setInt(2, myAcc);

               // execute the java preparedstatement
               preparedStmt1.executeUpdate();
               try {
                  // Update trans account balance
                  transAccBalance = transAccBalance + transAmount;
                  String query4 = "update account set balance = ? where id = ? ;";
                  PreparedStatement preparedStmt2 = conn.prepareStatement(query4);
                  preparedStmt2.setDouble(1, transAccBalance);
                  preparedStmt2.setInt(2, transAccNo);

                  // execute the java preparedstatement
                  preparedStmt2.executeUpdate();

                  try {

                     int accountNo1 = myAcc;
                     String transactionType1 = "Transfer money";
                     int accountNo2 = transAccNo;
                     String transactionType2 = "Receive money";
                     double amount = transAmount;
                     String description = "";
                     Calendar calendar = Calendar.getInstance();
                     Date date = new Date(calendar.getTime().getTime());
                     Time time = new Time(calendar.getTime().getTime());

                     // My account transaction details
                     String query5 = "insert into transaction (accountNo, type, amount, description, date,time)"
                           + " values (?, ?, ?, ?, ?,?);";

                     // create the mysql insert preparedstatement
                     PreparedStatement preparedStmt3 = conn.prepareStatement(query5);
                     preparedStmt3.setInt(1, accountNo1);
                     preparedStmt3.setString(2, transactionType1);
                     preparedStmt3.setDouble(3, amount);
                     preparedStmt3.setString(4, description);
                     preparedStmt3.setDate(5, date);
                     preparedStmt3.setTime(6, time);

                     // execute the preparedstatement
                     preparedStmt3.execute();

                     // Transfer account transaction details
                     String query6 = "insert into transaction (accountNo, type, amount, description, date,time)"
                           + " values (?, ?, ?, ?, ?,?);";

                     // create the mysql insert preparedstatement
                     PreparedStatement preparedStmt4 = conn.prepareStatement(query6);
                     preparedStmt4.setInt(1, accountNo2);
                     preparedStmt4.setString(2, transactionType2);
                     preparedStmt4.setDouble(3, amount);
                     preparedStmt4.setString(4, description);
                     preparedStmt4.setDate(5, date);
                     preparedStmt4.setTime(6, time);

                     // execute the preparedstatement
                     preparedStmt4.execute();

                     System.out.println("\n|--------------------------------|");
                     System.out.println("|                                |");
                     System.out.println("|        Bank Statement          |");
                     System.out.println("|                                |");
                     System.out.println("|   Account Number : " + transAccNo + "           |");
                     System.out.println("|   Account type : Isuru         |");
                     System.out.println("|   Transfered Ammount : " + transAmount + "  |");
                     System.out.println("|                                |");
                     System.out.println("|--------------------------------|\n");
                  } catch (Exception excep) {
                     excep.printStackTrace();
                  }
               } catch (Exception excep) {
                  excep.printStackTrace();
               }
            } catch (Exception excep) {
               excep.printStackTrace();
            }
         }

      } catch (SQLException excep) {
         excep.printStackTrace();
      } catch (Exception excep) {
         excep.printStackTrace();
      } finally {
         try {
            if (stmt != null)
               conn.close();
         } catch (SQLException se) {
         }
         try {
            if (conn != null)
               conn.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }

      }

   }

   // Thirdpart account transfer
   public void thirdPartyAccountTransfer(int myAcc, int transAccNo, double transAmount) {
      Connection conn = null;
      Statement stmt = null;
      double mybalance = 0;
      double transAccBalance = 0;
      double serviceCharge = 50.0;
      try {
         try {
            Class.forName("com.mysql.jdbc.Driver");
         } catch (Exception e) {
            System.out.println(e);
         }
         conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/banksystem?characterEncoding=latin1&autoReconnect=true&useSSL=false&useTimezone=true&serverTimezone=UTC",
               "root", "Nirodha@225");
         // Get my account balance
         stmt = conn.createStatement();
         String query1 = "select balance from account where id ='" + myAcc + "';";
         ResultSet resultSet1 = stmt.executeQuery(query1);
         while (resultSet1.next()) {
            mybalance = resultSet1.getDouble("balance");
         }
         // System.out.println("My account balance: " + mybalance);

         // Get transfer account balance

         stmt = conn.createStatement();
         String query2 = "select balance from account where id ='" + transAccNo + "';";
         ResultSet resultSet2 = stmt.executeQuery(query2);
         while (resultSet2.next()) {
            transAccBalance = resultSet2.getDouble("balance");
         }
         // System.out.println("My account balance: " + transAccBalance);

         if ((transAmount + serviceCharge) > mybalance) {
            System.out.println("You have not sufficent money..");
            System.out.println("Transaction declined...");
         } else {
            try {
               // Update my account balance
               mybalance = mybalance - transAmount - serviceCharge;
               String query3 = "update account set balance = ? where id = ? ;";
               PreparedStatement preparedStmt1 = conn.prepareStatement(query3);
               preparedStmt1.setDouble(1, mybalance);
               preparedStmt1.setInt(2, myAcc);

               // execute the java preparedstatement
               preparedStmt1.executeUpdate();
               try {
                  // Update trans account balance
                  transAccBalance = transAccBalance + transAmount;
                  String query4 = "update account set balance = ? where id = ? ;";
                  PreparedStatement preparedStmt2 = conn.prepareStatement(query4);
                  preparedStmt2.setDouble(1, transAccBalance);
                  preparedStmt2.setInt(2, transAccNo);

                  // execute the java preparedstatement
                  preparedStmt2.executeUpdate();

                  try {

                     int accountNo1 = myAcc;
                     String transactionType1 = "Transfer money";
                     int accountNo2 = transAccNo;
                     String transactionType2 = "Receive money";
                     double amount1 = transAmount + serviceCharge;
                     double amount2 = transAmount;
                     String description = "";
                     Calendar calendar = Calendar.getInstance();
                     Date date = new Date(calendar.getTime().getTime());
                     Time time = new Time(calendar.getTime().getTime());

                     // My account transaction details
                     String query5 = "insert into transaction (accountNo, type, amount, description, date,time)"
                           + " values (?, ?, ?, ?, ?,?);";

                     // create the mysql insert preparedstatement
                     PreparedStatement preparedStmt3 = conn.prepareStatement(query5);
                     preparedStmt3.setInt(1, accountNo1);
                     preparedStmt3.setString(2, transactionType1);
                     preparedStmt3.setDouble(3, amount1);
                     preparedStmt3.setString(4, description);
                     preparedStmt3.setDate(5, date);
                     preparedStmt3.setTime(6, time);

                     // execute the preparedstatement
                     preparedStmt3.execute();

                     // Transfer account transaction details
                     String query6 = "insert into transaction (accountNo, type, amount, description, date,time)"
                           + " values (?, ?, ?, ?, ?,?);";

                     // create the mysql insert preparedstatement
                     PreparedStatement preparedStmt4 = conn.prepareStatement(query6);
                     preparedStmt4.setInt(1, accountNo2);
                     preparedStmt4.setString(2, transactionType2);
                     preparedStmt4.setDouble(3, amount2);
                     preparedStmt4.setString(4, description);
                     preparedStmt4.setDate(5, date);
                     preparedStmt4.setTime(6, time);

                     // execute the preparedstatement
                     preparedStmt4.execute();

                     System.out.println("\n|--------------------------------|");
                     System.out.println("|                                |");
                     System.out.println("|        Bank Statement          |");
                     System.out.println("|                                |");
                     System.out.println("|   Account Number : " + transAccNo + "           |");
                     // System.out.println("| Account type : Nirogya |");
                     System.out.println("|   Transfered Ammount : " + transAmount + "  |");
                     System.out.println("|   Service Charge : " + serviceCharge + "  |");
                     System.out.println("|                                |");
                     System.out.println("|--------------------------------|\n");
                  } catch (Exception excep) {
                     excep.printStackTrace();
                  }
               } catch (Exception excep) {
                  excep.printStackTrace();
               }
            } catch (Exception excep) {
               excep.printStackTrace();
            }
         }

      } catch (SQLException excep) {
         excep.printStackTrace();
      } catch (Exception excep) {
         excep.printStackTrace();
      } finally {
         try {
            if (stmt != null)
               conn.close();
         } catch (SQLException se) {
         }
         try {
            if (conn != null)
               conn.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }

      }

   }

   public void viewActivityLog(String type, int accNo, int cusId) {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Select criteria ");
      System.out.println(" 1 - Past 7 days");
      System.out.println(" 2 - Past 30 days");
      // System.out.println(" 0 - Exit");
      System.out.print("Enter your choise (1/2) : ");

      int input = scanner.nextInt();
      int duration = 0;
      if (input == 1) {
         duration = 7;
      } else if (input == 2) {
         duration = 30;
      }
      Connection conn = null;
      Statement stmt = null;
      try {
         try {
            Class.forName("com.mysql.jdbc.Driver");
         } catch (Exception e) {
            System.out.println(e);
         }
         conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/banksystem?characterEncoding=latin1&autoReconnect=true&useSSL=false&useTimezone=true&serverTimezone=UTC",
               "root", "Nirodha@225");

         stmt = conn.createStatement();

         String query1 = "select time,date,description,type,amount from transaction where accountNo ='" + accNo
               + "'order by id desc limit " + duration + ";";

         ResultSet resultSet = stmt.executeQuery(query1);
         System.out.println("\n\t\t\t\tBank Statement\n");
         System.out.println("Time\t\tDate\t\tTransaction Type\tAmount");
         System.out.println("");
         while (resultSet.next()) {
            Time time = resultSet.getTime("time");
            Date date = resultSet.getDate("date");
            String transactionType = resultSet.getString("type");
            double amount = resultSet.getDouble("amount");
            System.out.println(time + "\t" + date
                  + "\t" + transactionType + "\t\t" + amount);

         }
         System.out.println("\n");

      } catch (SQLException excep) {
         excep.printStackTrace();
      } catch (Exception excep) {
         excep.printStackTrace();
      } finally {
         try {
            if (stmt != null)
               conn.close();
         } catch (SQLException se) {
         }
         try {
            if (conn != null)
               conn.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
         account("nirogya", accNo, cusId);
      }
   }

}
