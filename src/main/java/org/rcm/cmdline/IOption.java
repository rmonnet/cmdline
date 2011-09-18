package org.rcm.cmdline;

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
    public abstract boolean useImplicitValue();

    /**
     * reset the option to its initial state. This is used by {@link CommandLine#reset()}.
     */
    public abstract void reset();

    /**
     * set the value for the option. This is typically called by {@link CommandLine#parse(String[])}
     * . If the option uses an implicit
     * value, this is called to confirm that the option is present on the
     * command line. In this case the value itself is ignored.
     * 
     * @param value
     *            the value to associate with the option.
     */
    public abstract void setValue(String value);

}
