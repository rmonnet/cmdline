/*
 * Copyright Robert Monnet 2007, 2011
 * Released under the Apachae 2.0 license (http://www.opensource.org/licenses/Apache-2.0) 
 */
package org.rcm.cmdline;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class defines a smart command line. It parses short (-) and long (--)
 * options and provides the remaining positional arguments. It is inspired by
 * Python's <a
 * href="http://docs.python.org/lib/module-optparse.html">optparse</a>.
 * <p>
 * CommandLine accepts 3 types of options:
 * <ul>
 * <li>ToggleOption are boolean options that are present or not</li>
 * <li>Option are single value options</li>
 * <li>ArrayOption can be associated with a set of options</li>
 * </ul>
 * CommandLine parses the command line arguments, set all the associated options and provides the
 * remaining positional arguments in an array.
 * <p>
 * Short options are comprised of an hyphen (-) followed by one single letter. If a short option
 * accepts a value then it is separated from the short option by a space. If a short option accepts
 * a n array of values, they are packed together and separated by commas. For toggle options that do
 * not take values, it is valid to compact several options following a single hyphen.
 * <dl>
 * <dt>examples of short options:</dt>
 * <dd><code>ls -f</code></dd>
 * <dd><code>cc -o hello<code></dd>
 * <dd><code>ignore -p bak,exe,class</code></dd>
 * </dl>
 * <p>
 * Long options are comprised of two hyphens (--) followed by at least a two letter word. If a long
 * option accepts a value then it can be attached to the option by an equal sign or separated by a
 * space. An array of value can be used if separated by commas.
 * <dl>
 * <dt>examples of long options:</dt>
 * <dd><code>java --verbose</code></dd>
 * <dd><code>colorize --color=black</code></dd>
 * <dd><code>cal --except=monday,friday</code></dd>
 * </dl>
 * 
 * @author Robert Monnet
 */
public class CommandLine {

    // OS independent new-line
    private final static String  NL = System.getProperty("line.separator", "\n");

    // fields
    private Map<String, IOption> optionsByShortName;
    private Map<String, IOption> optionsByLongName;
    private List<IOption>        optionList;
    private String               usage;

    /**
     * Construct a CommandLine.
     * 
     * @param helpUsage
     *            usage text used by {@link CommandLine#getHelp()}
     */
    public CommandLine(String helpUsage) {

        optionsByShortName = new HashMap<String, IOption>();
        optionsByLongName = new HashMap<String, IOption>();
        optionList = new LinkedList<IOption>();
        usage = helpUsage;

    }

    /**
     * add a regular option to the command line. Once an option is added, it can
     * be used by its short name (-) or long name (--) if defined.
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
    public Option addOption(String shortName, String longName, String varName, String help)
        throws IllegalArgumentException {

        Option res = new Option(shortName, longName, varName, help);
        add(res);
        return res;

    }

    /**
     * add a regular option to the command line. Once an option is added, it can
     * be used by its short name (-) or long name (--) if defined.
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
    public Option addOption(String shortName, String longName, String varName, String help,
        String defValue)
        throws IllegalArgumentException {

        Option res = new Option(shortName, longName, varName, help, defValue);
        add(res);
        return res;

    }

    /**
     * add a toggle option to the command line. Once an option is added, it can
     * be used by its short name(-) or long name (--) if defined.
     * 
     * @param shortName
     *            the option short name (or null or "")
     * @param longName
     *            the option long name (or null or "")
     * @param help
     *            the help text associated with the option
     */
    public ToggleOption addToggleOption(String shortName, String longName, String help) {

        ToggleOption res = new ToggleOption(shortName, longName, help);
        add(res);
        return res;

    }

    /**
     * add an array option to the command line. Once an option is added, it can
     * be used by its short name (-) or its long name (--) if defined.
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
    public ArrayOption
        addArrayOption(String shortName, String longName, String varName, String help)
            throws IllegalArgumentException {

        ArrayOption res = new ArrayOption(shortName, longName, varName, help);
        add(res);
        return res;
    }

    /**
     * add an array option to the command line. Once an option is added, it can
     * be used by its short name (-) or its long name (--) if defined.
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
    public ArrayOption addArrayOption(String shortName, String longName, String varName,
        String help, String[] defValues)
        throws IllegalArgumentException {

        ArrayOption res = new ArrayOption(shortName, longName, varName, help);
        add(res);
        return res;
    }

    /**
     * add an option to the command line. Once an option is added, it can be
     * used by its short name (-) or long name (--) if defined.
     * 
     * @param option
     *            option to add to the command line
     * @throws IllegalArgumentException
     *             if the option is a duplicate of an existing option
     */
    public void add(IOption option)
        throws IllegalArgumentException {

        // each short name and long name can only be added once.
        String shortName = option.getShortName();
        if (shortName != null && optionsByShortName.containsKey(shortName)) {
            throw new IllegalArgumentException("duplicate short name option declared '" + shortName
                + "'");
        }
        String longName = option.getLongName();
        if (longName != null && optionsByLongName.containsKey(longName)) {
            throw new IllegalArgumentException("duplicate long name option declared '" + longName
                + "'");
        }

        // store the option by long and short name
        if (shortName != null) {
            optionsByShortName.put(shortName, option);
        }
        if (longName != null) {
            optionsByLongName.put(longName, option);
        }
        // keep a list of options for global operations such as help() and
        // reset()
        optionList.add(option);
    }

    /**
     * reset all the options associated with the command line. This is useful if
     * more than one set of command line argument must be parsed.
     */
    public void reset() {

        for (IOption option : optionList) {
            option.reset();
        }
    }

