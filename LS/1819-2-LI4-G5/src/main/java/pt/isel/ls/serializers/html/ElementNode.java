package pt.isel.ls.serializers.html;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class ElementNode implements Node {

    private List<Attribute> attributes = new LinkedList<>();
    private List<Node> nodes = new LinkedList<>();
    private String name;

    public ElementNode(String name) {
        this.name = name;
    }

    public static ElementNode html(Node... n) {
        return new ElementNode("html").addNode(n);
    }

    public static ElementNode head(Node... n) {
        return new ElementNode("head").addNode(n);
    }

    public static ElementNode body(Node... n) {
        return new ElementNode("body").addNode(n);
    }

    public static ElementNode title(Node... n) {
        return new ElementNode("title").addNode(n);
    }

    public static ElementNode table(List<Node> a, Node... n) {
        return new ElementNode("table").addNode(n).addNode(a);
    }

    public static ElementNode tr(Node... n) {
        return new ElementNode("tr").addNode(n);
    }

    public static ElementNode th(Node... n) {
        return new ElementNode("th").addNode(n);
    }

    public static ElementNode td(Node... n) {
        return new ElementNode("td").addNode(n);
    }

    public static ElementNode ul(Node... n) {
        return new ElementNode("ul").addNode(n);
    }

    public static ElementNode ul(List<Node> n) {
        return new ElementNode("ul").addNode(n);
    }

    public static ElementNode li(Node... n) {
        return new ElementNode("li").addNode(n);
    }

    public static ElementNode meta() {
        return new ElementNode("meta").addAttribute("charset", "UTF-8");
    }

    public static ElementNode h1(Node... n) {
        return new ElementNode("h1").addNode(n);
    }

    public static ElementNode h2(Node... n) {
        return new ElementNode("h2").addNode(n);
    }

    public static ElementNode h3(Node... n) {
        return new ElementNode("h3").addNode(n);
    }

    public static ElementNode form(String method, String action, Node... n) {
        return new ElementNode("form").addAttribute("method", method)
                .addAttribute("action", action)
                .addNode(n);
    }

    public static ElementNode div(Node... n) {
        return new ElementNode("div").addNode(n);
    }

    public static ElementNode style() {
        return new ElementNode("style").addNode(text(""
                + "input[type=text], select {\n"
                + "  width: 100%;\n"
                + "  padding: 12px 20px;\n"
                + "  margin: 8px 0;\n"
                + "  display: inline-block;\n"
                + "  border: 1px solid #ccc;\n"
                + "  border-radius: 4px;\n"
                + "  box-sizing: border-box;\n"
                + "}"
                + "ul {\n"
                + "  list-style-type: circle;\n"
                + "  font-size : 20px;\n"
                + "  margin: 20px;\n"
                + "  padding: 5;\n"
                + "}"
                + "h1,h2{"
                + "   display: block; "
                + "   font-weight: bold;"
                + "   text-align : center;"
                + "}\n"
                + "\n"
                + "div {\n"
                + "  border-radius: 0px;\n"
                + "  padding: 2px;\n"
                + "}"
                + "a{"
                + " display: block;"
                + " font-weight: bold;"
                + ""

                + "}"
                + "input[type=submit] {\n"
                + "  width: 100%;\n"
                + "  background-color: #009933;\n"
                + "  color: white;\n"
                + "  padding: 14px 20px;\n"
                + "  margin: 8px 0;\n"
                + "  border: none;\n"
                + "  border-radius: 4px;\n"
                + "  cursor: pointer;\n"
                + "}\n"
                + "\n"
                + "table {\n"
                + "  font-family: arial, sans-serif;\n"
                + "  border-collapse: collapse;\n"
                + "  width: 100%;\n"
                + "}\n"
                + "th{\n"
                + "  background-color : #009933; \n"
                + "  color : #ffffff;\n"
                + "}\n"
                + "td,th {\n"
                + "  border: 1px solid #dddddd;\n"
                + "  text-align: left;\n"
                + "  padding: 8px;\n"
                + "}\n"
                + "\n"
                + "tr:nth-child(even) {\n"
                + "  background-color: #dddddd;\n"
                + "}"));
    }

    public static ElementNode select(String... s) {
        return new ElementNode("select")
                .addNode(addOptions(s));
    }

    private static Iterable<Node> addOptions(String[] s) {
        LinkedList<Node> nodes = new LinkedList<>();
        for (String string : s) {
            nodes.add(option(string));
        }
        return nodes;
    }

    private static Node option(String s) {
        return new ElementNode("option")
                .addAttribute("value", s)
                .addNode(text(s));
    }


    public static ElementNode requiredInput(String type, String name) {
        return new ElementNode("input")
                .addAttribute("type", type)
                .addAttribute("name", name)
                .addAttribute("required", "");
    }

    public static ElementNode optionalInput(String type, String name) {
        return new ElementNode("input")
                .addAttribute("type", type)
                .addAttribute("name", name);
    }

    public static ElementNode submit() {
        return new ElementNode("input")
                .addAttribute("type", "submit")
                .addAttribute("value", "Submit");
    }

    public static Node text(String s) {
        return new TextNode(s);
    }

    public static ElementNode anchor(String name, String value, Node... n) {
        return new ElementNode("a").addAttribute(name, value).addNode(n);
    }

    public static ElementNode paragraph() {
        return new ElementNode("paragraph");
    }

    public static ElementNode warningText(String s) {
        return new ElementNode("paragraph")
                .addAttribute("style", "color:red;font-size:27px")
                .addNode(text(s));
    }

    @Override
    public void print(PrintStream out, int column) {
        for (int i = 0; i < column; i++) {
            out.print('\t');
        }
        out.print("<");
        out.print(name);
        for (Attribute at : attributes) {
            out.print(" ");
            out.print(at);
        }
        out.println(">");
        for (Node n : nodes) {
            n.print(out, column + 1);
            out.println();
        }
        for (int i = 0; i < column; i++) {
            out.print('\t');
        }
        out.print("</");
        out.print(name);
        out.print(">");
    }

    public ElementNode addAttribute(String name, String value) {
        attributes.add(new Attribute(name, value));
        return this;
    }

    public ElementNode addNode(Node n) {
        nodes.add(n);
        return this;
    }


    public ElementNode addNode(Iterable<Node> src) {
        for (Node n : src) {
            nodes.add(n);
        }
        return this;
    }

    private ElementNode addNode(Node... n) {
        for (Node node : n) {
            this.addNode(node);
        }
        return this;
    }


}
