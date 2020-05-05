package pt.isel.ls.result.classes;

import static pt.isel.ls.serializers.html.ElementNode.anchor;
import static pt.isel.ls.serializers.html.ElementNode.body;
import static pt.isel.ls.serializers.html.ElementNode.div;
import static pt.isel.ls.serializers.html.ElementNode.form;
import static pt.isel.ls.serializers.html.ElementNode.h1;
import static pt.isel.ls.serializers.html.ElementNode.h2;
import static pt.isel.ls.serializers.html.ElementNode.head;
import static pt.isel.ls.serializers.html.ElementNode.html;
import static pt.isel.ls.serializers.html.ElementNode.meta;
import static pt.isel.ls.serializers.html.ElementNode.paragraph;
import static pt.isel.ls.serializers.html.ElementNode.requiredInput;
import static pt.isel.ls.serializers.html.ElementNode.style;
import static pt.isel.ls.serializers.html.ElementNode.submit;
import static pt.isel.ls.serializers.html.ElementNode.table;
import static pt.isel.ls.serializers.html.ElementNode.td;
import static pt.isel.ls.serializers.html.ElementNode.text;
import static pt.isel.ls.serializers.html.ElementNode.th;
import static pt.isel.ls.serializers.html.ElementNode.title;
import static pt.isel.ls.serializers.html.ElementNode.tr;
import static pt.isel.ls.serializers.html.ElementNode.warningText;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.model.Class;
import pt.isel.ls.result.Result;
import pt.isel.ls.serializers.html.ElementNode;
import pt.isel.ls.serializers.html.Node;

public class GetClassesForACourseResult implements Result {

    private static final String TITLE = "GetClassesForACourse";
    private String message = null;
    private LinkedList<Class> classes;
    private String acr;

    public GetClassesForACourseResult(LinkedList<Class> classes, String acr) {
        this.classes = classes;
        this.acr = acr;
    }

    public GetClassesForACourseResult(LinkedList<Class> classes, String acr, String message) {
        this(classes, acr);
        this.message = message;
    }

    @Override
    public void showPlain(PrintStream out) {
        if (message != null) {
            out.println(message);
        }
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
                        h2(text("Create class for " + acr)),
                        warningText(message != null ? message : ""),
                        div(
                                buildForm()
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
        if (classes.isEmpty()) {
            return paragraph();
        }
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
                            td(text(c.getIdentifier())),
                            td(anchor("href", "/courses/" + c.getCourseAcronym() + "/classes/"
                                            + c.getSemesterRepresent(),
                                    text(c.getSemesterRepresent()))),
                            td(
                                    anchor("href", "/courses/" + c.getCourseAcronym(),
                                            text(c.getCourseAcronym())
                                    )
                            )
                    ));
        }
        return list;
    }

    private Node buildForm() {
        return form("POST", "/courses/" + acr + "/classes",
                text("Class Id : "),
                requiredInput("text", "num"),
                text("Semester : "),
                requiredInput("text", "sem"),
                submit()
        );
    }
}
