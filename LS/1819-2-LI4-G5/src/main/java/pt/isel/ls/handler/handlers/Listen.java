package pt.isel.ls.handler.handlers;

import static pt.isel.ls.handler.Parameters.Number.PORT;

import java.util.HashMap;
import java.util.LinkedList;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.exception.ArgumentException;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.Parameters.Number;
import pt.isel.ls.http.HandlersServlet;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.request.Method;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.router.Router;
import pt.isel.ls.template.Template;

public class Listen implements CommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(Listen.class);
    private Router router;

    public Listen(Router router) {

        this.router = router;
    }

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        try {
            String portDef = System.getenv("PORT");
            Integer port = Number.getAndValidFrom(portDef == null
                    ? values.get(PORT).get(0) : portDef);
            Server server = new Server(port);
            ServletHandler handler = new ServletHandler();
            server.setHandler(handler);
            handler.addServletWithMapping(new ServletHolder(new HandlersServlet(router)), "/*");
            server.start();
            return new Message("Server Up", HttpStatusCode.Ok);
        } catch (ArgumentException e) {
            throw e;
        } catch (Exception e) {
            return new Message("Internal Server Error", HttpStatusCode.InternalServerError);
        }
    }

    @Override
    public Method getMethod() {
        return Method.LISTEN;
    }

    @Override
    public Template getTemplate() {
        return Template.of("/");
    }

    @Override
    public String description() {
        return "Starts the HTTP Server. Recieves a parameter : "
                + "port - contains the TCP port where the server should listen for requests";
    }
}
