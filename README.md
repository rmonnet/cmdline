
## A simple java command line options library

The `org.rcm.cmdline` package provides a simple but powerful 
tool for parsing command line options in Java. It is inspired by the 
[python](http://www.python.org) [optparse](http://docs.python.org/lib/module-optparse.html) module.

The `cmdline` library supports short, posix style, options such as `-h` and long,
 gnu style, options such as `--help`. Options can be toggles (set, not-set),
 represent a value or a list of values.

### License and attribution

This code was originally developed by Robert Monnet (rcmonnet@acm.org).

The library is released under the [Apache 2.0](http://www.opensource.org/licenses/Apache-2.0)
license.

### Code Example

The following code shows how to set up a couple of options:

	// define the command line parser including the help text
	CommandLine cl =
   		new CommandLine("Usage org.rcm.Example [options] <name>");
	
	// define options as either ToggleOption, Option or ArrayOption
	Option fileOption =
   		cl.addOption("f", "file", "FILE",
        			 "write report to FILE or stdout if not specified");
	ToggleOption quietOption =
		cl.addToggleOption("q", "quiet",
						   "don't print status messages to stdout");
	ToggleOption helpOption =
   		cl.addToggleOption("h", "help", "display this help text");
	
	// now parse the command line for options, regular parameters will be
	// returned for further processing
	// options settings are available through the dfferent options
	try {
   		String[] pargs = cl.parse(args);
   		...
	} catch (CommandLineException ex) {
   		System.out.println("invalid command line: " + ex.getMessage());
   		System.out.println(cl.getHelp());
}

If the command line is invalid a `CommandLineException` will be thrown.
This exception is unchecked so you can let it propagate to the top or catch it and
display the help text. The help text shows the program usage and all the associated options.
As you add options to the command line, a line is appended to the help describing that option.
This is available by calling `CommandLine#getHelp()`.

For the example above, the help text will look like:

	Usage org.rcm.Example [options] <name>
    	-f <FILE>, --file=<FILE> : write report to FILE or stdout if not specified
    	-q --quiet : don't print status messages to stdout
    	-h --help : display this help text

To find if a variable has been set, just call `Option#isSet()`. For Options
having a value, `Option` and `ArrayOption`, it can be retrieved
using `Option#getValue()` and `Option#getValues()` respectively.
Options with value can be defined with a default value or not. 
If the option was not set on the command line and does not have a default value then a null
is returned.

To define options, use the following constructors:

1.    create an on/off option, no value is associated with it.
	
		ToggleOption(String shortName, String longName, String help)

2.	create an option with a single value. the variable name is used in the help text to
	designate the option value. The option is always set and is initialized with the default
	value

		Option(String shortName, String longName, String variableName, 
			   String help, String defaultValue)

3.	create an option with a single value. the variable name is used in the help text to
	designate the option value. The option is not set by default

		Option(String shortName, String longName, String variableName, 
			   String help)
			
4.	create an option with a set of values. the variable name is used in the help text to
	designate the option value. The option is always set and is initialized with the set of
	default values

		ArrayOption(String shortName, String longName, String variableName, 
					String help, String[] defaultValue)

5.	create an option with a set of values. the variable name is used in the help text to
	designate the option value. The option is not set by default

		ArrayOption(String shortName, String longName, String variableName, 
					String help)

Note that for all options, Short and Long names are
optional but at least one is required.

### Option Syntax

The option syntax follows the traditional posix style options `-` and gnu style options `--`:

Posix style options have only one letter, several options can be aggregated together behind a
single `-` as in most of unix commands. If the option requires a value then the option
needs to be the only option after the `-` and the value is separated from the option by a space.

Gnu style options have a minimum of two letters, options cannot be aggregate behind a single `--`.
If the option requires a value then it can be separated from the option by either a space
or an equal sign.

In a list of value, each value is separated by a comma, no spaces are allowed.

Here are a few examples of valid options:

- 	short toggle options

		ls -l -t -r

- 	short toggle options, compacted after a single `-`

		ls -ltr

- 	short option with a value

		process -f f.out

- 	short option with a list of values

		paint -c blue,red,white

- 	long toggle options

		process --quiet --stdout

- 	long option with a value

		process --file=f.out

- 	long option with a value

		process --file f.out

- 	long option with a list of values

		paint --colors=blue,red,white

- 	long option with a list of values

		paint --colors blue,red,white

### build the library

The library is setup for maven. There is no dependencies others than the JDK. 
To build the library jar you need to have [maven](http://maven.apache.org/) installed (at least version 2).
type:

	mvn package

To build the javadoc, type:

	mvn javadoc:jar

You will find the produced jar under `~/target`

