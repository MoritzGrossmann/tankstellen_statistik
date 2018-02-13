package program;

import client.tankerkoenig.TankerkoenigClient;
import config.ApiConfig;
import config.ConfigCreatedException;
import system.NotSupportedException;

import java.io.IOException;

/**
 * Created by mgrossmann on 13.02.2018.
 */
public class Program {

    public static void main(String[] args)
    {
        try {
            TankerkoenigClient client = new TankerkoenigClient(new ApiConfig().getApiKey());


        } catch (NotSupportedException e) {
            System.out.println(e.getMessage());
        } catch (ConfigCreatedException e) {
            System.out.println("No config file found\nCreating...\nconfigure Program and restart");
        } catch (IOException e) {
            System.out.println("Error while reading or writing files: " + e.getMessage());
        }

    }
}
