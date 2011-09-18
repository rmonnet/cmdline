/*
 * Copyright Robert Monnet 2007, 2011
 * Released under the Apache 2.0 license (http://www.opensource.org/licenses/Apache-2.0) 
 */
package org.rcm.cmdline.impl;

import org.rcm.cmdline.CommandLine;

/**
 * This class provides the foundation for all options types and define common
 * operations among ToggleOption, Option and ArrayOption.
 * 
 * @author Robert Monnet
 */
public abstract class AbstractOption {

    // fields
    private String shortName;
    private String longName;
    private String help;

    /**
     * Construct an option by specifying a short and long name and the help
     * text. Short and Long name are optional but at least one must be defined.
     * Undefined short or long names are specified by null or the empty string.
     * 
     * @param oShortName
     *            the short name associated with the option (or null or "" if
     *            not specified)
     * @param oLongName
     *            the long name associated with the option (or null or "" if not
     *            specified)
     * @param oHelp
     *            the help text associated with the option.
     * @throws IllegalArgumentException
     *             if the parameters are invalid.
     */
    public AbstractOption(String oShortName, String oLongName, String oHelp)
        throws IllegalArgumentException {

        // allows both form for no option: null or ""
        if ("".equals(oShortName)) {
            oShortName = null;
        }
        if ("".equals(oLongName)) {
            oLongName = null;
        }
        // make sure we have at least a short or long name
        if (oShortName == null && oLongName == null) {
            throw new IllegalArgumentException(
                "at least one of short name or long name must be specified");
        }
        // help should always be filled in
        if (oHelp == null || oHelp.length() == 0) {
            throw new IllegalArgumentException("help string cannot be null or empty");
        }

        // if short name is provided, it must be one character long
        if (oShortName != null && oShortName.length() != 1) {
            throw new IllegalArgumentException("short option name must be 1 character '"
                + oShortName + "'");
        }

        // if long name is provided, it must be at least 2 characters long
        if (oLongName != null && oLongName.length() < 2) {
            throw new IllegalArgumentException("long option name must be at least 2 characters '"
                + oLongName + "'");
        }

        // option is valid, store its properties
        shortName = oShortName;
        longName = oLongName;
        help = oHelp;
    }

    /**
     * provide one line of help describing the option.
     * 
     * @return the help text associated with the option
     */
    public String getHelp() {

        if (shortName == null) {
            return "--" + longName + " : " + help;
        }
        if (longName == null) {
            return "-" + shortName + " : " + help;
        }
        return "-" + shortName + " --" + longName + " : " + help;

    }

    /**
     * provides the help text associated with the option. This is nearly
     * identical to {@link AbstractOption#getHelp()} but allows to specify the
     * mnemonic name of the value associated with the option and the default
     * value. Both can be specified as null if non relevant for this option.
     * 
     * @param valueName
     *            the mnemonic name associated with the option value (or null)
     * @param defaultValue
     *            the default value associated with the option (or null)
     * @return the help text for the option.
     */
    protected String getHelp(String valueName, String defaultValue) {

        String res = null;
        if (shortName == null) {
            res = "--" + longName + "=<" + valueName + "> : " + help;
        } else
            if (longName == null) {
                res = "-" + shortName + " <" + valueName + "> : " + help;
            } else {
                res =
                    "-" + shortName + " <" + valueName + ">, --" + longName + "=<" + valueName
                        + "> : " + help;
            }
        if (defaultValue != null) {
            res = res + " (default to " + defaultValue + ")";
        }
        return res;
    }

    /**
     * Provide the option's long name
     * 
     * @return the long name associated with the option or null if none is
     *         associated.
     */
    public String getLongName() {

        return longName;

    }

    /**
     * Provide the option's short name
     * 
     * @return the short name associated with the option or null if none is
     *         associated.
     */
    public String getShortName() {

        return shortName;

    }

    /**
     * specifies if the option expect some associated value(s).
     * 
     * @return true if the option is expecting a value.
     */
    public abstract boolean expectValue();

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

    /**
     * specifies if this option has been set
     * 
     * @return true if the option is set
     */
    public abstract boolean isSet();

    /**
     * reset the option to its initial state. This is used by {@link CommandLine#reset()}.
     */
    public abstract void reset();

}
