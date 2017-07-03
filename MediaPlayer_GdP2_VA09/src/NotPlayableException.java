
@SuppressWarnings("serial")
public class NotPlayableException extends Exception {

	/* ------------------------------------------------- */
	// Globals
	private String pathname = "";
	
	/* ------------------------------------------------- */
	// Constructors
	public NotPlayableException (String pathname, String msg) {
		super(msg);
		this.pathname = pathname;
	}
	
	public NotPlayableException (String pathname, Throwable t) {
		super(t);
		this.pathname = pathname;
	}
	
	public NotPlayableException (String pathname, String msg, Throwable t) {
		super(msg, t);
		this.pathname = pathname;
	}
	
	
	// toString
	public String toString() {
		return pathname + ": " + super.toString();
	}
	
	
}
