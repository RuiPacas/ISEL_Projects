package pt.isel.ls.result;

import java.io.PrintStream;
import pt.isel.ls.http.HttpStatusCode;

public class Message implements Result {

    String result;
    private HttpStatusCode statusCode;

    public Message(String result, HttpStatusCode statusCode) {
        this.result = result;
        this.statusCode = statusCode;
    }

    public String getResult() {
        return result;
    }

    @Override
    public void showPlain(PrintStream out) {
        out.println(result);
    }

    @Override
    public void showHtml(PrintStream out) {
        out.println(result);
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

}
