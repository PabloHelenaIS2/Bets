package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@Entity @IdClass(MovementId.class)
@XmlAccessorType(XmlAccessType.FIELD)
public class Movement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private RegularUser user;
	@Id
	private Date date;
	private float moneyMoved;
	private String description;
	
	public Movement() {
		super();
	}
	
	public Movement(RegularUser user) {
		this.date = new Date();
		this.user=user;
		this.moneyMoved = 0.0f;
	}
	
	public Movement(RegularUser user, Date data, float moneyMoved, String description) {
		this.user = user;
		this.date = data;
		this.moneyMoved = moneyMoved;
		this.description = description;
	}

	public RegularUser getUser() {
		return this.user;
	}

	public void setUser(RegularUser user) {
		this.user = user;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date data) {
		this.date = data;
	}

	public float getMoneyMoved() {
		return this.moneyMoved;
	}

	public void setMoneyMoved(float moneyMove) {
		this.moneyMoved = moneyMove;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

}
