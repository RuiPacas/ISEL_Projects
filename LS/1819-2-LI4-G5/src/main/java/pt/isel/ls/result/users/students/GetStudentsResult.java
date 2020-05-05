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
import pt.isel.ls.model.Student;
import pt.isel.ls.result.Result;
import pt.isel.ls.serializers.html.ElementNode;
import pt.isel.ls.serializers.html.Node;

public class GetStudentsResult implements Result {


    private static final String TITLE = "GetStudents";
    private LinkedList<Student> studentsList;
    private String message = null;

    public GetStudentsResult(LinkedList<Student> results) {
        this.studentsList = results;
    }

    public GetStudentsResult(LinkedList<Student> students, String message) {
        this(students);
        this.message = message;
    }

    @Override
    public void showPlain(PrintStream out) {
        if (message != null) {
            out.println(message);
        }
        if (!studentsList.isEmpty()) {
            for (int i = 0; i < studentsList.size(); ++i) {
                out.println(studentsList.get(i));
            }
        } else {
            out.println();
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
                        h2(text("Add Student")),
                        warningText(message != null ? message : ""),
                        div(
                                buildForm()
                        ),
                        anchor("href", "/", text("HOME"))
                )
        ).print(out, 0);

    }

    private Node buildForm() {
        return form("POST", "/students",
                text("Student Name : "),
                requiredInput("text", "name"),
                text("Student Number : "),
                requiredInput("text", "num"),
                text("Student Email "),
                requiredInput("text", "email"),
                text("Student Programme ID"),
                requiredInput("text", "pid"),
                submit()
        );
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatusCode.Ok;
    }

    private ElementNode buildTable() {
        if (studentsList.isEmpty()) {
            return paragraph();
        }
        return table(
                courseTableData(),
                tr(
                        th(text("Name")),
                        th(text("Email")),
                        th(text("Number")),
                        th(text("Programme"))
                )
        ).addAttribute("border", "1");
    }

    private ArrayList<Node> courseTableData() {
        ArrayList<Node> list = new ArrayList<>();
        for (Student s : studentsList) {
            list.add(
                    tr(
                            td(text(s.getName())),
                            td(text(s.getEmail())),
                            td(
                                    anchor("href", "/students/" + s.getNumber(),
                                            text(s.getNumber().toString()))
                            ),
                            td(text(s.getProgrammeId()))
                    )
            );
        }
        return list;
    }
}
