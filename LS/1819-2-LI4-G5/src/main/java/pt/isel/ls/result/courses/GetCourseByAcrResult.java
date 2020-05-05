package pt.isel.ls.result.courses;

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
import pt.isel.ls.model.Course;
import pt.isel.ls.model.ProgrammeCourse;
import pt.isel.ls.result.Result;
import pt.isel.ls.serializers.html.ElementNode;
import pt.isel.ls.serializers.html.Node;

public class GetCourseByAcrResult implements Result {

    private static final String TITLE = "GetCourseByAcr";
    private Course course;
    private LinkedList<ProgrammeCourse> pcList;

    public GetCourseByAcrResult(Course course, LinkedList<ProgrammeCourse> pc) {
        this.course = course;
        this.pcList = pc;
    }

    @Override
    public void showPlain(PrintStream out) {
        out.println(course.toString());
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
                                buildCourse(),
                                buildTable()
                        ),
                        anchor("href", "/courses", text("COURSES")),
                        anchor("href", "/", text("HOME"))
                )
        ).print(out, 0);

    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatusCode.Ok;
    }

    private ElementNode buildCourse() {
        return ul(
                li(text("Course Name = " + course.getName())),
                li(text("Acronym = " + course.getAcronym())),
                li(anchor("href", "/teachers/" + course.getCoordinatorNumber(),
                        text("Coordinator Teacher Number = " + course.getCoordinatorNumber()))),
                li(anchor("href", "/courses/" + course.getAcronym() + "/classes",
                        text("Classes")))
        );
    }

    private ElementNode buildTable() {
        return pcList.size() > 0 ? table(
                courseTableData(),
                tr(
                        th(text("Programme Id")),
                        th(text("Mandatory")),
                        th(text("Curricular Semesters"))
                )
        ).addAttribute("border", "1")
                : paragraph();
    }

    private ArrayList<Node> courseTableData() {
        ArrayList<Node> list = new ArrayList<>();
        for (ProgrammeCourse pc : pcList) {
            list.add(
                    tr(
                            td(
                                    anchor("href", "/programmes/" + pc.getProgrammeId(),
                                            text(pc.getProgrammeId()))
                            ),
                            td(text(pc.getMandatory().toString())),
                            td(text(pc.getCurricularSemester()))
                    )
            );
        }
        return list;
    }


}
