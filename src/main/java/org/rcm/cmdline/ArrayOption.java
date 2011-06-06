/*
 * Copyright Robert Monnet 2007 
 */
package org.rcm.cmdline;

/**
 * This class defines a simple option with a set of string value. It can accept
 * a set of default value even if none is specified on the command line.
 * 
 * @author Robert Monnet
 */
public class ArrayOption
    extends AbstractOption
    implements IOption {

    // fields
    private String[] defaultValues;
    private String[] value;
    private String   variableName;

    /**
     * Construct an option without a default value. Short Name and Long name are
     * optional but at least one must be defined. To specify that short or long
     * name does not exist, the null or empty String must be used.
     * 
     * @param shortName
     *            the option short name (or null or "")
     * @param longName
     *            the option long name (or null or "")
     * @param varName
     *            the option variable name, used in the help text
     * @param help
     *            the help comment associated with the option
     * @throws IllegalArgumentException
     *             if the definition is invalid
     */
    public ArrayOption(String shortName, String longName, String varName, String help)
        throws IllegalArgumentException {

        this(shortName, longName, varName, help, null);
    }

    /**
     * Construct an option with a set of default value. Short Name and Long name
     * are optional but at least one must be defined. To specify that short or
     * long name does not exist, the null or empty String must be used.
     * 
     * @param shortName
     *            the option short name (or null or "")
     * @param longName
     *            the option long name (or null or "")
     * @param varName
     *            the option variable name, used in the help text
     * @param help
     *            the help comment associated with the option
     * @param defValues
     *            the default value associated with the option or null if none
     *            is provided
     * @throws IllegalArgumentException
     *             if the definition is invalid
     */
    public ArrayOption(String shortName, String longName, String varName, String help,
        String[] defValues)
        throws IllegalArgumentException {

        super(shortName, longName, help);
        defaultValues = defValues;
        value = defaultValues;
        variableName = varName;
    }

    /**
     * @see IOption#useImplicitValue()
     */
    public boolean useImplicitValue() {

        return false;
    }

    /**
     * @see IOption#setValue(String)
     */
    public void setValue(String optValue) {

        String[] values = optValue.split(",");
        value = values;
    }

    /**
     * retrieve the set of values associated with the option. a value of null
     * will be returned if the option was not specified on the command line and
     * if no default is provided
     * 
     * @return the set of values associated with the option
     */
    public String[] getValue() {

        return value;
    }

    /**
     * @see org.rcm.cmdline.IOption#isSet()
     */
    public boolean isSet() {

        return value != null;
    }

    /**
     * @see org.rcm.cmdline.IOption#reset()
     */
    public void reset() {

        value = defaultValues;
    }

    /**
     * @see IOption#getHelp()
     */
    public String getHelp() {

        String defaultString = null;

        if (defaultValues != null) {
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < defaultValues.length; i++) {
                if (i > 0) {
                    buf.append(",");
                }
                buf.append(defaultValues[i]);
            }
            defaultString = buf.toString();
        }
        return getHelp(variableName + ",...", defaultString);
    }

}
