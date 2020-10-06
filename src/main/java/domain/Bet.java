package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity

public class Bet implements Serializable {
	
	@Id  
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	@XmlID
	private Integer betNumber;
	//@OneToOne(cascade = CascadeType.REMOVE)

	@XmlIDREF
	private RegularUser user;
	private float bet;
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
	private ArrayList<Kuota> kuota = new ArrayList<Kuota>();

	
	public Bet(){
		super();
	}
	
	public Bet(Integer betNumber, RegularUser user, float bet, ArrayList<Kuota> kuota) {
		super();
		this.betNumber = betNumber;
		this.user = user;
		this.bet = bet;
		this.kuota = kuota;
		
	}
	
	public Bet(Integer betNumber, RegularUser user, float bet ) {
		super();
		this.betNumber = betNumber;
		this.user = user;
		this.bet = bet;
		
		
	}
	
	public Bet(RegularUser user, float bet, ArrayList<Kuota> kuota) {
		//super();
		this.user = user;
		this.bet = bet;
		this.kuota=kuota;
		
	}
	public Bet(RegularUser user, float bet) {
		super();
		this.user = user;
		this.bet = bet;
		
		
	}

	public Integer getBetNumber() {
		return betNumber;
	}

	public void setBetNumber(Integer betNumber) {
		this.betNumber = betNumber;
	}

	public RegularUser getUser() {
		return user;
	}

	public void setUser(RegularUser user) {
		this.user = user;
	}
	
	public float getBet() {
		return bet;
	}
	
	public void setBet(float bet) {
		this.bet = bet;
	}
	
	public ArrayList<Kuota> getKuota() {
		return this.kuota;
	}
	
	public void setKuota(ArrayList<Kuota> kuota) {
		this.kuota = kuota;
	}

	@Override
	public String toString() {
		return "Bet [betNumber=" + this.betNumber + ", user=" + this.user + ", bet=" + this.bet + ", kuota=" + this.kuota + "]";
	}
	public Date getFirstDate() {
		Date data = new Date();
		data= this.getKuota().get(0).getQuestion().getEvent().getEventDate();
		for(Kuota kuota : this.kuota) {
			if(kuota.getQuestion().getEvent().getEventDate().after(data)) data=kuota.getQuestion().getEvent().getEventDate();
		}
		return data;
		
	}
	public boolean equals(Bet bet) {
		int j=0;
		if(this.bet==bet.getBet() && this.kuota.size()==bet.getKuota().size()) {
			
			for(Kuota a : this.kuota) {
			 if(this.kuota.get(j).equals(bet.getKuota().get(j))) {
				 System.out.println("HAU BAI");
				 j++;
			 
		}
			 
			else {
				System.out.println("HAU EX");
				return false;
			}
		
	} 
			System.out.println("TURE bueltatu da");
			return true;
		
}
		else return false;
			}
	
}


