package pt.isel.ls.handler.handlers.programmes;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.GetHandlerBase;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.programmes.GetProgrammesResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class GetProgrammes extends GetHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(GetProgrammes.class);
    private final TransactionManager<Result> transactionManager;

    public GetProgrammes(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }


    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        return transactionManager.run(
                connection ->
                        new GetProgrammesResult(Queries.getAllProgrammes(connection)));
    }

    @Override
    public Template getTemplate() {
        return Template.of("/programmes");
    }

    @Override
    public String description() {
        return "list all the programmes.";
    }
}
