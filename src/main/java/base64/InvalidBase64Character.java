package base64;

@SuppressWarnings("serial")
public class InvalidBase64Character extends RuntimeException {

	public InvalidBase64Character() {
	}

	public InvalidBase64Character(String message) {
		super(message);
	}

	public InvalidBase64Character(Throwable cause) {
		super(cause);
	}

	public InvalidBase64Character(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidBase64Character(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
