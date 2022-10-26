import java.util.Scanner;

public class App{

    String bankName;
    String bankLocation;
    public static void main(String args[]){
        App app = new App();
        System.out.println("\n#Banking System Application");
        app.bankDetails();
        app.userLogin();
    
        
    }

    

    public void userLogin(){
        Scanner scanner= new Scanner(System.in);
        System.out.println(" 1 - Customer");
        System.out.println(" 2 - Manager"); 
        System.out.print("Choose your login type(1/2) : ");

        int loginType= scanner.nextInt();
        if(loginType==1){
            System.out.println("Logged in as a Customer \n");
            customerOperations();
        }else if(loginType==2){
            System.out.println("Logged in as a Manager \n");
        }else{
            System.out.println("Invalid login type ");
        }
    }

    //Bank Details
    public void bankDetails(){
        Bank bank = new Bank();
        bank.setBankName("Bank 001");
        bank.setBankLocation("Colombo");
        bankName=bank.getBankName();
        bankLocation=bank.getBankLocation();
        System.out.println("#Bank : "+bankName+","+bankLocation+"\n");

    }

    //Customer Operations
    public void customerOperations(){
        
        String type;
        Isuru isuru = new Isuru();
        Nirogya nirogya = new Nirogya();
        Scanner scanner= new Scanner(System.in);
        //Choose account type
        System.out.println(" 1 - Isuru");
        System.out.println(" 2 - Nirogya"); 
        System.out.println(" 0 - Exit");
         
        System.out.print("Enter your account type (1/2) : ");
        int accountType = scanner.nextInt();
        if(accountType==1){
            System.out.println("Selected acoount type : Isuru \n");
            type="isuru";
            isuru.account(type);
        }else if(accountType==2){
            System.out.println("Selected acoount type : Nirogya \n");
            type="nirogya";
            nirogya.account(type);
        }else if(accountType==0){
            System.out.println("Exit \n");
            userLogin();
        }else{
            System.out.println("***************Invalid selection***************");
            customerOperations();
        }
       
    }
}