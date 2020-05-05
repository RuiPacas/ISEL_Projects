package pt.isel.ls.result.classes;

import static pt.isel.ls.serializers.html.ElementNode.anchor;
import static pt.isel.ls.serializers.html.ElementNode.body;
import static pt.isel.ls.serializers.html.ElementNode.div;
import static pt.isel.ls.serializers.html.ElementNode.h1;
import static pt.isel.ls.serializers.html.ElementNode.head;
import static pt.isel.ls.serializers.html.ElementNode.html;
import static pt.isel.ls.serializers.html.ElementNode.meta;
import static pt.isel.ls.serializers.html.ElementNode.paragraph;
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
import pt.isel.ls.model.ClassTeacher;
import pt.isel.ls.result.Result;
import pt.isel.ls.serializers.html.ElementNode;
import pt.isel.ls.serializers.html.Node;

public class GetClassesWithTeacherResult implements Result {

    private static final String TITLE = "GetClassesWithTeacher";
    private LinkedList<ClassTeacher> classTeachers;

    public GetClassesWithTeacherResult(LinkedList<ClassTeacher> classTeachers) {
        this.classTeachers = classTeachers;
    }

    @Override
    public void showPlain(PrintStream out) {
        for (ClassTeacher ct : classTeachers) {
            out.println(ct.toString());
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
                        !classTeachers.isEmpty()
                                ? anchor("href",
                                "/teachers/" + classTeachers.get(0).getTeacherNumber(),
                                text("TEACHER"))
                                : paragraph(),
                        anchor("href", "/", text("HOME"))
                )
        ).print(out, 0);

    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatusCode.Ok;
    }

    private ElementNode buildTable() {
        if (classTeachers.isEmpty()) {
            return paragraph();
        }
        return table(
                classTeacherTableData(),
                tr(
                        th(text("Course Acronym")),
                        th(text("Class Identifier")),
                        th(text("Semester Represent"))
                )
        ).addAttribute("border", "1");
    }

    private ArrayList<Node> classTeacherTableData() {
        ArrayList<Node> list = new ArrayList<>();
        for (ClassTeacher ct : classTeachers) {
            list.add(
                    tr(
                            td(
                                    anchor("href", "/courses/" + ct.getCourseAcronym() + "/classes/"
                                            + ct.getSemesterRepresent() + "/" + ct
                                            .getIdentifierClass(), text(ct.getCourseAcronym()))
                            ),
                            td(text(ct.getIdentifierClass())),
                            td(text(ct.getSemesterRepresent()))
                    )
            );
        }
        return list;
    }
}
