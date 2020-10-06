package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Kuota implements Serializable {
	
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	@XmlID
	private Integer kuotaNumber;
	private String result;
	private float kuota;
	//@OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
	@XmlIDREF
	private Question question;
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
	public Collection<Bet> bets =new ArrayList<Bet>();;
	
	
	public Kuota(){
		super();
	}
	
	public Kuota(Integer kuotaNumber, String result, float kuota, Question question) {
		super();
		this.kuotaNumber = kuotaNumber;
		this.result = result;
		this.kuota = kuota;
		this.question = question;
		this.bets = new ArrayList<Bet>();
	}
	
	public Kuota(String result, float kuota,  Question question) {
		super();
		this.result = result;
		this.kuota = kuota;
		this.question=question;
		this.bets = new ArrayList<Bet>();
		
	}


	public Integer getKuotaNumber() {
		return kuotaNumber;
	}


	public void setKuotaNumber(Integer kuotaNumber) {
		this.kuotaNumber = kuotaNumber;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public float getKuota() {
		return kuota;
	}


	public void setKuota(float kuota) {
		this.kuota = kuota;
	}


	public Question getQuestion() {
		return question;
	}


	public void setQuestion(Question question) {
		this.question = question;
	}


	@Override
	public String toString() {
		return "Kuota [kuotaNumber=" + kuotaNumber + ", result=" + result + ", kuota=" + kuota + ", question="
				+ question + "]";
	}
	
	public void add(Bet b){
		bets.add(b);
	}

	public Collection<Bet> getBets() {
		return bets;
	}
	

	public void setBets(ArrayList<Bet> bets) {
		this.bets = bets;
	}

	public Bet addBet(RegularUser user, float bet){
		Bet b = new Bet(user, bet);
		bets.add(b);
		return b;
	}
	public Bet addBet(Bet bet) {
		bets.add(bet);
		return bet;
	}
	
	public boolean DoesBetExists(RegularUser user)  {	
		for (Bet b :this.getBets()){
			if (b.getUser() == user)
				return true;
		}
		return false;
	} 
	
	public boolean equals(Kuota kuota) {
		//if(this.kuota==kuota.getKuota()&&this.question.getQuestion()==kuota.getQuestion().getQuestion())
			if(this.kuota==kuota.getKuota()&&this.question.getQuestion().equals(kuota.getQuestion().getQuestion()))return true;
		 return false;
		
		
	}
	
}
