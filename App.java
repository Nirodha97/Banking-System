import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            System.out.println("Logged in as a Manager \n");
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

}