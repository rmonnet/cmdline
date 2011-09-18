/*
 * Copyright Robert Monnet 2007, 2011
 * Released under the Apache 2.0 license (http://www.opensource.org/licenses/Apache-2.0) 
 */
package org.rcm.cmdline.impl;

import org.rcm.cmdline.ToggleOption;

/**
 * This class defines a toggle option, that is an option that return true or
 * false. The option returns true if it is set on the command line and false
 * otherwise.
 * 
 * @author Robert Monnet
 */
public class ToggleOptionImpl
    extends AbstractOption
    implements ToggleOption {

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
    public ToggleOptionImpl(String shortName, String longName, String help) {

        super(shortName, longName, help);
    }

    /**
     * @see org.rcm.cmdline.IOption#expectValue()
     */
    @Override
    public boolean expectValue() {

        return false;
    }

    /**
     * @see IOption#setValue(String)
     */
    @Override
    public void setValue(String value) {

        // value is implicit, just turn the option on
        isSet = true;
    }

    /**
     * specify if the option was defined on the command line.
     * 
     * @return true if the option was set on the command line.
     */
    @Override
    public boolean isSet() {

        return isSet;
    }

    /**
     * @see IOption#setValue(String)
     */
    @Override
    public void reset() {

        isSet = false;
    }

}
