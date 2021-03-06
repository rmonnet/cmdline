/*
 * Copyright Robert Monnet 2007, 2011
 * Released under the Apache 2.0 license (http://www.opensource.org/licenses/Apache-2.0) 
 */
package org.rcm.cmdline.impl;

import org.rcm.cmdline.ValueOption;

/**
 * This class defines a simple option with a string value. It can accept a
 * default value even if none is specified on the command line.
 * 
 * @author Robert Monnet
 */
public class ValueOptionImpl
    extends AbstractOption
    implements ValueOption {

    // fields
    private String defaultValue;
    private String value;
    private String variableName;

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
    public ValueOptionImpl(String shortName, String longName, String varName, String help)
        throws IllegalArgumentException {

        this(shortName, longName, varName, help, null);
    }

    /**
     * Construct an option with a default value. Short Name and Long name are
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
     * @param defValue
     *            the default value associated with the option or null if none
     *            is provided
     * @throws IllegalArgumentException
     *             if the definition is invalid
     */
    public ValueOptionImpl(String shortName, String longName, String varName, String help,
        String defValue)
        throws IllegalArgumentException {

        super(shortName, longName, help);
        defaultValue = defValue;
        value = defaultValue;
        variableName = varName;
    }

    /**
     * @see org.rcm.cmdline.IOption#expectValue()
     */
    @Override
    public boolean expectValue() {

        // option is always associated with a value
        return true;
    }

    /**
     * @see IOption#setValue(String)
     */
    @Override
    public void setValue(String optValue) {

        value = optValue;
    }

    /**
     * get the value associated with the option. a value of null indicates that
     * the option was not specified and that no default value is available.
     * 
     * @return the value associated with the option or null if none was
     *         specified.
     */
    public String getValue() {

        return value;
    }

    /**
     * @see org.rcm.cmdline.IOption#isSet()
     */
    @Override
    public boolean isSet() {

        return value != null;
    }

    /**
     * @see org.rcm.cmdline.IOption#reset()
     */
    @Override
    public void reset() {

        value = defaultValue;

    }

    /**
     * @see IOption#getHelp()
     */
    @Override
    public String getHelp() {

        return getHelp(variableName, defaultValue);
    }

}
