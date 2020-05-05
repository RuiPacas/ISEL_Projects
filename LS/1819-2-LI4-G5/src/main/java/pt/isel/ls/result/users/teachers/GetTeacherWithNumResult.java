package pt.isel.ls.result.users.teachers;

import static pt.isel.ls.serializers.html.ElementNode.anchor;
import static pt.isel.ls.serializers.html.ElementNode.body;
import static pt.isel.ls.serializers.html.ElementNode.div;
import static pt.isel.ls.serializers.html.ElementNode.h1;
import static pt.isel.ls.serializers.html.ElementNode.h2;
import static pt.isel.ls.serializers.html.ElementNode.head;
import static pt.isel.ls.serializers.html.ElementNode.html;
import static pt.isel.ls.serializers.html.ElementNode.li;
import static pt.isel.ls.serializers.html.ElementNode.meta;
import static pt.isel.ls.serializers.html.ElementNode.style;
import static pt.isel.ls.serializers.html.ElementNode.text;
import static pt.isel.ls.serializers.html.ElementNode.title;
import static pt.isel.ls.serializers.html.ElementNode.ul;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.model.Teacher;
import pt.isel.ls.result.Result;
import pt.isel.ls.serializers.html.ElementNode;
import pt.isel.ls.serializers.html.Node;

public class GetTeacherWithNumResult implements Result {

    private static final String TITLE = "GetTeacherWithNum";
    private Teacher teacher;
    private LinkedList<String> coursesAcronyms;


    public GetTeacherWithNumResult(Teacher teacher, LinkedList<String> coursesAcronyms) {
        this.teacher = teacher;
        this.coursesAcronyms = coursesAcronyms;
    }

    @Override
    public void showPlain(PrintStream out) {
        out.println(teacher);
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
                                buildTeacher(),
                                buildBullets()
                        ),
                        anchor("href", "/teachers", text("TEACHERS")),
                        anchor("href", "/", text("HOME"))
                )
        ).print(out, 0);

    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatusCode.Ok;
    }

    private ElementNode buildTeacher() {
        return ul(
                li(text("Teacher Name = " + teacher.getName())),
                li(text("Teacher Email = " + teacher.getEmail())),
                li(anchor("href", "/teachers/" + teacher.getNumber() + "/classes",
                        text("Teacher Number = " + teacher.getNumber())))
        );
    }

    private ElementNode buildBullets() {
        return ul(acronymsBullets());
    }

    private List<Node> acronymsBullets() {
        ArrayList<Node> list = new ArrayList<>();
        if (coursesAcronyms.size() != 0) {
            list.add(h2(text("Coordinates :")));
            for (String s : coursesAcronyms) {
                list.add(
                        li(
                                anchor("href", "/courses/" + s, text(s))
                        )
                );
            }
        }
        return list;
    }
}
