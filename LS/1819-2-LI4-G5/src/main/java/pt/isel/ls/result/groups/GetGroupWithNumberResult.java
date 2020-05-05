package pt.isel.ls.result.groups;

import static pt.isel.ls.serializers.html.ElementNode.anchor;
import static pt.isel.ls.serializers.html.ElementNode.body;
import static pt.isel.ls.serializers.html.ElementNode.div;
import static pt.isel.ls.serializers.html.ElementNode.form;
import static pt.isel.ls.serializers.html.ElementNode.h1;
import static pt.isel.ls.serializers.html.ElementNode.h2;
import static pt.isel.ls.serializers.html.ElementNode.head;
import static pt.isel.ls.serializers.html.ElementNode.html;
import static pt.isel.ls.serializers.html.ElementNode.meta;
import static pt.isel.ls.serializers.html.ElementNode.optionalInput;
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
import pt.isel.ls.model.Group;
import pt.isel.ls.model.Student;
import pt.isel.ls.result.Result;
import pt.isel.ls.serializers.html.ElementNode;
import pt.isel.ls.serializers.html.Node;

public class GetGroupWithNumberResult implements Result {

    private static final String TITLE = "GetGroupWithNumber";
    private LinkedList<Student> students;
    private Group group;
    private Class cls;
    private String message = null;

    public GetGroupWithNumberResult(LinkedList<Student> students, Class cls, Group group) {
        this.students = students;
        this.cls = cls;
        this.group = group;
    }

    public GetGroupWithNumberResult(LinkedList<Student> students,
            Class cls, Group group, String message) {
        this(students, cls, group);
        this.message = message;
    }


    @Override
    public void showPlain(PrintStream out) {
        if (message != null) {
            out.println(message);
        }
        for (Student s : students) {
            out.print(s.toString());
            out.print(", ");
            out.println(group.toString());
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
                                buildScGroup()
                        ),
                        h2(text("Add a students")),
                        warningText(message != null ? message : ""),
                        div(
                                buildForm()
                        ),
                        anchor("href", "/courses/" + cls.getCourseAcronym()
                                + "/classes/" + cls.getSemesterRepresent() + "/"
                                + cls.getIdentifier() + "/groups", text("GROUPS")),
                        anchor("href", "/", text("HOME"))
                )
        ).print(out, 0);
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatusCode.Ok;
    }

    private ElementNode buildScGroup() {
        return table(
                scGroupTableData(),
                tr(
                        th(text("Student Number")),
                        th(text("Class Identifier")),
                        th(text("Semester Represent")),
                        th(text("Course Acronym")),
                        th(text("Group Number"))
                )
        ).addAttribute("border", "1");
    }

    private ArrayList<Node> scGroupTableData() {
        ArrayList<Node> list = new ArrayList<>();
        for (Student s : students) {
            list.add(
                    tr(
                            td(
                                    anchor("href", "/students/" + s.getNumber(),
                                            text(s.getNumber() + ""))
                            ),
                            td(
                                    text(cls.getIdentifier())
                            ),
                            td(text(cls.getSemesterRepresent())),
                            td(text(cls.getCourseAcronym())),
                            td(text(group.getNumber() + ""))
                    )
            );
        }
        return list;
    }

    private Node buildForm() {
        return form("POST",
                "/courses/" + cls.getCourseAcronym()
                        + "/classes/" + cls.getSemesterRepresent() + "/" + cls.getIdentifier()
                        + "/groups/" + group.getNumber(),
                text("Student Number : "),
                optionalInput("text", "numStu"),
                text("Student Number : "),
                optionalInput("text", "numStu"),
                text("Student Number : "),
                optionalInput("text", "numStu"),
                submit()
        );
    }
}
