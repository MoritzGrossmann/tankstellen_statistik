package program.cli;

import org.apache.commons.lang3.StringUtils;
import program.information.InputHandler;
import program.information.Logger;

import java.util.Calendar;
import java.util.Scanner;

/**
 * Created by mgrossmann on 14.02.2018.
 */
public class CliHandler implements Logger, InputHandler{

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    private static final String INFORMATION_PREFIX = "[INFO]";

    private static final String WARNING_PREFIX = "[WARNING]";

    private static final String ERROR_PREFIX = "[ERROR]";

    private final Scanner scanner = new Scanner(System.in);

    private String getTimeString()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime().toString();
    }

    public void printInformation(String text) {
        System.out.printf("%s %s: %s\n", getTimeString(), INFORMATION_PREFIX, text);
    }

    public void printWarning(String text) {
        System.out.printf("%s %s%s%s: %s\n", getTimeString(),ANSI_YELLOW, WARNING_PREFIX, ANSI_RESET, text);
    }

    public void printError(String text) {
        System.out.printf("%s %s%s%s: %s\n", getTimeString(), ANSI_RED, ERROR_PREFIX, ANSI_RESET,text);
    }

    public String input() {
        String input = "";
        while (input.trim().equals(StringUtils.EMPTY)) {
            if (scanner.hasNextLine()) {
                input = scanner.next();
            }
        }
        return input;
    }

    public String password()
    {
        String input = "";
        while (input.trim().equals(StringUtils.EMPTY)) {
            if (scanner.hasNextLine()) {
                input = scanner.next();
            }
        }
        return input;
    }

    public boolean question()
    {
        String answer = scanner.next();
        if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes"))
        {
            return true;
        } else {
            return false;
        }

    }
}
