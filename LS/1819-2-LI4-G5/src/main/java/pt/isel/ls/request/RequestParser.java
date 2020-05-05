package pt.isel.ls.request;

import pt.isel.ls.exception.NoSuchCommandException;
import pt.isel.ls.exception.NoSuchMethodException;

public class RequestParser {

    private static final String SEPARATOR = " ";
    private Method method;
    private Path path;
    private Parameters parameters = null;
    private Headers headers;

    public RequestParser(String line) {
        String[] info = line.split(SEPARATOR);
        int size = info.length;
        //There are 2 mandatory fields in a request, and 2 others that are optional
        if (size > 1 && size <= 4) {
            try {
                method = Method.valueOf(info[0]);
            } catch (IllegalArgumentException e) {
                throw new NoSuchMethodException("Invalid Method");
            }
            path = new Path(info[1]);
            switch (size) {
                case 2:
                    headers = Headers.defaultHeader();
                    break;
                case 3:
                    if (Parameters.isValidParameter(info[2])) {
                        parameters = new Parameters(info[2]);
                        headers = Headers.defaultHeader();
                    } else {
                        headers = new Headers(info[2]);
                    }
                    break;
                default:
                    headers = new Headers(info[2]);
                    parameters = new Parameters(info[3]);
                    break;
            }
        } else {
            throw new NoSuchCommandException("COMMAND SIZE <1 OR >4");
        }
    }

    public Method getMethod() {
        return method;
    }

    public Path getPath() {
        return path;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public Headers getHeaders() {
        return headers;
    }

}
