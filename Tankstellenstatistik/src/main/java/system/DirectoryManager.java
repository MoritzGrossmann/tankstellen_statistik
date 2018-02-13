package system;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class DirectoryManager {

    public String getAppdata()
    {
        return System.getenv("APPDATA");
    }

    public String getEtc()
    {
        return "/etc";
    }
}
