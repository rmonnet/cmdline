/**
 * 
 */
package org.rcm.cmdline;

import org.rcm.cmdline.CommandLine;
import org.rcm.cmdline.CommandLineException;
import org.rcm.cmdline.Option;
import org.rcm.cmdline.ToggleOption;
import static java.lang.System.out;

/**
 * This class shows example of using CommandLine.
 * 
 * @author Robert Monnet
 */
public class Example {

    /**
     * Show options passed at the comment line.
     * 
     * @param args
     *            the command line arguments.
     */
    public static void main(String[] args) {

        CommandLine cl = new CommandLine("Usage org.rcm.Example [options] <name>");
        Option file =
            cl.addOption("f", "file", "FILE", "write report to FILE or stdout if not specified");
        ToggleOption quiet =
            cl.addToggleOption("q", "quiet", "don't print status messages to stdout");
        ToggleOption help = cl.addToggleOption("h", "help", "display this help text");

        try {
            String[] pargs = cl.parse(args);

            if (help.isSet()) {
                out.print(cl.getHelp());
            }
            else {
                if (file.isSet()) {
                    out.println("--file=" + file.getValue());
                }
                if (quiet.isSet()) {
                    out.println("--quiet");
                }
                for (String arg : pargs) {
                    out.println(arg);
                }
            }
        }
        catch (CommandLineException ex) {
            out.println("invalid command line: " + ex.getMessage());
            out.println(cl.getHelp());
        }

    }

}
