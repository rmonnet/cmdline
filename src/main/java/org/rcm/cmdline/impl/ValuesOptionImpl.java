/*
 * Copyright Robert Monnet 2007, 2011
 * Released under the Apache 2.0 license (http://www.opensource.org/licenses/Apache-2.0) 
 */
package org.rcm.cmdline.impl;

import org.rcm.cmdline.ValuesOption;

/**
 * This class defines a simple option with a set of string value. It can accept
 * a set of default value even if none is specified on the command line.
 * 
 * @author Robert Monnet
 */
public class ValuesOptionImpl
    extends AbstractOption
    implements ValuesOption {

    // fields
    private String[] defaultValues;
    private String[] values;
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
    public ValuesOptionImpl(String shortName, String longName, String varName, String help)
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
    public ValuesOptionImpl(String shortName, String longName, String varName, String help,
        String[] defValues)
        throws IllegalArgumentException {

        super(shortName, longName, help);
        defaultValues = defValues;
        values = defaultValues;
        variableName = varName;
    }

    /**
     * @see IOption#expectValue()
     */
    @Override
    public boolean expectValue() {

        // always expect a value
        return true;
    }

    /**
     * @see IOption#setValue(String)
     */
    @Override
    public void setValue(String optValue) {

        values = optValue.split(",");
    }

    /**
     * retrieve the set of values associated with the option. a value of null
     * will be returned if the option was not specified on the command line and
     * if no default is provided
     * 
     * @return the set of values associated with the option
     */
    public String[] getValues() {

        return values;
    }

    /**
     * @see org.rcm.cmdline.IOption#isSet()
     */
    @Override
    public boolean isSet() {

        return values != null;
    }

    /**
     * @see org.rcm.cmdline.IOption#reset()
     */
    @Override
    public void reset() {

        values = defaultValues;
    }

    /**
     * @see IOption#getHelp()
     */
    @Override
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
