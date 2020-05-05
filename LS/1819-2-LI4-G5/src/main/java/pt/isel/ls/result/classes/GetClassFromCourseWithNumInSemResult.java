package pt.isel.ls.result.classes;

import static pt.isel.ls.serializers.html.ElementNode.anchor;
import static pt.isel.ls.serializers.html.ElementNode.body;
import static pt.isel.ls.serializers.html.ElementNode.div;
import static pt.isel.ls.serializers.html.ElementNode.h1;
import static pt.isel.ls.serializers.html.ElementNode.head;
import static pt.isel.ls.serializers.html.ElementNode.html;
import static pt.isel.ls.serializers.html.ElementNode.li;
import static pt.isel.ls.serializers.html.ElementNode.meta;
import static pt.isel.ls.serializers.html.ElementNode.style;
import static pt.isel.ls.serializers.html.ElementNode.text;
import static pt.isel.ls.serializers.html.ElementNode.title;
import static pt.isel.ls.serializers.html.ElementNode.ul;

import java.io.PrintStream;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.model.Class;
import pt.isel.ls.result.Result;
import pt.isel.ls.serializers.html.ElementNode;

public class GetClassFromCourseWithNumInSemResult implements Result {

    private static final String TITLE = "GetClassFromCourseWithNumInSem";
    private Class cls;

    public GetClassFromCourseWithNumInSemResult(Class cls) {
        this.cls = cls;

    }

    @Override
    public void showPlain(PrintStream out) {
        out.println(cls);
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
                                buildClass()
                        ),
                        anchor("href", "/", text("HOME"))
                )
        ).print(out, 0);
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatusCode.Ok;
    }

    private ElementNode buildClass() {
        return ul(
                li(text("Class Identifier = " + cls.getIdentifier())),
                li(text("Semester Represent = " + cls.getSemesterRepresent())),
                li(text("Course Acronym = " + cls.getCourseAcronym())),
                li(anchor("href", "/courses/" + cls.getCourseAcronym()
                        + "/classes/" + cls.getSemesterRepresent()
                        + "/" + cls.getIdentifier() + "/groups", text("Groups"))),
                li(anchor("href", "/courses/" + cls.getCourseAcronym()
                        + "/classes/" + cls.getSemesterRepresent(), text("Classes"))),
                li(anchor("href", "/courses/" + cls.getCourseAcronym()
                        + "/classes/" + cls.getSemesterRepresent() + "/" + cls.getIdentifier()
                        + "/teachers", text("Teachers"))),
                li(anchor("href", "/courses/" + cls.getCourseAcronym()
                        + "/classes/" + cls.getSemesterRepresent() + "/" + cls.getIdentifier()
                        + "/students", text("Students")))
        );
    }
}
