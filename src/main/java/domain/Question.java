package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Question implements Serializable {
	
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	@XmlID
	private Integer questionNumber;
	private String question; 
	private float betMinimum;
	private String result;  
	@XmlIDREF
	private Event event;
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
	public Vector<Kuota> kuotas =new Vector<Kuota>();
	
	public Question(){
		super();
	}
	
	public Question(Integer queryNumber, String query, float betMinimum, Event event) {
		super();
		this.questionNumber = queryNumber;
		this.question = query;
		this.betMinimum=betMinimum;
		this.event = event;
		this.kuotas = new Vector<Kuota>();
	}
	
	public Question(String query, float betMinimum,  Event event) {
		super();
		this.question = query;
		this.betMinimum=betMinimum;
		this.kuotas = new Vector<Kuota>();
		//this.event = event;
	}

	/**
	 * Get the  number of the question
	 * 
	 * @return the question number
	 */
	public Integer getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * Set the bet number to a question
	 * 
	 * @param questionNumber to be setted
	 */
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}


	/**
	 * Get the question description of the bet
	 * 
	 * @return the bet question
	 */

	public String getQuestion() {
		return question;
	}


	/**
	 * Set the question description of the bet
	 * 
	 * @param question to be setted
	 */	
	public void setQuestion(String question) {
		this.question = question;
	}



	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @return the minimum bet ammount
	 */
	
	public float getBetMinimum() {
		return betMinimum;
	}


	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @param  betMinimum minimum bet ammount to be setted
	 */

	public void setBetMinimum(float betMinimum) {
		this.betMinimum = betMinimum;
	}



	/**
	 * Get the result of the  query
	 * 
	 * @return the the query result
	 */
	public String getResult() {
		return result;
	}



	/**
	 * Get the result of the  query
	 * 
	 * @param result of the query to be setted
	 */
	
	public void setResult(String result) {
		this.result = result;
	}



	/**
	 * Get the event associated to the bet
	 * 
	 * @return the associated event
	 */
	public Event getEvent() {
		return event;
	}



	/**
	 * Set the event associated to the bet
	 * 
	 * @param event to associate to the bet
	 */
	public void setEvent(Event event) {
		this.event = event;
	}




	public String toString(){
		return questionNumber+";"+question+";"+Float.toString(betMinimum);
	}
	
	public void add(Kuota k){
		kuotas.add(k);
	}

	public Collection<Kuota> getKuotas() {
		System.out.println("ABIA");
		return kuotas;
	}
	

	public void setKuotas(Vector<Kuota> kuotas) {
		this.kuotas = kuotas;
	}

	public Kuota addKuota(String result, float kuota){
		
		Kuota k = new Kuota(result, kuota, this);
		
		
		kuotas.add( k);
		
		
		return k;
	}
	
	public boolean DoesKuotaExists(String result)  {	
		for (Kuota k:this.getKuotas()){
			if (k.getResult().compareTo(result)==0)
				return true;
		}
		return false;
	}


	
}