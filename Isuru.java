

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
	
	//Withdraw money
	public double withdraw(double ammount) {
        System.out.println("#Account Type : Isuru ");
		System.out.println("#Transaction Type : Withdraw \n");
		setWithdrawalCharge(5.00);
		return ammount;
	}
	
	//Deposite money
	public double deposite(double ammount) {
        System.out.println("#Account Type : Isuru ");
		System.out.println("#Transaction Type : Deposite \n");
		double bonus = (ammount*5.00)/100;
		setBonus(bonus);
		return ammount;
	}
	
	
	
}
