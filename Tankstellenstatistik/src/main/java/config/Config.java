package config;

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

    private SystemEnvironment systemEnvironment = new SystemEnvironment();

    protected Properties getConfig() throws NotSupportedException, ConfigCreatedException, IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(getConfigFile());
        properties.load(fileInputStream);
        fileInputStream.close();
        return properties;
    }

    private File getConfigFile() throws NotSupportedException, IOException, ConfigCreatedException {
        File f = new File(String.format("%s/%s", getConfigFolder(), CONFIG_FILE));
        if (f.exists()) return f;
        return createConfigFile();
    }

    private File createConfigFile() throws IOException, NotSupportedException, ConfigCreatedException{
        File configFolder = new File(getConfigFolder());
        if (!configFolder.exists()) configFolder.mkdirs();

        File file = new File(String.format("%s/%s", getConfigFolder(), CONFIG_FILE));
        boolean newFile = file.createNewFile();
        if (newFile) initialize(file);
        throw new ConfigCreatedException();
    }

    private void initialize(File file) throws IOException
    {
        BufferedWriter br = new BufferedWriter(new FileWriter(file, true));
        br.write(String.format("%s=", ApiConfig.API_KEY_PROPERTY));
        br.newLine();
        br.write("GASSTATIONS=");
        br.newLine();
        br.write("DATABASE_HOST=");
        br.newLine();
        br.write("DATABASE_PORT=");
        br.newLine();
        br.write("DATABASE_NAME=");
        br.newLine();
        br.write("DATABASE_USER=");
        br.newLine();
        br.write("DATABASE_PASSWORD=");
        br.newLine();
        br.write("DATABASE_TIMEZONE=");
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
}
