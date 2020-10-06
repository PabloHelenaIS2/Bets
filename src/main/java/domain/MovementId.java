package domain;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

/**
 * Class used to query movements from the database using it's two Primary Keys:
 * RegularUser and
 * Date
 *
 */
@XmlAccessorType(XmlAccessType.PROPERTY) // ezdakit
public class MovementId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlID
	@XmlIDREF
	RegularUser user;
	
	@XmlID
	Date date;
	
	public MovementId(RegularUser user, Date date) {
		this.user = user;
		this.date = date;
	}

}
