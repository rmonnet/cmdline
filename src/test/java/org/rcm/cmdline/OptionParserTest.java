/**
 * 
 */
package org.rcm.cmdline;

import junit.framework.TestCase;

/**
 * @author Robert
 */
public class OptionParserTest
    extends TestCase {

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
        throws Exception {

        super.setUp();
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
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
            op.addValueOption("c", "color", "color", "help for color");
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
            op.addValueOption("c", "color", "color", "help for color");
            op.addToggleOption("d", "debug", "help for debug");
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
            op.addValueOption("c", "color", "color", "help for color");
            op.addToggleOption("d", "debug", "help for debug");
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
            op.addValueOption("c", "color", "color", "help for color");
            op.addToggleOption("d", "debug", "help for debug");
            // it is valid to specify an empty string option,
            // however this can only be doen through the = form
            op.parse(new String[] {"--color=", "-d", "arg1"});
        }

        // toggle option with value (long )
        {
            CommandLine op = new CommandLine("usage...");
            op.addValueOption("c", "color", "color", "help for color");
            op.addToggleOption("d", "debug", "help for debug");
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
            op.addValueOption("c", "color", "color", "help for color");
            op.addToggleOption("d", "debug", "help for debug");
            try {
                op.addToggleOption("c", "echo", "echo commands");
                fail("should have thrown IllegalArgumentException");
            }
            catch (IllegalArgumentException _) {
                // expected
            }
        }

        // test duplicate long
        {
            CommandLine op = new CommandLine("usage...");
            op.addValueOption("c", "color", "color", "help for color");
            op.addToggleOption("d", "debug", "help for debug");
            try {
                op.addToggleOption("e", "debug", "echo commands");
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
            CommandLine op = new CommandLine("usage ...");
            ToggleOption verbose = op.addToggleOption("v", "verbose", "set the output to verbose");

            String[] args = op.parse(new String[] {"-v", "arg1", "arg2"});
            assertEquals(2, args.length);
            assertEquals("arg1", args[0]);
            assertEquals("arg2", args[1]);
            assertTrue(verbose.isSet());
        }

        // test long option
        {
            CommandLine op = new CommandLine("usage ...");
            ToggleOption verbose = op.addToggleOption("v", "verbose", "set the output to verbose");

            String args[] = op.parse(new String[] {"--verbose", "arg1", "arg2"});
            assertEquals(2, args.length);
            assertEquals("arg1", args[0]);
            assertEquals("arg2", args[1]);
            assertTrue(verbose.isSet());
        }

        // test multiple short options
        {
            CommandLine op = new CommandLine("usage ...");
            ToggleOption verbose = op.addToggleOption("v", "verbose", "set the output to verbose");
            ToggleOption bandw =
                op.addToggleOption("b", "bandw", "set the display to black and white");
            ToggleOption unused = op.addToggleOption("u", "unused", "some unused option");

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
     * test the value option
     */
    public void testValueOption() {

        // without default, short option
        {
            CommandLine op = new CommandLine("usage ...");
            ValueOption color = op.addValueOption("c", "color", "color", "set the color");

            assertTrue(color.getValue() == null);
            String args[] = op.parse(new String[] {"-c", "blue", "arg1", "arg2"});
            assertEquals(2, args.length);
            assertEquals("arg1", args[0]);
            assertEquals("arg2", args[1]);
            assertEquals("blue", color.getValue());
        }

        // with default, short option
        {
            CommandLine op = new CommandLine("usage ...");
            ValueOption color = op.addValueOption("c", "color", "color", "set the color", "purple");

            assertEquals("purple", color.getValue());
            String args[] = op.parse(new String[] {"-c", "green", "arg11", "arg12"});
            assertEquals(2, args.length);
            assertEquals("arg11", args[0]);
            assertEquals("arg12", args[1]);
            assertEquals("green", color.getValue());
        }

        // without default, long option
        {
            CommandLine op = new CommandLine("usage ...");
            ValueOption color = op.addValueOption("c", "color", "color", "set the color");

            assertTrue(color.getValue() == null);
            String args[] = op.parse(new String[] {"--color", "blue", "arg1", "arg2"});
            assertEquals(2, args.length);
            assertEquals("arg1", args[0]);
            assertEquals("arg2", args[1]);
            assertEquals("blue", color.getValue());
        }

        // with default, long option
        {
            CommandLine op = new CommandLine("usage ...");
            ValueOption color = op.addValueOption("c", "color", "color", "set the color", "purple");

            assertEquals("purple", color.getValue());
            String args[] = op.parse(new String[] {"--color", "green", "arg11", "arg12"});
            assertEquals(2, args.length);
            assertEquals("arg11", args[0]);
            assertEquals("arg12", args[1]);
            assertEquals("green", color.getValue());
        }

        // with default, long option with =
        {
            CommandLine op = new CommandLine("usage ...");
            ValueOption color = op.addValueOption("c", "color", "color", "set the color", "purple");

            assertEquals("purple", color.getValue());
            String args[] = op.parse(new String[] {"--color=green", "arg11", "arg12"});
            assertEquals(2, args.length);
            assertEquals("arg11", args[0]);
            assertEquals("arg12", args[1]);
            assertEquals("green", color.getValue());
        }
    }

    /**
     * test the values option
     */
    public void testValuesOption() {

        // without default, short option
        {
            CommandLine op = new CommandLine("usage ...");
            ValuesOption color = op.addValuesOption("c", "color", "color", "set the colors");

            assertTrue(color.getValues() == null);
            String args[] = op.parse(new String[] {"-c", "blue,green", "arg1", "arg2"});
            assertEquals(2, args.length);
            assertEquals("arg1", args[0]);
            assertEquals("arg2", args[1]);
            assertEquals(2, color.getValues().length);
            assertEquals("blue", color.getValues()[0]);
            assertEquals("green", color.getValues()[1]);
        }

        // with default, short option
        {
            CommandLine op = new CommandLine("usage ...");
            ValuesOption color =
                op.addValuesOption("c", "color", "color", "set the colors", new String[] {"purple"});

            assertEquals(1, color.getValues().length);
            assertEquals("purple", color.getValues()[0]);
            String args[] = op.parse(new String[] {"-c", "green", "arg11", "arg12"});
            assertEquals(2, args.length);
            assertEquals("arg11", args[0]);
            assertEquals("arg12", args[1]);
            assertEquals(1, color.getValues().length);
            assertEquals("green", color.getValues()[0]);
        }

        // without default, long option
        {
            CommandLine op = new CommandLine("usage ...");
            ValuesOption color = op.addValuesOption("c", "color", "color", "set the colors");

            assertTrue(color.getValues() == null);
            String args[] = op.parse(new String[] {"--color", "blue,magenta", "arg1", "arg2"});
            assertEquals(2, args.length);
            assertEquals("arg1", args[0]);
            assertEquals("arg2", args[1]);
            assertEquals(2, color.getValues().length);
            assertEquals("blue", color.getValues()[0]);
            assertEquals("magenta", color.getValues()[1]);
        }

        // with default, long option
        {
            CommandLine op = new CommandLine("usage ...");
            ValuesOption color =
                op.addValuesOption("c", "color", "color", "set the color", new String[] {"purple",
                    "pink"});

            assertEquals(2, color.getValues().length);
            assertEquals("purple", color.getValues()[0]);
            assertEquals("pink", color.getValues()[1]);
            String args[] = op.parse(new String[] {"--color", "green", "arg11", "arg12"});
            assertEquals(2, args.length);
            assertEquals("arg11", args[0]);
            assertEquals("arg12", args[1]);
            assertEquals(1, color.getValues().length);
            assertEquals("green", color.getValues()[0]);
        }

        // with default, long option with =
        {
            CommandLine op = new CommandLine("usage ...");
            ValuesOption color =
                op.addValuesOption("c", "color", "color", "set the color", new String[] {"purple"});

            assertEquals(1, color.getValues().length);
            assertEquals("purple", color.getValues()[0]);
            String args[] = op.parse(new String[] {"--color=green,yellow", "arg11", "arg12"});
            assertEquals(2, args.length);
            assertEquals("arg11", args[0]);
            assertEquals("arg12", args[1]);
            assertEquals(2, color.getValues().length);
            assertEquals("green", color.getValues()[0]);
            assertEquals("yellow", color.getValues()[1]);
        }
    }

    /**
     * test invalid toggle option definitions
     */
    public void testBadToggleOption() {

        try {
            CommandLine op = new CommandLine("usage ...");
            op.addToggleOption(null, null, "invalid");
            fail("should have thrown IllegalArgumentException");
        }
        catch (IllegalArgumentException _) {
            // expected
        }

        try {
            CommandLine op = new CommandLine("usage ...");
            op.addToggleOption("", "", "invalid");
            fail("should have thrown IllegalArgumentException");
        }
        catch (IllegalArgumentException _) {
            // expected
        }

        try {
            CommandLine op = new CommandLine("usage ...");
            op.addToggleOption("ve", "verbose", "invalid");
            fail("should have thrown IllegalArgumentException");
        }
        catch (IllegalArgumentException _) {
            // expected
        }

        try {
            CommandLine op = new CommandLine("usage ...");
            op.addToggleOption("v", "w", "invalid");
            fail("should have thrown IllegalArgumentException");
        }
        catch (IllegalArgumentException _) {
            // expected
        }

        try {
            CommandLine op = new CommandLine("usage ...");
            op.addToggleOption("v", "verbose", null);
            fail("should have thrown IllegalArgumentException");
        }
        catch (IllegalArgumentException _) {
            // expected
        }

        try {
            CommandLine op = new CommandLine("usage ...");
            op.addToggleOption("v", "verbose", "");
            fail("should have thrown IllegalArgumentException");
        }
        catch (IllegalArgumentException _) {
            // expected
        }

        // valid
        CommandLine op = new CommandLine("usage ...");
        op.addToggleOption("v", null, "help for verbose");
        op.addToggleOption(null, "verbose", "help for verbose");

    }

    /**
     * test the help function
     */
    public void testHelp() {

        String NL = System.getProperty("line.separator", "\n");

        // test toggle option
        {
            CommandLine op = new CommandLine("usage...");
            op.addToggleOption("v", "verbose", "set verbose output");
            op.addToggleOption("w", null, "set verbose output (short only)");
            op.addToggleOption(null, "werbose", "set verbose output (long only)");

            String exp =
                "usage..." + NL + "    -v --verbose : set verbose output" + NL
                    + "    -w : set verbose output (short only)" + NL
                    + "    --werbose : set verbose output (long only)" + NL;

            assertEquals(exp, op.getHelp());
        }

        // test String option
        {
            CommandLine op = new CommandLine("usage...");
            op.addValueOption("c", "color", "COLOR", "set color output", "yellow");
            op.addValueOption("d", null, "COLOR", "set color output (short only)", "yellow");
            op.addValueOption(null, "dolor", "COLOR", "set color output (long only)", "yellow");
            op.addValueOption("e", "eolor", "COLOR", "set color output (no default)", null);

            String exp =
                "usage..." + NL
                    + "    -c <COLOR>, --color=<COLOR> : set color output (default to yellow)" + NL
                    + "    -d <COLOR> : set color output (short only) (default to yellow)" + NL
                    + "    --dolor=<COLOR> : set color output (long only) (default to yellow)" + NL
                    + "    -e <COLOR>, --eolor=<COLOR> : set color output (no default)" + NL;
            assertEquals(exp, op.getHelp());
        }

        // test Array option
        {
            CommandLine op = new CommandLine("usage...");
            op.addValuesOption("c", "color", "COLOR", "set color output", new String[] {"yellow"});
            op.addValuesOption("d", null, "COLOR", "set color output (short only)",
                new String[] {"yellow"});
            op.addValuesOption(null, "dolor", "COLOR", "set color output (long only)",
                new String[] {"yellow", "green"});
            op.addValuesOption("e", "eolor", "COLOR", "set color output (no default)", null);

            String exp =
                "usage...\n"
                    + "    -c <COLOR,...>, --color=<COLOR,...> : set color output (default to yellow)\n"
                    + "    -d <COLOR,...> : set color output (short only) (default to yellow)\n"
                    + "    --dolor=<COLOR,...> : set color output (long only) (default to yellow,green)\n"
                    + "    -e <COLOR,...>, --eolor=<COLOR,...> : set color output (no default)\n";

            assertEquals(exp, op.getHelp());

        }

    }

}
