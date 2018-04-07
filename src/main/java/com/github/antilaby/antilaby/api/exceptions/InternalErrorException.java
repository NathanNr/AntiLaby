package com.github.antilaby.antilaby.api.exceptions;

import com.github.antilaby.antilaby.util.Constants;

/**
 * Exception for problems with AntiLaby.
 * 
 * @author NathanNr
 */
public class InternalErrorException extends RuntimeException {

	private static final long serialVersionUID = 4682945371734435384L;

	public InternalErrorException(String location, String message, String id) {
		super(id == null
				? "[AntiLaby/" + location + "] An internal error occured: \"" + message
						+ "\" Please report the bug here: " + Constants.BUG_REPORT_URL
				: "[AntiLaby/" + location + "] An internal error occured. \"" + message
						+ "\" Please report the bug with the id '" + id + "' here: " + Constants.BUG_REPORT_URL);
	}

}