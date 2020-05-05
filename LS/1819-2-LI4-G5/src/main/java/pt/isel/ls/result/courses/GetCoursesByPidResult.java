package pt.isel.ls.result.courses;

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
import static pt.isel.ls.serializers.html.ElementNode.select;
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
import pt.isel.ls.model.ProgrammeCourse;
import pt.isel.ls.result.Result;
import pt.isel.ls.serializers.html.ElementNode;
import pt.isel.ls.serializers.html.Node;

public class GetCoursesByPidResult implements Result {

    private static final String TITLE = "GetCoursesByPid";
    private LinkedList<ProgrammeCourse> programmeCourses;
    private String pid;
    private String message = null;


    public GetCoursesByPidResult(LinkedList<ProgrammeCourse> programmeCourses, String pid) {

        this.programmeCourses = programmeCourses;
        this.pid = pid;
    }

    public GetCoursesByPidResult(LinkedList<ProgrammeCourse> programmeCoursesByPid, String s,
            String message) {
        this(programmeCoursesByPid, s);
        this.message = message;
    }

    @Override
    public void showPlain(PrintStream out) {
        if (message != null) {
            out.println(message);
        }
        for (ProgrammeCourse pc : programmeCourses) {
            out.println(pc);
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
                        h2(text("Create a Course for " + pid)),
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
        if (programmeCourses.isEmpty()) {
            return paragraph();
        }
        return table(
                courseTableData(),
                tr(
                        th(text("Programme Id")),
                        th(text("Course Acronym")),
                        th(text("Mandatory")),
                        th(text("Curricular Semesters"))
                )
        ).addAttribute("border", "1");
    }

    private ArrayList<Node> courseTableData() {
        ArrayList<Node> list = new ArrayList<>();
        for (ProgrammeCourse pc : programmeCourses) {
            list.add(
                    tr(
                            td(
                                    anchor("href", "/programmes/" + pc.getProgrammeId(),
                                            text(pc.getProgrammeId()))
                            ),
                            td(
                                    anchor("href", "/courses/" + pc.getCourseAcronym(),
                                            text(pc.getCourseAcronym()))
                            ),
                            td(text(pc.getMandatory().toString())),
                            td(text(pc.getCurricularSemester()))
                    )
            );
        }
        return list;
    }

    private Node buildForm() {
        return form("POST", "/programmes/" + pid + "/courses",
                text("Acronym : "),
                requiredInput("text", "acr"),
                text("Mandatory : "),
                select("true", "false").addAttribute("name", "mandatory"),
                text("Semesters : "),
                requiredInput("text", "semesters"),
                submit()
        );
    }
}
