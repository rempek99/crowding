package pl.remplewicz.util;

import java.time.ZonedDateTime;
import java.util.Locale;

public class PrettyStringFormatter {

    private PrettyStringFormatter(){}

    public static String prettyDate(ZonedDateTime dateTime) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(Locale.ROOT,"%02d",dateTime.getDayOfMonth()));
        sb.append('-');
        sb.append(String.format(Locale.ROOT,"%02d",dateTime.getMonthValue()));
        sb.append('-');
        sb.append(dateTime.getYear());
        sb.append(' ');
        sb.append(String.format(Locale.ROOT,"%02d",dateTime.getHour()));
        sb.append(':');
        sb.append(String.format(Locale.ROOT,"%02d",dateTime.getMinute()));
        return sb.toString();
    }
}
