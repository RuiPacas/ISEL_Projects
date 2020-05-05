package pt.isel.ls.result.users.students;

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
import pt.isel.ls.model.Student;
import pt.isel.ls.result.Result;
import pt.isel.ls.serializers.html.ElementNode;
import pt.isel.ls.serializers.html.Node;

public class GetStudentsForAClassResult implements Result {

    private static final String TITLE = "GetStudentsForAClass";
    private LinkedList<Student> students;
    private Class cls;
    private String message = null;

    public GetStudentsForAClassResult(LinkedList<Student> student, Class cls) {
        this.students = student;
        this.cls = cls;
    }

    public GetStudentsForAClassResult(LinkedList<Student> students,
            Class cls, String message) {
        this(students, cls);
        this.message = message;
    }

    @Override
    public void showPlain(PrintStream out) {
        if (message != null) {
            out.println(message);
        }
        for (Student s : students) {
            out.print(s);
            out.print(", ");
            out.println(cls.toString());
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
                        h2(text("Add a Student to a class ")),
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
        if (students.isEmpty()) {
            return paragraph();
        }
        return table(
                courseTableData(),
                tr(
                        th(text("Student Number")),
                        th(text("Class")),
                        th(text("Semester")),
                        th(text("Course"))
                )
        ).addAttribute("border", "1");
    }

    private ArrayList<Node> courseTableData() {
        ArrayList<Node> list = new ArrayList<>();
        for (Student s : students) {
            list.add(
                    tr(
                            td(
                                    anchor("href", "/students/" + s.getNumber(),
                                            text(s.getNumber().toString()))
                            ),
                            td(
                                    anchor("href",
                                            "/courses/" + cls.getCourseAcronym() + "/classes/"
                                                    + cls.getSemesterRepresent() + "/" + cls
                                                    .getIdentifier(),
                                            text(cls.getCourseAcronym()))
                            ),
                            td(text(cls.getIdentifier())),
                            td(text(cls.getSemesterRepresent()))

                    )
            );
        }
        return list;
    }

    private Node buildForm() {
        return form("POST", "/courses/" + cls.getCourseAcronym() + "/classes/"
                        + cls.getSemesterRepresent() + "/" + cls.getIdentifier()
                        + "/students",
                text("Student Number : "),
                requiredInput("text", "numStu"),
                submit()
        );
    }
}
