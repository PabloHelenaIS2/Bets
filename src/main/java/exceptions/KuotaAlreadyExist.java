package exceptions;

public class KuotaAlreadyExist extends Exception{
	private static final long serialVersionUID = 1L;
	 
	 public KuotaAlreadyExist()
	  {
	    super();
	  }
	  /**This exception is triggered if the question already exists 
	  *@param s String of the exception
	  */
	  public KuotaAlreadyExist(String s)
	  {
	    super(s);
	  }
}
