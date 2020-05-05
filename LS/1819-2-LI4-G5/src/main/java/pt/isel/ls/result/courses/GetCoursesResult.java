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
import java.util.List;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.model.Course;
import pt.isel.ls.result.Result;
import pt.isel.ls.serializers.html.ElementNode;
import pt.isel.ls.serializers.html.Node;


public class GetCoursesResult implements Result {

    private static final String TITLE = "GetCourses";
    private List<Course> courseList;
    private String message = null;

    public GetCoursesResult(List<Course> courseList) {
        this.courseList = courseList;
    }

    public GetCoursesResult(LinkedList<Course> courses, String message) {
        this(courses);
        this.message = message;
    }

    @Override
    public void showPlain(PrintStream out) {
        if (message != null) {
            out.println(message);
        }
        if (!courseList.isEmpty()) {
            for (int i = 0; i < courseList.size(); ++i) {
                out.println(courseList.get(i));
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
                        h2(text("Add Course")),
                        warningText(message != null ? message : ""),
                        div(
                                buildForm()
                        ),
                        anchor("href", "/", text("HOME"))
                )
        ).print(out, 0);

    }

    private Node buildForm() {
        return form("POST", "/courses",
                text("Course Name : "),
                requiredInput("text", "name"),
                text("Acronym : "),
                requiredInput("text", "acr"),
                text("Coordinator Teacher Number : "),
                requiredInput("text", "teacher"),
                submit()
        );
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatusCode.Ok;
    }

    private ElementNode buildTable() {
        if (courseList.isEmpty()) {
            return paragraph();
        }
        return table(
                courseTableData(),
                tr(
                        th(text("Name")),
                        th(text("Acronym")),
                        th(text("Coordinator Number"))
                )
        ).addAttribute("border", "1");
    }

    private ArrayList<Node> courseTableData() {
        ArrayList<Node> list = new ArrayList<>();
        for (Course c : courseList) {
            list.add(
                    tr(
                            td(text(c.getName())),
                            td(
                                    anchor("href", "/courses/" + c.getAcronym(),
                                            text(c.getAcronym()))
                            ),
                            td(text(c.getCoordinatorNumber().toString()))
                    )
            );
        }
        return list;
    }
}
