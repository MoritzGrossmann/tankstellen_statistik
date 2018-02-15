package program.cli;

/**
 * Created by mgrossmann on 14.02.2018.
 */
public interface Logger {

    void printInformation(String text);

    void printWarning(String text);

    void printError(String text);

    void printSuccess(String text);

    void printOption(String text);
}
