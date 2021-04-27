package br.com.audoraselection.exception;

public class ResourceNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1419928075736513430L;

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
	
	public ResourceNotFoundException(String msg, Throwable cause){
        super(msg, cause);
	}
}
