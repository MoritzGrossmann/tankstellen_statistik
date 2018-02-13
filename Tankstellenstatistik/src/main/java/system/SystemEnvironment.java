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

        return (OS.indexOf("win") >= 0);

    }

    private boolean isMac() {

        return (OS.indexOf("mac") >= 0);

    }

    private boolean isUnix() {

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );

    }

    private boolean isSolaris() {

        return (OS.indexOf("sunos") >= 0);
    }
}

