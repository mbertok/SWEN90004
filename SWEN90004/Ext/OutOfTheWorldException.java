package Ext;

public class OutOfTheWorldException extends Exception {
	public OutOfTheWorldException(String message) {
        super(message);
    } 
	
	public OutOfTheWorldException(String message, Throwable cause) {
        super(message, cause);
    }
}
