package exceptions;

public class NoMoneyException extends Exception{
	private static final long serialVersionUID = 1L;
	 
	 public NoMoneyException()
	  {
	    super();
	  }
	  /**This exception is triggered if the question already exists 
	  *@param s String of the exception
	  */
	  public NoMoneyException(String s)
	  {
	    super(s);
	  }
}
