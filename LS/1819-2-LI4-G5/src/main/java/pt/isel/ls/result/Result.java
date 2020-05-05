package pt.isel.ls.result;

import java.io.PrintStream;
import pt.isel.ls.http.HttpStatusCode;

public interface Result {

    void showPlain(PrintStream out);

    void showHtml(PrintStream out);

    HttpStatusCode getStatusCode();
}
