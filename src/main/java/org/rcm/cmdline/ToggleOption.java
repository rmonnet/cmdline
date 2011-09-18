/*
 * Copyright Robert Monnet 2007, 2011
 * Released under the Apachae 2.0 license (http://www.opensource.org/licenses/Apache-2.0) 
 */
package org.rcm.cmdline;

/**
 * This class defines a toggle option, that is an option that return true or
 * false. The option returns true if it is set on the command line and false
 * otherwise.
 * 
 * @author Robert Monnet
 */
public class ToggleOption
    extends AbstractOption
    implements IOption {

    // fields
    private boolean isSet;

    /**
     * Construct the option specifying a short and a long name. Both are
     * optional but at least one must be specified. To indicate no short or long
     * name is associated with the option, use either null or the empty string.
     * 
     * @param shortName
     *            the option short name (or null or "")
     * @param longName
     *            the option long name (or null or "")
     * @param help
     *            the help text associated with the option
     */
    public ToggleOption(String shortName, String longName, String help) {

        super(shortName, longName, help);
    }

    /**
     * @see org.rcm.cmdline.IOption#useImplicitValue()
     */
    public boolean useImplicitValue() {

        return true;
    }

    /**
     * @see IOption#setValue(String)
     */
    public void setValue(String value) {

        // value is implicit, just turn the option on
        isSet = true;
    }

    /**
     * specify if the option was defined on the command line.
     * 
     * @return true if the option was set on the command line.
     */
    public boolean isSet() {

        return isSet;
    }

    /**
     * @see IOption#setValue(String)
     */
    public void reset() {

        isSet = false;
    }

}
