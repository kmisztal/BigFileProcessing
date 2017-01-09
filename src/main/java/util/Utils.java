package util;

import io.Delimiter;

/**
 * Created by Andrzej on 2016-12-19.
 */
public class Utils {

    public static String getDelimiter(String line) {
        for(Delimiter delimiter : Delimiter.values()) {
            if (line.contains(delimiter.getDelimiter()))
                return delimiter.getDelimiter();
        }
        throw new RuntimeException("Cannot find delimiter");
    }
}
