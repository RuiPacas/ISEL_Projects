package pt.isel.ls.result.users.teachers;

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
import pt.isel.ls.model.Teacher;
import pt.isel.ls.result.Result;
import pt.isel.ls.serializers.html.ElementNode;
import pt.isel.ls.serializers.html.Node;

public class GetTeachersInClassResult implements Result {

    private static final String TITLE = "GetTeachersInClass";
    private LinkedList<Teacher> teachers;
    private Class cls;
    private String message = null;

    public GetTeachersInClassResult(LinkedList<Teacher> teachers, Class cls) {
        this.teachers = teachers;
        this.cls = cls;
    }

    public GetTeachersInClassResult(LinkedList<Teacher> teachers,
            Class cls, String message) {
        this(teachers, cls);
        this.message = message;
    }

    @Override
    public void showPlain(PrintStream out) {
        if (message != null) {
            out.println(message);
        }
        for (Teacher t : teachers) {
            out.println(t.toString());
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
                        h2(text("Add Teacher to Class")),
                        warningText(message != null ? message : ""),
                        div(
                                buildForm()
                        ),
                        anchor("href", "/courses/" + cls.getCourseAcronym() + "/classes/"
                                        + cls.getSemesterRepresent() + "/" + cls.getIdentifier(),
                                text("CLASS")),
                        anchor("href", "/", text("HOME"))
                )
        ).print(out, 0);

    }

    private Node buildForm() {
        return form("POST",
                "/courses/" + cls.getCourseAcronym() + "/classes/"
                        + cls.getSemesterRepresent() + "/" + cls.getIdentifier() + "/teachers",
                text("Teacher Number : "),
                requiredInput("text", "numDoc"),
                submit()
        );
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatusCode.Ok;
    }

    private ElementNode buildTable() {
        if (teachers.isEmpty()) {
            return paragraph();
        }
        return table(
                courseTableData(),
                tr(
                        th(text("Teacher Name")),
                        th(text("Teacher Email")),
                        th(text("Teacher Number"))
                )
        ).addAttribute("border", "1");
    }

    private ArrayList<Node> courseTableData() {
        ArrayList<Node> list = new ArrayList<>();

        for (Teacher t : teachers) {
            list.add(
                    tr(
                            td(text(t.getName())),
                            td(text(t.getEmail())),
                            td(
                                    anchor("href", "/teachers/" + t.getNumber(),
                                            text(t.getNumber().toString()))
                            )
                    )
            );
        }
        return list;
    }
}
