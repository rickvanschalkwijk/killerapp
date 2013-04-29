package core.connection;

public class DataException extends Exception {

	private static final long serialVersionUID = 513339257350180321L;
	private final String message;

	public DataException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
