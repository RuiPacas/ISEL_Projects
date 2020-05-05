package pt.isel.ls.handler.handlers.programmes;

import static pt.isel.ls.handler.Parameters.Programme.PROGRAMME_ID;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.GetHandlerBase;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.model.Programme;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.programmes.GetProgrammeByPidResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class GetProgrammeByPid extends GetHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(GetProgrammeByPid.class);
    private TransactionManager<Result> transactionManager;

    public GetProgrammeByPid(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        try {
            String pid = pt.isel.ls.handler.Parameters.Programme
                    .getAndValidFrom(values.get(PROGRAMME_ID).get(0));
            return transactionManager.run(connection -> {
                Programme programme = Queries.getProgrammeByPid(pid, connection);
                if (programme != null) {
                    return new GetProgrammeByPidResult(programme);
                }
                return new Message("No Such Programme " + pid, HttpStatusCode.NotFound);
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new Message(e.getMessage(), HttpStatusCode.NotFound);
        }
    }

    @Override
    public Template getTemplate() {
        return Template.of("/programmes/{pid}");
    }

    @Override
    public String description() {
        return "shows the details of programme with pid acronym.";
    }


}
