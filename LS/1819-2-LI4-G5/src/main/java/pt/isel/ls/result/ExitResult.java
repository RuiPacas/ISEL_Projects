package pt.isel.ls.result;

import java.io.PrintStream;
import pt.isel.ls.http.HttpStatusCode;

public class ExitResult implements Result {


    @Override
    public void showPlain(PrintStream out) {

    }

    @Override
    public void showHtml(PrintStream out) {

    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatusCode.Ok;
    }
}
