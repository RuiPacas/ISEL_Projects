package pt.isel.ls.result.groups;

import static pt.isel.ls.serializers.html.ElementNode.anchor;
import static pt.isel.ls.serializers.html.ElementNode.body;
import static pt.isel.ls.serializers.html.ElementNode.div;
import static pt.isel.ls.serializers.html.ElementNode.form;
import static pt.isel.ls.serializers.html.ElementNode.h1;
import static pt.isel.ls.serializers.html.ElementNode.h2;
import static pt.isel.ls.serializers.html.ElementNode.head;
import static pt.isel.ls.serializers.html.ElementNode.html;
import static pt.isel.ls.serializers.html.ElementNode.meta;
import static pt.isel.ls.serializers.html.ElementNode.optionalInput;
import static pt.isel.ls.serializers.html.ElementNode.paragraph;
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
import pt.isel.ls.model.Group;
import pt.isel.ls.result.Result;
import pt.isel.ls.serializers.html.ElementNode;
import pt.isel.ls.serializers.html.Node;

public class GetGroupsForAClassResult implements Result {

    private static final String TITLE = "GetGroupsForAClass";
    private LinkedList<Group> groups;
    private Class cls;
    private String message = null;

    public GetGroupsForAClassResult(LinkedList<Group> groups, Class cls) {
        this.groups = groups;
        this.cls = cls;
    }

    public GetGroupsForAClassResult(LinkedList<Group> groups, Class cls,
            String message) {
        this(groups, cls);
        this.message = message;
    }

    @Override
    public void showPlain(PrintStream out) {
        if (message != null) {
            out.println(message);
        }
        for (Group g : groups) {
            out.println(g.toString());
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
                                buildGroups()
                        ),
                        h2(text("Add a Group")),
                        warningText(message != null ? message : ""),
                        div(
                                buildForm()
                        ),
                        anchor("href", "/courses/" + cls.getCourseAcronym() + "/classes/"
                                        + cls.getSemesterRepresent() + "/" + cls.getIdentifier(),
                                text("CLASS")),
                        anchor("href", "/", text("HOME"))
                )
        ).print(out, 0);
    }


    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatusCode.Ok;
    }

    private ElementNode buildGroups() {
        if (groups.isEmpty()) {
            return paragraph();
        }
        return table(
                groupTableData(),
                tr(
                        th(text("Group Number"))
                )
        ).addAttribute("border", "1");
    }

    private ArrayList<Node> groupTableData() {
        ArrayList<Node> list = new ArrayList<>();
        for (Group g : groups) {
            list.add(
                    tr(
                            td(
                                    anchor("href", "/courses/" + cls.getCourseAcronym()
                                                    + "/classes/" + cls.getSemesterRepresent()
                                                    + "/" + cls
                                                    .getIdentifier()
                                                    + "/groups/" + g.getNumber(),
                                            text(g.getNumber().toString()))
                            )
                    )
            );
        }
        return list;
    }

    private Node buildForm() {
        return form("POST",
                "/courses/" + cls.getCourseAcronym()
                        + "/classes/" + cls.getSemesterRepresent() + "/" + cls.getIdentifier()
                        + "/groups",
                text("Student Number : "),
                optionalInput("text", "numStu"),
                text("Student Number : "),
                optionalInput("text", "numStu"),
                text("Student Number : "),
                optionalInput("text", "numStu"),
                submit()
        );
    }
}
