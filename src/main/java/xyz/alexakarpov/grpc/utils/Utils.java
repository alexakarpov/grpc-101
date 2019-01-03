package xyz.alexakarpov.grpc.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Utils {

    private static DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss/SSS");

    public static String timeStr() {
        return fmt.print(DateTime.now());
    }
}
