package pt.isel.ls.handler.handlers;

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
import pt.isel.ls.model.User;
import pt.isel.ls.result.Result;
import pt.isel.ls.serializers.html.ElementNode;
import pt.isel.ls.serializers.html.Node;

public class GetUsersResult implements Result {

    private static final String TITLE = "GetUsersResult";
    private final LinkedList<User> users;

    public GetUsersResult(LinkedList<User> users) {
        this.users = users;
    }

    @Override
    public void showPlain(PrintStream out) {
        for (User user : users) {
            out.println(user.toString());
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
                        )
                )
        ).print(out, 0);

    }

    private ElementNode buildTable() {
        if (users.isEmpty()) {
            return paragraph();
        }
        return table(
                teachersTableData(),
                tr(
                        th(text("Name")),
                        th(text("Email"))
                )
        ).addAttribute("border", "1");
    }

    private ArrayList<Node> teachersTableData() {
        ArrayList<Node> list = new ArrayList<>();
        for (User user : users) {
            list.add(
                    tr(
                            td(text(user.getName())),
                            td(text(user.getEmail()))
                    )
            );
        }
        return list;
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatusCode.Ok;
    }
}
