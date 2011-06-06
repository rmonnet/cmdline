/*
 * Copyright Robert Monnet 2007 
 */
package org.rcm.cmdline;

/**
 * This interface define the API for any type of option accepted by the command
 * line. This package can be extended by addding new options such as an option
 * that would only parse integer numbers.
 * 
 * @author Robert Monnet
 */
public interface IOption {

    /**
     * Provide the option's short name
     * 
     * @return the short name associated with the option or null if none is
     *         associated.
     */
    public String getShortName();

    /**
     * Provide the option's long name
     * 
     * @return the long name associated with the option or null if none is
     *         associated.
     */
    public String getLongName();

    /**
     * provide one line of help describing the option.
     * 
     * @return the help text associated with the option
     */
    public String getHelp();

    /**
     * specifies if the option uses an implicit value or not
     * 
     * @return true if the option is not expecting a value.
     */
    public boolean useImplicitValue();

    /**
     * set the value for the option. This is typically called by {@link CommandLine#parse(String[])}
     * . If the option uses an implicit
     * value, this is called to confirm that the option is present on the
     * command line. In this case the value itself is ignored.
     * 
     * @param value
     *            the value to associate with the option.
     */
    public void setValue(String value);

    /**
     * specifies if this option has been set
     * 
     * @return true if the option is set
     */
    public boolean isSet();

    /**
     * reset the option to its initial state. This is used by {@link CommandLine#reset()}.
     */
    public void reset();

}
