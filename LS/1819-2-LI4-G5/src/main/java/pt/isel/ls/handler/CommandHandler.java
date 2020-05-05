package pt.isel.ls.handler;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.request.Method;
import pt.isel.ls.result.Result;
import pt.isel.ls.template.Template;


public interface CommandHandler {

    Result executeCommand(HashMap<String, LinkedList<String>> values);

    Method getMethod();

    Template getTemplate();

    String description();
}


