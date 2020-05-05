package pt.isel.ls.handler.handlers;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.request.Method;
import pt.isel.ls.result.ExitResult;
import pt.isel.ls.result.Result;
import pt.isel.ls.template.Template;

public class Exit implements CommandHandler {


    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        return new ExitResult();
    }

    @Override
    public Method getMethod() {
        return Method.EXIT;
    }

    @Override
    public Template getTemplate() {
        return Template.of("/");
    }

    @Override
    public String description() {
        return "terminates the application.";
    }
}
