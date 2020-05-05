package pt.isel.ls.result;

import static pt.isel.ls.serializers.html.ElementNode.anchor;
import static pt.isel.ls.serializers.html.ElementNode.body;
import static pt.isel.ls.serializers.html.ElementNode.h1;
import static pt.isel.ls.serializers.html.ElementNode.head;
import static pt.isel.ls.serializers.html.ElementNode.html;
import static pt.isel.ls.serializers.html.ElementNode.li;
import static pt.isel.ls.serializers.html.ElementNode.meta;
import static pt.isel.ls.serializers.html.ElementNode.style;
import static pt.isel.ls.serializers.html.ElementNode.text;
import static pt.isel.ls.serializers.html.ElementNode.title;
import static pt.isel.ls.serializers.html.ElementNode.ul;

import java.io.PrintStream;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.serializers.html.Node;

public class HomeResult implements Result {

    private static final String TITLE = "Home";


    @Override
    public void showPlain(PrintStream out) {
        out.println("Home");
    }

    @Override
    public void showHtml(PrintStream out) {
        html(
                head(
                        style(),
                        meta(),
                        title(text(TITLE))
                ),
                body(
                        h1(text(TITLE)),
                        showLinks()
                )
        ).print(out, 0);
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatusCode.Ok;
    }

    private Node showLinks() {
        return ul(
                li(anchor("href", "/courses", text("Courses"))),
                li(anchor("href", "/programmes", text("Programmes"))),
                li(anchor("href", "/teachers", text("Teachers"))),
                li(anchor("href", "/students", text("Students")))
        );
    }
}
