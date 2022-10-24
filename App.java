
public class App{

    String bankName;
    String bankLocation;
    public static void main(String args[]){
        App app = new App();
        System.out.println("\n        Bank Statement          ");
        System.out.println("\n#Banking System Application");
       // Scanner scanner= new Scanner(System.in);
        app.bankDetails();

        
        //app.depositeIsuru();
		//app.withdrawIsuru();
		app.depositeNirogya();
		//app.withdrawNirogya();
        
        


    }

    public void bankDetails(){
        Bank bank = new Bank();
        bank.setBankName("Bank 001");
        bank.setBankLocation("Colombo");
        bankName=bank.getBankName();
        bankLocation=bank.getBankLocation();
        System.out.println("#Bank : "+bankName+","+bankLocation+"\n");

    }

    	//Isuru
	public void depositeIsuru() {
		Isuru isuru = new Isuru();
		double depositeAmount= isuru.deposite(1500);
		double bonus = isuru.getBonus();
		System.out.println("Deposite Ammount : "+depositeAmount);
		System.out.println("Deposite Bonus : "+bonus);
	}
	
	public void withdrawIsuru() {
		Isuru isuru = new Isuru();
		double withdrawAmount= isuru.withdraw(1500);
		double charge = isuru.getWithdrawalCharge();
		System.out.println("Withdrawal Ammount : "+withdrawAmount);
		System.out.println("Withdrawal Charge : "+charge);
	}
	
	
	//Nirogya
	public void depositeNirogya() {
		Nirogya nirogya = new Nirogya();
		double depositeAmount= nirogya.deposite(500);
		double bonus = nirogya.getBonus();
		System.out.println("Deposite Ammount : "+depositeAmount);
		System.out.println("Deposite Bonus : "+bonus);
	}
	
	public void withdrawNirogya() {
		Nirogya nirogya = new Nirogya();
		double withdrawAmount= nirogya.withdraw(5000);
		double charge = nirogya.getWithdrawalCharge();
		System.out.println("Withdrawal Ammount : "+withdrawAmount);
		System.out.println("Withdrawal Charge : "+charge);
	}
}