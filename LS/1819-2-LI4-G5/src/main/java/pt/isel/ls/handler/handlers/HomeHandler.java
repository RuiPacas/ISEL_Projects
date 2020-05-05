package pt.isel.ls.handler.handlers;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.GetHandlerBase;
import pt.isel.ls.result.HomeResult;
import pt.isel.ls.result.Result;
import pt.isel.ls.template.Template;

public class HomeHandler extends GetHandlerBase {

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        return new HomeResult();
    }

    @Override
    public Template getTemplate() {
        return Template.of("/");
    }

    @Override
    public String description() {
        return "Home Handler for GET requests";
    }
}
