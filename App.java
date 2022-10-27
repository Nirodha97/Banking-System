import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Scanner;

public class App {

    String bankName;
    String bankLocation;

    public static void main(String args[]) {
        App app = new App();
        System.out.println("\n#Banking System Application");
        app.bankDetails();
        app.userLogin();

    }

    public void userLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" 1 - Customer");
        System.out.println(" 2 - Manager");
        System.out.println(" 0 - Exit");
        System.out.print("Choose your login type(1/2/0) : ");

        int loginType = scanner.nextInt();
        if (loginType == 1) {
            System.out.print("\nEnter Username : ");
            String username = scanner.next();
            System.out.print("Enter Password : ");
            String password = scanner.next();
            int cusId = userValidation(username, password);
            if (cusId > 0) {
                System.out.println("Logged in as a Customer \n");
                customerOperations(cusId);
            } else {
                System.out.println("******Invalid Username or Password******\n");
                userLogin();
            }

        } else if (loginType == 2) {
            System.out.print("\nEnter Username : ");
            String username = scanner.next();
            System.out.print("Enter Password : ");
            String password = scanner.next();
            int manId = managerValidation(username, password);
            if (manId > 0) {
                System.out.println("Logged in as a Manager \n");
                managerOperations(manId);
            } else {
                System.out.println("******Invalid Username or Password******\n");
                userLogin();
            }
        } else if (loginType == 0) {
            System.out.println("Exit...");
        } else {
            System.out.println("Invalid login type ");
        }
    }

    // Bank Details
    public void bankDetails() {
        Bank bank = new Bank();
        bank.setBankName("Bank 001");
        bank.setBankLocation("Colombo");
        bankName = bank.getBankName();
        bankLocation = bank.getBankLocation();
        System.out.println("#Bank : " + bankName + "," + bankLocation + "\n");

    }

    public int userValidation(String username, String password) {
        Connection conn = null;
        Statement stmt = null;
        String pw = "";
        int customerId = 0;
        // boolean validation=false;
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
            String query1 = "select id from customer where username ='" + username + "' and password='" + password
                    + "';";
            ResultSet resultSet = stmt.executeQuery(query1);
            while (resultSet.next()) {
                // pw=resultSet.getString("password");
                customerId = resultSet.getInt("id");

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
        return customerId;
    }

    public int managerValidation(String username, String password) {
        Connection conn = null;
        Statement stmt = null;
        String pw = "";
        int managerId = 0;

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
            String query1 = "select id from managers where username ='" + username + "' and password='" + password
                    + "';";
            ResultSet resultSet = stmt.executeQuery(query1);
            while (resultSet.next()) {
                // pw=resultSet.getString("password");
                managerId = resultSet.getInt("id");

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
        return managerId;
    }

    public int getAccountNumber(String type, int customerId) {
        Connection conn = null;
        Statement stmt = null;
        int accountNo = 0;
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
            String query1 = "select id from account where customerId ='" + customerId + "' and type='" + type + "';";
            ResultSet resultSet = stmt.executeQuery(query1);
            while (resultSet.next()) {
                accountNo = resultSet.getInt("id");
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
        return accountNo;
    }

    // Customer Operations
    public void customerOperations(int cusId) {

        String type;
        int accNo;
        Isuru isuru = new Isuru();
        Nirogya nirogya = new Nirogya();
        Scanner scanner = new Scanner(System.in);
        // Choose account type
        System.out.println(" 1 - Isuru Account");
        System.out.println(" 2 - Nirogya Account");
        System.out.println(" 3 - Change Password");
        System.out.println(" 0 - Exit");

        System.out.print("Enter your choice (1/2/3) : ");
        int accountType = scanner.nextInt();
        if (accountType == 1) {
            System.out.println("Selected acoount type : Isuru \n");
            type = "isuru";
            accNo = getAccountNumber(type, cusId);
            isuru.account(type, accNo, cusId);
        } else if (accountType == 2) {
            System.out.println("Selected acoount type : Nirogya \n");
            type = "nirogya";
            accNo = getAccountNumber(type, cusId);
            nirogya.account(type, accNo, cusId);
        } else if (accountType == 3) {
            changePassword(cusId);
        } else if (accountType == 0) {
            System.out.println("Exit \n");
            userLogin();
        } else {
            System.out.println("***************Invalid selection***************\n");
            customerOperations(cusId);
        }

    }

    public void changePassword(int cusId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your current passwrod: ");
        String inputPassword = scanner.next();
        String curPassword = "";
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
            // Get my account balance
            stmt = conn.createStatement();
            String query1 = "select password from customer where id ='" + cusId + "';";
            ResultSet resultSet1 = stmt.executeQuery(query1);
            while (resultSet1.next()) {
                curPassword = resultSet1.getString("password");
            }
            if (inputPassword.equals(curPassword)) {
                System.out.print("Enter the new passwrod: ");
                String newPassword = scanner.next();
                String query2 = "update customer set password = ? where id = ? ;";
                PreparedStatement preparedStmt1 = conn.prepareStatement(query2);
                preparedStmt1.setString(1, newPassword);
                preparedStmt1.setInt(2, cusId);

                // execute the java preparedstatement
                preparedStmt1.executeUpdate();
                System.out.println("Password updated Successfully\n");
                userLogin();
            } else {
                System.out.println("Invalid Password !!!\n");
                changePassword(cusId);
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

    // Manager Operations
    public void managerOperations(int manId) {

        Scanner scanner = new Scanner(System.in);
        System.out.println(" 1 - View customers activity log");
        System.out.println(" 2 - View account details");
        System.out.println(" 3 - View accounts closing requests ");
        System.out.println(" 4 - Close Account ");
        System.out.println(" 0 - Exit");

        System.out.print("Enter your choice (1/2/3/0) : ");
        int operationType = scanner.nextInt();
        if (operationType == 1) {
            viewCustomerActivityLog();
            managerOperations(manId);
        } else if (operationType == 2) {
            viewCustomerAccountDetails();
            managerOperations(manId);
        } else if (operationType == 3) {
            viewCustomerAccountsClosingRequests();
            managerOperations(manId);
        } else if (operationType == 4) {
            closeAccount();
            managerOperations(manId);
        } else if (operationType == 0) {
            System.out.println("Exit \n");
            userLogin();
        } else {
            System.out.println("***************Invalid selection***************\n");
            managerOperations(manId);
        }

    }

    public void viewCustomerActivityLog() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account Number : ");
        int accNo = scanner.nextInt();

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
                    + "'order by id desc ;";

            ResultSet resultSet = stmt.executeQuery(query1);
            System.out.println("\n\t\t\tCustomer Activity Log\n");
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
        }
    }

    public void viewCustomerAccountDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account Number : ");
        int accNo = scanner.nextInt();

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

            String query1 = "select id,type,balance,customerId from account where id ='" + accNo
                    + "';";

            ResultSet resultSet = stmt.executeQuery(query1);
            System.out.println("\n\tCustomer Account Details\n");
            System.out.println("AccountNo\tType\tBalance\tCustomerId");
            System.out.println("");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");
                double balance = resultSet.getDouble("balance");
                int customerId = resultSet.getInt("customerId");
                System.out.println(id + "\t\t" + type
                        + "\t" + balance + "\t\t" + customerId);

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
        }
    }

    public void viewCustomerAccountsClosingRequests() {
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

            String query1 = "select customerId,accountNo,description,date,time from accounts_closing_requests;";

            ResultSet resultSet = stmt.executeQuery(query1);
            System.out.println("\n\tCustomer Account Closing Requests\n");
            System.out.println("Date\t\tTime\t\tCustomerId\tAccountNo\tdescription");
            System.out.println("");
            while (resultSet.next()) {
                int customerId = resultSet.getInt("customerId");
                int accountNo = resultSet.getInt("accountNo");
                String description = resultSet.getString("description");
                Time time = resultSet.getTime("time");
                Date date = resultSet.getDate("date");
                System.out.println(date + "\t" + time
                        + "\t\t" + customerId + "\t\t" + accountNo + "\t" + description);

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
        }
    }

    public void closeAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the account number to close account : ");
        int accNo = scanner.nextInt();
        System.out.print("Are you sure you want to delete accout " + accNo + "? (y/n)) : ");
        String del = scanner.next();
        if (del.equalsIgnoreCase("y")) {

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

                String query1 = "DELETE FROM account WHERE id='" + accNo + "';";

                stmt.executeUpdate(query1);
                System.out.println("Account " + accNo + " Successfully Deleted...");
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
            }
        }
    }

}