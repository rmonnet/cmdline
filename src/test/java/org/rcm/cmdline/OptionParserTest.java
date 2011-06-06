/**
 * 
 */
package org.rcm.cmdline;

import org.rcm.cmdline.CommandLine;
import org.rcm.cmdline.ArrayOption;
import org.rcm.cmdline.CommandLineException;
import org.rcm.cmdline.Option;
import org.rcm.cmdline.ToggleOption;
import junit.framework.TestCase;

/**
 * @author Robert
 */
public class OptionParserTest
    extends TestCase {

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp()
        throws Exception {

        super.setUp();
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown()
        throws Exception {

        super.tearDown();
    }

    /**
     * test invalid command line
     */
    public void testInvalidCommandLine() {

        // unknown option (short)
        {
            CommandLine op = new CommandLine("usage...");
            try {
                op.parse(new String[] {"-c", "arg1"});
                fail("should have thrown CommandLineException");
            }
            catch (CommandLineException _) {
                // expected
            }
        }

        // unknown option (long)
        {
            CommandLine op = new CommandLine("usage...");
            try {
                op.parse(new String[] {"--color", "arg1"});
                fail("should have thrown CommandLineException");
            }
            catch (CommandLineException _) {
                // expected
            }
        }

        // invalid short option
        {
            CommandLine op = new CommandLine("usage...");
            try {
                op.parse(new String[] {"-co", "arg1"});
                fail("should have thrown CommandLineException");
            }
            catch (CommandLineException _) {
                // expected
            }
        }

        // invalid short option ("-")
        {
            CommandLine op = new CommandLine("usage...");
            try {
                op.parse(new String[] {"-", "arg1"});
                fail("should have thrown CommandLineException");
            }
            catch (CommandLineException _) {
                // expected
            }
        }

        // invalid long option
        {
            CommandLine op = new CommandLine("usage...");
            try {
                op.parse(new String[] {"--c", "arg1"});
                fail("should have thrown CommandLineException");
            }
            catch (CommandLineException _) {
                // expected
            }
        }

        // invalid long option ("--")
        {
            CommandLine op = new CommandLine("usage...");
            try {
                op.parse(new String[] {"--", "arg1"});
                fail("should have thrown CommandLineException");
            }
            catch (CommandLineException _) {
                // expected
            }
        }

        // invalid short option ("-c=ad")
        {
            CommandLine op = new CommandLine("usage...");
            Option color = new Option("c", "color", "color", "help for color");
            op.add(color);
            try {
                op.parse(new String[] {"-c=blue", "arg1"});
                fail("should have thrown CommandLineException");
            }
            catch (CommandLineException _) {
                // expected
            }
        }

        // missing value (short option)
        {
            CommandLine op = new CommandLine("usage...");
            Option color = new Option("c", "color", "color", "help for color");
            op.add(color);
            ToggleOption debug = new ToggleOption("d", "debug", "help for debug");
            op.add(debug);
            try {
                op.parse(new String[] {"-c", "-d", "arg1"});
                fail("should have thrown CommandLineException");
            }
            catch (CommandLineException _) {
                // expected
            }
        }

        // missing value (long option)
        {
            CommandLine op = new CommandLine("usage...");
            Option color = new Option("c", "color", "color", "help for color");
            op.add(color);
            ToggleOption debug = new ToggleOption("d", "debug", "help for debug");
            op.add(debug);
            try {
                op.parse(new String[] {"--color", "-d", "arg1"});
                fail("should have thrown CommandLineException");
            }
            catch (CommandLineException _) {
                // expected
            }
        }

        // missing value (long option, =)
        {
            CommandLine op = new CommandLine("usage...");
            Option color = new Option("c", "color", "color", "help for color");
            op.add(color);
            ToggleOption debug = new ToggleOption("d", "debug", "help for debug");
            op.add(debug);
            // it is valid to specify an empty string option,
            // however this can only be doen through the = form
            op.parse(new String[] {"--color=", "-d", "arg1"});
        }

        // toggle option with value (long )
        {
            CommandLine op = new CommandLine("usage...");
            Option color = new Option("c", "color", "color", "help for color");
            op.add(color);
            ToggleOption debug = new ToggleOption("d", "debug", "help for debug");
            op.add(debug);
            try {
                op.parse(new String[] {"--debug=2", "arg1"});
                fail("should have thrown CommandLineException");
            }
            catch (CommandLineException _) {
                // expected
            }
        }

    }

    /**
     * test duplicate options for the same command line
     */
    public void testDuplicateOptions() {

        // test duplicate shorts
        {
            CommandLine op = new CommandLine("usage...");
            Option color = new Option("c", "color", "color", "help for color");
            op.add(color);
            ToggleOption debug = new ToggleOption("c", "debug", "help for debug");
            try {
                op.add(debug);
                fail("should have thrown IllegalArgumentException");
            }
            catch (IllegalArgumentException _) {
                // expected
            }
        }

        // test duplicate shorts
        {
            CommandLine op = new CommandLine("usage...");
            Option color = new Option("c", "color", "color", "help for color");
            op.add(color);
            ToggleOption debug = new ToggleOption("d", "color", "help for debug");
            try {
                op.add(debug);
                fail("should have thrown IllegalArgumentException");
            }
            catch (IllegalArgumentException _) {
                // expected
            }
        }

    }

    /**
     * test the toggle option
     */
    public void testToggleOption() {

        // test short option
        {
            ToggleOption verbose = new ToggleOption("v", "verbose", "set the output to verbose");
            CommandLine op = new CommandLine("usage ...");
            op.add(verbose);

            String[] args = op.parse(new String[] {"-v", "arg1", "arg2"});
            assertEquals(2, args.length);
            assertEquals("arg1", args[0]);
            assertEquals("arg2", args[1]);
            assertTrue(verbose.isSet());
        }

        // test long option
        {
            ToggleOption verbose = new ToggleOption("v", "verbose", "set the output to verbose");
            CommandLine op = new CommandLine("usage ...");
            op.add(verbose);

            String args[] = op.parse(new String[] {"--verbose", "arg1", "arg2"});
            assertEquals(2, args.length);
            assertEquals("arg1", args[0]);
            assertEquals("arg2", args[1]);
            assertTrue(verbose.isSet());
        }

        // test multiple short options
        {
            CommandLine op = new CommandLine("usage ...");
            ToggleOption verbose = new ToggleOption("v", "verbose", "set the output to verbose");
            op.add(verbose);
            ToggleOption bandw =
                new ToggleOption("b", "bandw", "set the display to black and white");
            op.add(bandw);
            ToggleOption unused = new ToggleOption("u", "unused", "some unused option");
            op.add(unused);

            assertFalse(verbose.isSet());
            assertFalse(bandw.isSet());
            assertFalse(unused.isSet());
            String args[] = op.parse(new String[] {"-bv"});
            assertEquals(0, args.length);
            assertTrue(verbose.isSet());
            assertTrue(bandw.isSet());
            assertFalse(unused.isSet());
        }
    }

    /**
     * test the simple option
     */
    public void testOption() {

        // without default, short option
        {
            Option color = new Option("c", "color", "color", "set the color");
            CommandLine op = new CommandLine("usage ...");
            op.add(color);

            assertTrue(color.getValue() == null);
            String args[] = op.parse(new String[] {"-c", "blue", "arg1", "arg2"});
            assertEquals(2, args.length);
            assertEquals("arg1", args[0]);
            assertEquals("arg2", args[1]);
            assertEquals("blue", color.getValue());
        }

        // with default, short option
        {
            Option color = new Option("c", "color", "color", "set the color", "purple");
            CommandLine op = new CommandLine("usage ...");
            op.add(color);

            assertEquals("purple", color.getValue());
            String args[] = op.parse(new String[] {"-c", "green", "arg11", "arg12"});
            assertEquals(2, args.length);
            assertEquals("arg11", args[0]);
            assertEquals("arg12", args[1]);
            assertEquals("green", color.getValue());
        }

        // without default, long option
        {
            Option color = new Option("c", "color", "color", "set the color");
            CommandLine op = new CommandLine("usage ...");
            op.add(color);

            assertTrue(color.getValue() == null);
            String args[] = op.parse(new String[] {"--color", "blue", "arg1", "arg2"});
            assertEquals(2, args.length);
            assertEquals("arg1", args[0]);
            assertEquals("arg2", args[1]);
            assertEquals("blue", color.getValue());
        }

        // with default, long option
        {
            Option color = new Option("c", "color", "color", "set the color", "purple");
            CommandLine op = new CommandLine("usage ...");
            op.add(color);

            assertEquals("purple", color.getValue());
            String args[] = op.parse(new String[] {"--color", "green", "arg11", "arg12"});
            assertEquals(2, args.length);
            assertEquals("arg11", args[0]);
            assertEquals("arg12", args[1]);
            assertEquals("green", color.getValue());
        }

        // with default, long option with =
        {
            Option color = new Option("c", "color", "color", "set the color", "purple");
            CommandLine op = new CommandLine("usage ...");
            op.add(color);

            assertEquals("purple", color.getValue());
            String args[] = op.parse(new String[] {"--color=green", "arg11", "arg12"});
            assertEquals(2, args.length);
            assertEquals("arg11", args[0]);
            assertEquals("arg12", args[1]);
            assertEquals("green", color.getValue());
        }
    }

    /**
     * test the array option
     */
    public void testArrayOption() {

        // without default, short option
        {
            ArrayOption color = new ArrayOption("c", "color", "color", "set the colors");
            CommandLine op = new CommandLine("usage ...");
            op.add(color);

            assertTrue(color.getValue() == null);
            String args[] = op.parse(new String[] {"-c", "blue,green", "arg1", "arg2"});
            assertEquals(2, args.length);
            assertEquals("arg1", args[0]);
            assertEquals("arg2", args[1]);
            assertEquals(2, color.getValue().length);
            assertEquals("blue", color.getValue()[0]);
            assertEquals("green", color.getValue()[1]);
        }

        // with default, short option
        {
            ArrayOption color =
                new ArrayOption("c", "color", "color", "set the colors", new String[] {"purple"});
            CommandLine op = new CommandLine("usage ...");
            op.add(color);

            assertEquals(1, color.getValue().length);
            assertEquals("purple", color.getValue()[0]);
            String args[] = op.parse(new String[] {"-c", "green", "arg11", "arg12"});
            assertEquals(2, args.length);
            assertEquals("arg11", args[0]);
            assertEquals("arg12", args[1]);
            assertEquals(1, color.getValue().length);
            assertEquals("green", color.getValue()[0]);
        }

        // without default, long option
        {
            ArrayOption color = new ArrayOption("c", "color", "color", "set the colors");
            CommandLine op = new CommandLine("usage ...");
            op.add(color);

            assertTrue(color.getValue() == null);
            String args[] = op.parse(new String[] {"--color", "blue,magenta", "arg1", "arg2"});
            assertEquals(2, args.length);
            assertEquals("arg1", args[0]);
            assertEquals("arg2", args[1]);
            assertEquals(2, color.getValue().length);
            assertEquals("blue", color.getValue()[0]);
            assertEquals("magenta", color.getValue()[1]);
        }

        // with default, long option
        {
            ArrayOption color =
                new ArrayOption("c", "color", "color", "set the color", new String[] {"purple",
                    "pink"});
            CommandLine op = new CommandLine("usage ...");
            op.add(color);

            assertEquals(2, color.getValue().length);
            assertEquals("purple", color.getValue()[0]);
            assertEquals("pink", color.getValue()[1]);
            String args[] = op.parse(new String[] {"--color", "green", "arg11", "arg12"});
            assertEquals(2, args.length);
            assertEquals("arg11", args[0]);
            assertEquals("arg12", args[1]);
            assertEquals(1, color.getValue().length);
            assertEquals("green", color.getValue()[0]);
        }

        // with default, long option with =
        {
            ArrayOption color =
                new ArrayOption("c", "color", "color", "set the color", new String[] {"purple"});
            CommandLine op = new CommandLine("usage ...");
            op.add(color);

            assertEquals(1, color.getValue().length);
            assertEquals("purple", color.getValue()[0]);
            String args[] = op.parse(new String[] {"--color=green,yellow", "arg11", "arg12"});
            assertEquals(2, args.length);
            assertEquals("arg11", args[0]);
            assertEquals("arg12", args[1]);
            assertEquals(2, color.getValue().length);
            assertEquals("green", color.getValue()[0]);
            assertEquals("yellow", color.getValue()[1]);
        }
    }

    /**
     * test invalid toggle option definitions
     */
    public void testBadToggleOption() {

        try {
            new ToggleOption(null, null, "invalid");
            fail("should have thrown IllegalArgumentException");
        }
        catch (IllegalArgumentException _) {
            // expected
        }

        try {
            new ToggleOption("", "", "invalid");
            fail("should have thrown IllegalArgumentException");
        }
        catch (IllegalArgumentException _) {
            // expected
        }

        try {
            new ToggleOption("ve", "verbose", "invalid");
            fail("should have thrown IllegalArgumentException");
        }
        catch (IllegalArgumentException _) {
            // expected
        }

        try {
            new ToggleOption("v", "w", "invalid");
            fail("should have thrown IllegalArgumentException");
        }
        catch (IllegalArgumentException _) {
            // expected
        }

        try {
            new ToggleOption("v", "verbose", null);
            fail("should have thrown IllegalArgumentException");
        }
        catch (IllegalArgumentException _) {
            // expected
        }

        try {
            new ToggleOption("v", "verbose", "");
            fail("should have thrown IllegalArgumentException");
        }
        catch (IllegalArgumentException _) {
            // expected
        }

        // valid
        new ToggleOption("v", null, "help for verbose");
        new ToggleOption(null, "verbose", "help for verbose");

    }

    /**
     * test the help function
     */
    public void testHelp() {

        String NL = System.getProperty("line.separator", "\n");

        // test toggle option
        {
            CommandLine op = new CommandLine("usage...");
            ToggleOption verbose = new ToggleOption("v", "verbose", "set verbose output");
            op.add(verbose);
            ToggleOption verboseS = new ToggleOption("w", null, "set verbose output (short only)");
            op.add(verboseS);
            ToggleOption verboseL =
                new ToggleOption(null, "werbose", "set verbose output (long only)");
            op.add(verboseL);

            String exp =
                "usage..." + NL + "    -v --verbose : set verbose output" + NL
                    + "    -w : set verbose output (short only)" + NL
                    + "    --werbose : set verbose output (long only)" + NL;

            assertEquals(exp, op.getHelp());
        }

        // test String option
        {
            CommandLine op = new CommandLine("usage...");
            Option color = new Option("c", "color", "COLOR", "set color output", "yellow");
            op.add(color);
            Option colorS =
                new Option("d", null, "COLOR", "set color output (short only)", "yellow");
            op.add(colorS);
            Option colorL =
                new Option(null, "dolor", "COLOR", "set color output (long only)", "yellow");
            op.add(colorL);
            Option colorD =
                new Option("e", "eolor", "COLOR", "set color output (no default)", null);
            op.add(colorD);

            String exp =
                "usage..." + NL
                    + "    -c <COLOR>, --color=<COLOR> : set color output (default to yellow)" + NL
                    + "    -d <COLOR> : set color output (short only) (default to yellow)" + NL
                    + "    --dolor=<COLOR> : set color output (long only) (default to yellow)" + NL
                    + "    -e <COLOR>, --eolor=<COLOR> : set color output (no default)" + NL;
            // System.err.println(exp);
            // System.out.println(op.getHelp());
            assertEquals(exp, op.getHelp());
        }

        // test Array option
        {
            CommandLine op = new CommandLine("usage...");
            ArrayOption color =
                new ArrayOption("c", "color", "COLOR", "set color output", new String[] {"yellow"});
            op.add(color);
            ArrayOption colorS =
                new ArrayOption("d", null, "COLOR", "set color output (short only)",
                    new String[] {"yellow"});
            op.add(colorS);
            ArrayOption colorL =
                new ArrayOption(null, "dolor", "COLOR", "set color output (long only)",
                    new String[] {"yellow", "green"});
            op.add(colorL);
            ArrayOption colorD =
                new ArrayOption("e", "eolor", "COLOR", "set color output (no default)", null);
            op.add(colorD);

            String exp =
                "usage..."
                    + NL
                    + "    -c <COLOR,...>, --color=<COLOR,...> : set color output (default to yellow)"
                    + NL
                    + "    -d <COLOR,...> : set color output (short only) (default to yellow)"
                    + NL
                    + "    --dolor=<COLOR,...> : set color output (long only) (default to yellow,green)"
                    + NL
                    + "    -e <COLOR,...>, --eolor=<COLOR,...> : set color output (no default)"
                    + NL;

            assertEquals(exp, op.getHelp());
        }

    }

}
