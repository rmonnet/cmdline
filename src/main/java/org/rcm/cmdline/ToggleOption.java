/*
 * Copyright Robert Monnet 2007, 2011
 * Released under the Apache 2.0 license (http://www.opensource.org/licenses/Apache-2.0) 
 */
package org.rcm.cmdline;

/**
 * This interface defines a toggle option, that is an option that return true or
 * false. The option returns true if it is set on the command line and false
 * otherwise.
 * 
 * @author Robert Monnet
 */
public interface ToggleOption {

    /**
     * specify if the option was defined on the command line.
     * 
     * @return true if the option was set on the command line.
     */
    public boolean isSet();

}
