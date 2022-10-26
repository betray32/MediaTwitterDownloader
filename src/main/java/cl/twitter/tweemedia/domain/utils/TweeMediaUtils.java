package cl.twitter.tweemedia.domain.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.io.FileUtils;

public class TweeMediaUtils {

    public static int writeMediaIntoFile(String url, String path) {
        try {
            String fileName = url.substring(url.lastIndexOf("/") + 1);

            /*
             * Last character must be slash
             */
            String validateLastCharacter = path.substring(path.length() - 1);
            if (!"/".equalsIgnoreCase(validateLastCharacter)) {
                path = path + "/";
            }

            FileUtils.copyURLToFile(new URL(url), new File(path + fileName));
            return 1;

        } catch (MalformedURLException e) {
            System.err.println("Error when downloading and writing, Detail > " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error when downloading and writing, Detail > " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

}
