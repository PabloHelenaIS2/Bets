package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSeeAlso;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ RegularUser.class, Admin.class })
public abstract class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Objectdb inplementatzerakoan, gako nagusia username 
	@Id
	@XmlID
	private String userName;
	private String userPass;
	
	private String firstName;
	private String lastName;
	private String userId;
	private Date birthDate;
	
	protected String email;
	protected String bankAccount;
	protected Integer phoneNumb;
	
	private String address;
	
	public User() {
		super();
	}
	
	public User(String userName, String userPass, String firstName, String lastName, String userId, Date birthDate, 
			String email, String bankAccount, Integer phoneNumber, String address) {
		this.userName = userName;
		this.userPass = userPass;
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.userId = userId;
		this.birthDate = birthDate;
		
		this.email = email;
		this.bankAccount = bankAccount;
		this.phoneNumb = phoneNumber;
		
		this.address = address;
	}
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Integer getPhoneNumb() {
		return phoneNumb;
	}

	public void setPhoneNumb(Integer phoneNumb) {
		this.phoneNumb = phoneNumb;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
