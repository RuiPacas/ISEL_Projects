package pt.isel.ls.router;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.exception.NoSuchCommandException;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.request.Method;
import pt.isel.ls.request.Request;
import pt.isel.ls.template.Template;
import pt.isel.ls.utils.Pair;

/**
 * Class Responsible for choosing a handler based on a request. This is possible because a router
 * has a map based on a Method, a with that method and a specific template, we can return the right
 * CommandHandler. *
 */

public class Router {

    private HashMap<Method, LinkedList<Pair<Template, CommandHandler>>>
            commandMap = new HashMap<>();
    private LinkedList<CommandHandler> commandHandlers = new LinkedList<>();


    public RouterResult getHandler(Request r) throws NoSuchCommandException {
        LinkedList<Pair<Template, CommandHandler>> list = commandMap.get(r.getMethod());
        Template.MatchResult match = null;
        CommandHandler command = null;
        if (list == null) {
            throw new NoSuchCommandException("Invalid Command");
        }
        for (Pair<Template, CommandHandler> pair : list) {
            match = pair.first.match(r.getPath());
            if (match.isValid()) {
                command = pair.second;
                break;
            }
        }
        if (command == null) {
            throw new NoSuchCommandException("Invalid Command.");
        }
        return new RouterResult(match.getMatches(), command);
    }

    public boolean add(CommandHandler c) {
        commandHandlers.add(c);
        Method m = c.getMethod();
        LinkedList<Pair<Template, CommandHandler>> list = commandMap
                .computeIfAbsent(m, k -> new LinkedList<>());
        return list.add(new Pair<>(c.getTemplate(), c));

    }

    public LinkedList<CommandHandler> getHandlers() {
        return commandHandlers;
    }

    public static class RouterResult {

        private HashMap<String, String> values;
        private CommandHandler commandHandler;

        public RouterResult(HashMap<String, String> values, CommandHandler commandHandler) {
            this.values = values;
            this.commandHandler = commandHandler;
        }

        public HashMap<String, String> getValues() {
            return values;
        }

        public CommandHandler getCommandHandler() {
            return commandHandler;
        }
    }
}
