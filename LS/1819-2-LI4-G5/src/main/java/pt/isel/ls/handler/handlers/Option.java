package pt.isel.ls.handler.handlers;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.request.Method;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.router.Router;
import pt.isel.ls.template.Template;

public class Option implements CommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(Option.class);
    private Router router;

    public Option(Router r) {
        router = r;
    }

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        LinkedList<CommandHandler> ch = router.getHandlers();
        String result = "";
        for (CommandHandler c : ch) {
            result += c.getMethod().toString() + " " + c.getTemplate().toString() + " - " + c
                    .description() + "\n";
        }
        return new Message(result, HttpStatusCode.Ok);
    }

    @Override
    public Method getMethod() {
        return Method.OPTION;
    }

    @Override
    public Template getTemplate() {
        return Template.of("/");
    }

    @Override
    public String description() {
        return "presents a list of available commands and their description.";
    }
}
