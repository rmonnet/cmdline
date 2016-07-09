/*
 * Copyright Robert Monnet 2007, 2011
 * Released under the Apache 2.0 license (http://www.opensource.org/licenses/Apache-2.0) 
 */
package org.rcm.cmdline;

/**
 * This class defines an exception thrown when CommandLine parsing fails.
 * 
 * @author Robert Monnet
 */
public class CommandLineException extends RuntimeException {

	/**
	 * serial id for the class
	 */
	private static final long serialVersionUID = -1039222295988814347L;

	/**
	 * Construct a simple Command Line Exception
	 * 
	 * @param message
	 *            the error message associated with the exception
	 */
	public CommandLineException(String message) {

		super(message);
	}

}
