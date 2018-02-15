package system;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class SystemEnvironment {

    private String OS = System.getProperty("os.name").toLowerCase();

    public OperatingSystem getOperatingSystem()
    {
        if (isWindows()) return OperatingSystem.WINDOWS;
        if (isMac()) return OperatingSystem.MAC;
        if (isUnix()) return OperatingSystem.UNIX;
        if (isSolaris()) return OperatingSystem.SOLARIS;
        return OperatingSystem.UNKNOWN;
    }

    private boolean isWindows() {

        return (OS.contains("win"));

    }

    private boolean isMac() {

        return (OS.contains("mac"));

    }

    private boolean isUnix() {

        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));

    }

    private boolean isSolaris() {

        return (OS.contains("sunos"));
    }
}

