/*
 * Copyright Robert Monnet 2007, 2011
 * Released under the Apache 2.0 license (http://www.opensource.org/licenses/Apache-2.0) 
 */
package org.rcm.cmdline;

/**
 * This interface defines a simple option with a string value. It may have a
 * default value even if none is specified on the command line.
 * 
 * @author Robert Monnet
 */
public interface ValueOption {

    /**
     * get the value associated with the option. a value of null indicates that
     * the option was not specified and that no default value is available.
     * 
     * @return the value associated with the option or null if none was
     *         specified.
     */
    public String getValue();

    /**
     * specify if the option was defined on the command line (or has a default value).
     * 
     * @return true if the option was set on the command line (or has a default value).
     */
    public boolean isSet();

}
