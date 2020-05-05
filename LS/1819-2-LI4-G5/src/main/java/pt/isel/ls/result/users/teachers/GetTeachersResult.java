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
import pt.isel.ls.model.Teacher;
import pt.isel.ls.result.Result;
import pt.isel.ls.serializers.html.ElementNode;
import pt.isel.ls.serializers.html.Node;

public class GetTeachersResult implements Result {

    private static final String TITLE = "GetTeachers";
    private String message = null;
    private LinkedList<Teacher> teachers;

    public GetTeachersResult(LinkedList<Teacher> teachers) {
        this.teachers = teachers;
    }

    public GetTeachersResult(LinkedList<Teacher> teachers, String message) {
        this(teachers);
        this.message = message;
    }

    @Override
    public void showPlain(PrintStream out) {
        if (message != null) {
            out.println(message);
        }
        if (!teachers.isEmpty()) {
            for (int i = 0; i < teachers.size(); ++i) {
                out.println(teachers.get(i));
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
                        h2(text("Add Teacher")),
                        warningText(message != null ? message : ""),
                        div(
                                buildForm()
                        ),
                        anchor("href", "/", text("HOME"))
                )
        ).print(out, 0);

    }

    private Node buildForm() {
        return form("POST", "/teachers",
                text("Teacher Name : "),
                requiredInput("text", "name"),
                text("Teacher Number : "),
                requiredInput("text", "num"),
                text("Teacher Email "),
                requiredInput("text", "email"),
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
                teachersTableData(),
                tr(
                        th(text("Name")),
                        th(text("Email")),
                        th(text("Number"))
                )
        ).addAttribute("border", "1");
    }

    private ArrayList<Node> teachersTableData() {
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
