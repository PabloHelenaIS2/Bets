package domain;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class RegularUser extends User {

	private float wallet;
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//@XmlIDREF

	private double moneyWon;

//@OneToMany(cascade = CascadeType.REMOVE)
	private ArrayList<Bet> bets = new ArrayList<Bet>();
	
	public RegularUser() {
		super();
	}

	public RegularUser(String userName, String userPass, String firstName, String lastName, String userId, Date birthDate,
			String email, String bankAccount, Integer phoneNumber, String address) {
		super(userName, userPass, firstName, lastName, userId, birthDate, email, bankAccount, phoneNumber, address);
		this.wallet = 0;
		this.moneyWon = 0.0;
	}

	public Bet addBet(Bet apustua) {
		bets.add(apustua);
		return apustua;
	}
	
	public double getMoneyWon() {
		return this.moneyWon;
	}
	
	public void setMoneyWon(double money) {
		this.moneyWon+=money;
	}
	
	public float getWallet() {
		return this.wallet;
	}
	
	public void setWallet(float wallet) {
		this.wallet = wallet;
	}
}
