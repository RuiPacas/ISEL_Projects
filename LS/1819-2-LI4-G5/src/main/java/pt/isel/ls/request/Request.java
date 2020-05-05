package pt.isel.ls.request;

/***
 * Class responsible for creating a request,
 * which has a Method, a Path and one or more Parameters.
 * The components refered above are obtained via the String in the Constructor.
 */
public class Request {

    private Method method;
    private Path path;
    private Parameters parameters;
    private Headers headers;

    public Request(Method method, Path path, Parameters parameters, Headers headers) {
        this.method = method;
        this.path = path;
        this.parameters = parameters;
        this.headers = headers;
    }

    public Request(RequestParser request) {
        this.method = request.getMethod();
        this.path = request.getPath();
        this.parameters = request.getParameters();
        this.headers = request.getHeaders();
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
