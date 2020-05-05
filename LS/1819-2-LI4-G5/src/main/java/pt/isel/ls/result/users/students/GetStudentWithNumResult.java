package pt.isel.ls.result.users.students;

import static pt.isel.ls.serializers.html.ElementNode.anchor;
import static pt.isel.ls.serializers.html.ElementNode.body;
import static pt.isel.ls.serializers.html.ElementNode.div;
import static pt.isel.ls.serializers.html.ElementNode.h1;
import static pt.isel.ls.serializers.html.ElementNode.head;
import static pt.isel.ls.serializers.html.ElementNode.html;
import static pt.isel.ls.serializers.html.ElementNode.li;
import static pt.isel.ls.serializers.html.ElementNode.meta;
import static pt.isel.ls.serializers.html.ElementNode.paragraph;
import static pt.isel.ls.serializers.html.ElementNode.style;
import static pt.isel.ls.serializers.html.ElementNode.table;
import static pt.isel.ls.serializers.html.ElementNode.td;
import static pt.isel.ls.serializers.html.ElementNode.text;
import static pt.isel.ls.serializers.html.ElementNode.th;
import static pt.isel.ls.serializers.html.ElementNode.title;
import static pt.isel.ls.serializers.html.ElementNode.tr;
import static pt.isel.ls.serializers.html.ElementNode.ul;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.model.ScGroup;
import pt.isel.ls.model.Student;
import pt.isel.ls.result.Result;
import pt.isel.ls.serializers.html.ElementNode;
import pt.isel.ls.serializers.html.Node;

public class GetStudentWithNumResult implements Result {


    private static final String TITLE = "GetStudentWithNum";
    private Student student;
    private LinkedList<ScGroup> scGroups;

    public GetStudentWithNumResult(Student student, LinkedList<ScGroup> scGroups) {
        this.student = student;
        this.scGroups = scGroups;
    }

    @Override
    public void showPlain(PrintStream out) {
        out.println(student);
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
                                buildStudent(),
                                buildTable()
                        ),
                        anchor("href", "/students", text("STUDENTS")),
                        anchor("href", "/", text("HOME"))
                )
        ).print(out, 0);

    }

    private ElementNode buildStudent() {
        return ul(
                li(text("Student Name = " + student.getName())),
                li(text("Student Email = " + student.getEmail())),
                li(text("Student Number = " + student.getNumber())),
                li(anchor("href", "/programmes/" + student.getProgrammeId()),
                        text("Student Programme = " + student.getProgrammeId()))
        );
    }

    private ElementNode buildTable() {
        if (scGroups.size() != 0) {
            return table(
                    acronymsTableData(),
                    tr(
                            th(text("Class Identifier")),
                            th(text("Semester Represent")),
                            th(text("Course")),
                            th(text("Group"))
                    )
            ).addAttribute("border", "1");
        }
        return paragraph();
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatusCode.Ok;
    }

    private ArrayList<Node> acronymsTableData() {
        ArrayList<Node> list = new ArrayList<>();
        for (ScGroup sc : scGroups) {
            list.add(
                    tr(
                            td(
                                    anchor("href", "/courses/" + sc.getCourseAcronym() + "/classes"
                                                    + "/" + sc.getSemesterRepresent() + "/" + sc
                                                    .getClassIdentifier(),
                                            text(sc.getClassIdentifier()))
                            ),
                            td(
                                    text(sc.getSemesterRepresent())
                            ),
                            td(
                                    text(sc.getCourseAcronym())
                            ),
                            td(
                                    anchor("href", "/courses/" + sc.getCourseAcronym() + "/classes"
                                                    + "/" + sc.getSemesterRepresent() + "/" + sc
                                                    .getClassIdentifier()
                                                    + "/groups/" + sc.getGroupNumber(),
                                            text(sc.getGroupNumber().toString()))
                            )
                    )
            );
        }
        return list;
    }


}