    /**
     * parse an array of command line argument. Arguments associated with
     * options added to the Command Line will be parsed and set, remaining
     * arguments (positional arguments) will be returned.
     * 
     * @param args
     *            the array of arguments including options and positional
     *            arguments
     * @return the array of positional arguments
     * @throws CommandLineException
     *             if the command line does not match the set of defined options
     */
    public String[] parse(String[] args)
        throws CommandLineException {

        // the OptionParser can be reused several time so everytime
        // we parse, we first reset the results.
        reset();

        // Let's parse all options first
        // we could find:
        // 1- "-a" => a single short boolean option
        // 2- "-ab" => a set of short boolean options
        // 3- "-a=value" => a single short option with a value or a list
        // 4- "-a" "value" => a single short option with the next arg being the
        // value r a list of values
        // 5- "--opt" => a single long boolean option
        // 6- "--noopt" => a single long boolean option in negative form
        // 7- "--opt=value" => a single long option with a value or a list
        // 8- "--opt" "value" => a single long option with the next arg being
        // the
        // value or the list of values

        int idx = 0;
        while (idx < args.length) {

            String tok = args[idx];
            if (tok.charAt(0) != '-') {
                // we are past the option section, in the positional arguments
                break;
            }

            if (tok.startsWith("--")) {
                // long name option
                if (tok.length() == 2) {
                    throw new CommandLineException("missing short option after --");
                }
                idx = parseLongOption(args, idx);
            }
            else {
                // short name option
                if (tok.length() == 1) {
                    throw new CommandLineException("missing short option after -");
                }
                if (tok.length() == 2) {
                    idx = parseShortOption(args, idx);
                }
                else {
                    idx = parseMultipleShortOptions(args, idx);
                }
            }
        }

        // now if any parameters are left, they are positional
        String[] res = new String[args.length - idx];
        for (int i = idx; i < args.length; i++) {
            res[i - idx] = args[i];
        }

        return res;
    }

    /**
     * parse a long option
     * 
     * @param args
     *            the array of parameters to parse
     * @param idx
     *            the index in the parameters array of the option
     * @return the index of the next option to parse
     * @throws CommandLineException
     *             if the option is invalid
     */
    private int parseLongOption(String[] args, int idx)
        throws CommandLineException {

        String tok = args[idx];
        // see if there is a value attached to the option
        int eqIdx = tok.indexOf("=");
        String optName = eqIdx < 0 ? tok.substring(2) : tok.substring(2, eqIdx);

        // find if the option exists
        IOption option = optionsByLongName.get(optName);
        if (option == null) {
            throw new CommandLineException("unknown option long name '" + optName + "'");
        }

        // see if the option requires a value
        if (option.useImplicitValue()) {
            if (eqIdx >= 0) {
                throw new CommandLineException("option '" + optName + "' was not expecting a value");
            }
            // no value needed, just "toggle" the option
            option.setValue(null);
        }
        else {
            // need a value, if we don't have one then it should be in the next
            // argument
            if (eqIdx >= 0) {
                String value = tok.substring(eqIdx + 1);
                option.setValue(value);
            }
            else {
                if (idx >= args.length - 1 || args[idx + 1].startsWith("-")) {
                    throw new CommandLineException("option '" + optName + "' was expecting a value");
                }
                String value = args[++idx];
                option.setValue(value);
            }
        }
        // skip to the next argument
        return ++idx;
    }

    /**
     * parse a single short option
     * 
     * @param args
     *            the array of parameters to parse
     * @param idx
     *            the index in the parameters array of the option
     * @return the index of the next option to parse
     * @throws CommandLineException
     *             if the option is invalid
     */
    private int parseShortOption(String[] args, int idx)
        throws CommandLineException {

        String tok = args[idx];
        String optName = tok.substring(1);

        // find if the option exists
        IOption option = optionsByShortName.get(optName);
        if (option == null) {
            throw new CommandLineException("unknown option short name '" + optName + "'");
        }

        // see if the option requires a value
        if (option.useImplicitValue()) {
            // no value needed, just "toggle" the option
            option.setValue(null);
        }
        else {
            // need a value, for short option this is in the next argument
            if (idx >= args.length - 1 || args[idx + 1].startsWith("-")) {
                throw new CommandLineException("option '" + optName + "' was expecting a value");
            }
            String value = args[++idx];
            option.setValue(value);
        }
        // skip to the next argument
        return ++idx;

    }

    /**
     * parse a set of short options packed together
     * 
     * @param args
     *            the array of parameters to parse
     * @param idx
     *            the index in the parameters array of the option
     * @return the index of the next option to parse
     * @throws CommandLineException
     *             if any option is invalid
     */
    private int parseMultipleShortOptions(String[] args, int idx)
        throws CommandLineException {

        String tok = args[idx];

        // multiple short options, all must use implicit value
        for (int i = 1; i < tok.length(); i++) {
            String optName = tok.substring(i, i + 1);

            // find if the option exists
            IOption option = optionsByShortName.get(optName);
            if (option == null) {
                throw new CommandLineException("unknown option short name '" + optName + "'");
            }

            // see if the option requires a value
            if (option.useImplicitValue()) {
                // no value needed, just "toggle" the option
                option.setValue(null);
            }
            else {

                throw new CommandLineException("option '" + optName
                    + "' was expecting a value, cannot be used in combination with other options '"
                    + tok + "'");

            }
        }
        // skip to the next argument
        return ++idx;

    }

    /**
     * return a help text for the command line and all associated options. It
     * includes the usage set when the command line is created as well as one
     * line for each option associated with the command line.
     * 
     * @return the help text for the command line.
     */
    public String getHelp() {

        StringBuffer res = new StringBuffer();
        res.append(usage).append(NL);
        for (IOption option : optionList) {
            res.append("    ").append(option.getHelp()).append(NL);
        }
        return res.toString();

    }
}
