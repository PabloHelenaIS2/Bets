package exceptions;

public class MovementAlreadyExistsException extends Exception {
	
	public MovementAlreadyExistsException() {
		super();
	}

	public MovementAlreadyExistsException(String message) {
		super(message);
	}

}
