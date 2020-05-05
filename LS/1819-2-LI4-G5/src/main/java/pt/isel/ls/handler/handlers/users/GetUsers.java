package pt.isel.ls.handler.handlers.users;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.GetHandlerBase;
import pt.isel.ls.handler.handlers.GetUsersResult;
import pt.isel.ls.result.Result;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class GetUsers extends GetHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(GetUsers.class);
    private TransactionManager<Result> transactionManager;

    public GetUsers(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }


    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        return transactionManager.run(
                connection -> new GetUsersResult(Queries.getUsers(connection))
        );
    }

    @Override
    public Template getTemplate() {
        return Template.of("/users");
    }

    @Override
    public String description() {
        return "shows all users.";
    }
}
