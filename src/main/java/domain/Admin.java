package domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Admin extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Admin() {
		super();
	}
	
	public Admin(String userName, String userPass, String firstName, String lastName, String userId, Date birthDate, 
			String email, String bankAccount, Integer phoneNumber, String address) {
		
		super(userName, userPass, firstName, lastName, userId, birthDate, 
				email, bankAccount, phoneNumber, address);
		
	}
		
}
