package pt.isel.ls.request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.exception.ArgumentException;

/**
 * Class responsible for creating a Parameters, which as a map of all the parametersMap passed for
 * the actual request
 */
public class Parameters {

    private static final String SEQUENCE_SEPARATOR = "&";
    private static final char SPACE_SEPARATOR = '+';
    private static final String EQUAL_SEPARATOR = "=";
    private HashMap<String, LinkedList<String>> parametersMap = new HashMap<>();

    public Parameters(String s) {
        for (String param : s.split(SEQUENCE_SEPARATOR)) {
            if (param.contains(Character.toString(SPACE_SEPARATOR))) {
                char[] parametersChar = param.toCharArray();
                for (int i = 0; i < parametersChar.length; i++) {
                    if (parametersChar[i] == SPACE_SEPARATOR) {
                        parametersChar[i] = ' ';
                    }
                }
                param = new String(parametersChar);
            }
            String[] parameter = param.split(EQUAL_SEPARATOR);
            if (parameter.length == 2) {
                try {
                    String key = URLDecoder.decode(parameter[0], "UTF-8");
                    LinkedList<String> strings = parametersMap
                            .computeIfAbsent(key, k -> new LinkedList<>());
                    strings.add(URLDecoder.decode(parameter[1], "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new ArgumentException();
                }
            }
        }
    }

    public static boolean isValidParameter(String s) {
        String[] splitted = s.split(SEQUENCE_SEPARATOR);
        for (String str : splitted) {
            if (str.split(EQUAL_SEPARATOR).length != 2) {
                return false;
            }
        }
        return true;

    }

    public LinkedList<String> getParameter(String key) {
        return parametersMap.get(key);
    }

    public HashMap<String, LinkedList<String>> getParametersMap() {
        return parametersMap;
    }
}
