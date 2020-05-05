package pt.isel.ls.result.programmes;

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
import pt.isel.ls.model.Programme;
import pt.isel.ls.result.Result;
import pt.isel.ls.serializers.html.ElementNode;
import pt.isel.ls.serializers.html.Node;

public class GetProgrammesResult implements Result {

    private static final String TITLE = "GetProgrammes";
    private LinkedList<Programme> programmes;
    private String message = null;

    public GetProgrammesResult(LinkedList<Programme> programmes) {
        this.programmes = programmes;
    }

    public GetProgrammesResult(LinkedList<Programme> programmes, String message) {
        this(programmes);
        this.message = message;
    }

    @Override
    public void showPlain(PrintStream out) {
        if (message != null) {
            out.println(message);
        }
        if (!programmes.isEmpty()) {
            for (int i = 0; i < programmes.size(); ++i) {
                out.println(programmes.get(i));
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
                        h2(text("Create a programme")),
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
        if (programmes.isEmpty()) {
            return paragraph();
        }
        return table(
                courseTableData(),
                tr(
                        th(text("Programme Id")),
                        th(text("Name")),
                        th(text("Number Of Semesters"))
                )
        ).addAttribute("border", "1");
    }

    private ArrayList<Node> courseTableData() {
        ArrayList<Node> list = new ArrayList<>();
        for (Programme p : programmes) {
            list.add(
                    tr(
                            td(
                                    anchor("href", "/programmes/" + p.getProgrammeId(),
                                            text(p.getProgrammeId()))
                            ),
                            td(text(p.getName())),
                            td(text(p.getNumberOfSemesters().toString()))
                    )
            );
        }
        return list;
    }

    private Node buildForm() {
        return form("POST", "/programmes",
                text("Programme Id : "),
                requiredInput("text", "pid"),
                text("Name : "),
                requiredInput("text", "name"),
                text("Number of semesters : "),
                requiredInput("text", "length"),
                submit()
        );
    }
}
