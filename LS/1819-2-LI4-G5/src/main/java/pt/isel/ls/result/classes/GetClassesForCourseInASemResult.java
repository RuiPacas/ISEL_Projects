package pt.isel.ls.result.classes;

import static pt.isel.ls.serializers.html.ElementNode.anchor;
import static pt.isel.ls.serializers.html.ElementNode.body;
import static pt.isel.ls.serializers.html.ElementNode.div;
import static pt.isel.ls.serializers.html.ElementNode.h1;
import static pt.isel.ls.serializers.html.ElementNode.head;
import static pt.isel.ls.serializers.html.ElementNode.html;
import static pt.isel.ls.serializers.html.ElementNode.meta;
import static pt.isel.ls.serializers.html.ElementNode.style;
import static pt.isel.ls.serializers.html.ElementNode.table;
import static pt.isel.ls.serializers.html.ElementNode.td;
import static pt.isel.ls.serializers.html.ElementNode.text;
import static pt.isel.ls.serializers.html.ElementNode.th;
import static pt.isel.ls.serializers.html.ElementNode.title;
import static pt.isel.ls.serializers.html.ElementNode.tr;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.model.Class;
import pt.isel.ls.result.Result;
import pt.isel.ls.serializers.html.ElementNode;
import pt.isel.ls.serializers.html.Node;

public class GetClassesForCourseInASemResult implements Result {

    private static final String TITLE = "GetClassesForCourseInASem";
    private LinkedList<Class> classes;

    public GetClassesForCourseInASemResult(LinkedList<Class> classes) {
        this.classes = classes;
    }

    @Override
    public void showPlain(PrintStream out) {
        for (Class c : classes) {
            out.println(c.toString());
        }
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
                                buildTable()
                        ),
                        anchor("href", "/", text("HOME"))
                )
        ).print(out, 0);

    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatusCode.Ok;
    }

    private ElementNode buildTable() {
        return table(
                courseTableData(),
                tr(
                        th(text("Class Identifier")),
                        th(text("Semester Represent ")),
                        th(text("Course Acronym"))
                )
        ).addAttribute("border", "1");
    }

    private ArrayList<Node> courseTableData() {
        ArrayList<Node> list = new ArrayList<>();
        for (Class c : classes) {
            list.add(
                    tr(
                            td(anchor("href", "/courses/" + c.getCourseAcronym() + "/classes/"
                                            + c.getSemesterRepresent() + "/" + c.getIdentifier(),
                                    text(c.getIdentifier()))),
                            td(text(c.getSemesterRepresent())),
                            td(
                                    anchor("href", "/courses/" + c.getCourseAcronym() + "/classes",
                                            text(c.getCourseAcronym())
                                    )
                            )
                    ));
        }
        return list;
    }
}

