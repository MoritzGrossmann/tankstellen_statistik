package config;

import program.cli.CliHandler;
import program.information.Logger;
import system.DirectoryManager;
import system.NotSupportedException;
import system.SystemEnvironment;

import java.io.*;
import java.util.Properties;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public abstract class Config {

    private final String CONFIG_FILE = "tankstellenstatistik.conf";

    private final String CONFIG_FOLDER = "tankstellenstatistik";

    private final Logger logger = new CliHandler();

    private SystemEnvironment systemEnvironment = new SystemEnvironment();

    protected Properties getConfig() throws NotSupportedException, IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(getConfigFile());
        properties.load(fileInputStream);
        fileInputStream.close();
        return properties;
    }

    public File getConfigFile() throws NotSupportedException, IOException {
        File f = new File(String.format("%s/%s", getConfigFolder(), CONFIG_FILE));
        if (f.exists()) return f;
        logger.printWarning("no configuration file found");
        return createConfigFile();
    }

    private File createConfigFile() throws IOException, NotSupportedException{
        File configFolder = new File(getConfigFolder());
        if (!configFolder.exists()) {
            logger.printInformation("no config folder found");
            logger.printInformation(String.format("generate config folder %s", configFolder.getAbsolutePath()));
                if (!configFolder.mkdirs())
                {
                    logger.printError(String.format("cannot create config folder %s", configFolder.getAbsolutePath()));
                } else {
                    logger.printInformation("config folder created successful");
                }
            }
        File file = new File(String.format("%s/%s", getConfigFolder(), CONFIG_FILE));
        logger.printInformation(String.format("create file %s", file.getAbsolutePath()));
        boolean newFile = file.createNewFile();
        if (newFile) {
            logger.printInformation("config file created successful");
            initialize(file);
        }
        return file;
    }

    private void initialize(File file) throws IOException
    {
        BufferedWriter br = new BufferedWriter(new FileWriter(file, true));
        br.write(String.format("%s=", ApiConfig.API_KEY_PROPERTY));
        br.newLine();
        br.write(String.format("%s=", GasstationConfig.GASSTATIONS_PROPERTY));
        br.newLine();
        br.write(String.format("%s=", DatabaseConfig.HOST_PROPERTY));
        br.newLine();
        br.write(String.format("%s=", DatabaseConfig.PORT_PROPERTY));
        br.newLine();
        br.write(String.format("%s=", DatabaseConfig.NAME_PROPERTY));
        br.newLine();
        br.write(String.format("%s=", DatabaseConfig.USER_PROPERTY));
        br.newLine();
        br.write(String.format("%s=", DatabaseConfig.PASSWORD_PROPERTY));
        br.newLine();
        br.write(String.format("%s=", DatabaseConfig.TIMEZONE_PROPERTY));
        br.flush();
        br.close();

    }

    private String getConfigFolder() throws NotSupportedException
    {
        switch (systemEnvironment.getOperatingSystem())
        {
            case WINDOWS:
                return String.format("%s/%s", new DirectoryManager().getAppdata(), CONFIG_FOLDER);
            case MAC:
                throw new NotSupportedException("Mac is not supported yet");
            case UNIX:
                return String.format("%s/%s", new DirectoryManager().getEtc(), CONFIG_FOLDER);
            case SOLARIS:
                throw new NotSupportedException("Solaris is not supported yet");
            case UNKNOWN:
                throw new NotSupportedException("Unknown OS is not supported");
        }
        throw new NotSupportedException("Unknown OS is not supported");
    }

    public abstract void configurate(CliHandler cliHandler) throws NotSupportedException, IOException;
}
