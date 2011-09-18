package org.rcm.cmdline;

/**
 * This interface defines a simple option with a set of string value. It can accept
 * a set of default value even if none is specified on the command line.
 * 
 * @author Robert Monnet
 */
public interface ValuesOption {

    /**
     * get the list of values associated with the option. a value of null indicates that
     * the option was not specified and that no default value is available.
     * 
     * @return the value associated with the option or null if none was
     *         specified.
     */
    public String[] getValues();

    /**
     * specify if the option was defined on the command line (or has default values).
     * 
     * @return true if the option was set on the command line (or has default values).
     */
    public boolean isSet();

}
