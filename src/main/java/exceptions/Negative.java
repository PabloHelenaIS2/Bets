package exceptions;

public class Negative extends Exception{
	private static final long serialVersionUID = 1L;
	
	public Negative() {
		super();
	}

	public Negative(String s) {
		super(s);
		System.out.println(s);
	}
}
