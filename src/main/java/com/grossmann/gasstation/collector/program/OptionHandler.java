package com.grossmann.gasstation.collector.program;

import com.grossmann.gasstation.collector.App;
import org.apache.commons.cli.*;

/**
 * Created by mgrossmann on 14.02.2018.
 */
public class OptionHandler {

    public CommandLine parseOptions(String[] args) throws IllegalArgumentException
    {
        Options options = new Options();

        Option configure = new Option("c", "configure", false, "Create Configuration File");
        Option run = new Option("r", "run", false, "Run the Collector");
        Option database = new Option("d", "database", false, "Creates the Database based on configuration");
        Option purge = new Option("p", "purge", false, "Drop database tables or drop Configuration");

        configure.setRequired(false);
        run.setRequired(false);
        options.addOption(configure);
        options.addOption(run);
        options.addOption(database);
        options.addOption(purge);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            return cmd;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(App.PROGRAM_NAME, options);
            throw new IllegalArgumentException();
        }
    }
}
