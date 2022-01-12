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

    public static String prettyLocation(Double latitude, Double longitude) {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append(latitude);
        sb.append(';');
        sb.append(longitude);
        sb.append(')');
        return sb.toString();
    }
}
