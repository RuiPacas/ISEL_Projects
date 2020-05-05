package pt.isel.ls.request;

import java.util.HashMap;
import pt.isel.ls.exception.NoSuchCommandException;

public class Headers {

    private static final String EQUAL_SEPARATOR = ":";
    private static final String PAIR_SEPARATOR = "\\|";
    private static final String TYPE_SEPARATOR = "/";
    private HashMap<String, String> headersMap = new HashMap<>();

    public Headers(String header) {
        for (String h : header.split(PAIR_SEPARATOR)) {
            String[] values = h.split(EQUAL_SEPARATOR);
            if (values.length != 2) {
                throw new NoSuchCommandException("Invalid Header");
            }
            String value = values[1];
            if (value.contains(TYPE_SEPARATOR)) {
                value = value.split(TYPE_SEPARATOR)[1];
            }
            headersMap.put(values[0], value);
        }
    }

    public static Headers defaultHeader() {
        return new Headers("accept:text/plain");
    }

    public HashMap<String, String> getHeadersMap() {
        return headersMap;
    }
}
