package pt.isel.ls.handler.handlers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.request.Method;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.template.Template;

public class Time implements CommandHandler {

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date d = new Date();
        return new Message(df.format(d), HttpStatusCode.Ok);
    }

    @Override
    public Method getMethod() {
        return Method.GET;
    }

    @Override
    public Template getTemplate() {
        return Template.of("/time");
    }

    @Override
    public String description() {
        return "presents the current time.";
    }
}
