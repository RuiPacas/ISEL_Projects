package pt.isel.ls.result.programmes;

import static pt.isel.ls.serializers.html.ElementNode.anchor;
import static pt.isel.ls.serializers.html.ElementNode.body;
import static pt.isel.ls.serializers.html.ElementNode.div;
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
import pt.isel.ls.model.Programme;
import pt.isel.ls.result.Result;
import pt.isel.ls.serializers.html.ElementNode;

public class GetProgrammeByPidResult implements Result {


    private static final String TITLE = "GetProgrammeByPid";
    private Programme programme;

    public GetProgrammeByPidResult(Programme programme) {
        this.programme = programme;
    }

    @Override
    public void showPlain(PrintStream out) {
        out.println(programme.toString());
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
                        div(
                                buildProgramme()
                        ),

                        anchor("href", "/programmes", text("PROGRAMMES")),

                        anchor("href", "/", text("HOME"))
                )
        ).print(out, 0);
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatusCode.Ok;
    }

    private ElementNode buildProgramme() {
        return ul(
                li(text("Programme Id = " + programme.getProgrammeId())),
                li(text("Programme Name = " + programme.getName())),
                li(text("Number of Semesters = " + programme.getNumberOfSemesters())),
                li(anchor("href", "/programmes/" + programme.getProgrammeId() + "/courses",
                        text("Courses")))
        );
    }
}
