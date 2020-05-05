package pt.isel.ls.handler.handlers.programmes;

import static pt.isel.ls.handler.Parameters.Name.NAME;
import static pt.isel.ls.handler.Parameters.Number.NUMBER_OF_SEMESTERS;
import static pt.isel.ls.handler.Parameters.Programme.PROGRAMME_ID;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.Parameters.Name;
import pt.isel.ls.handler.Parameters.Number;
import pt.isel.ls.handler.Parameters.Programme;
import pt.isel.ls.handler.PostHandlerBase;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.programmes.GetProgrammesResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class PostProgramme extends PostHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(PostProgramme.class);
    private final TransactionManager<Result> transactionManager;

    public PostProgramme(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        try {
            String programmeId = Programme.getAndValidFrom(values.get(PROGRAMME_ID).get(0));
            String name = Name.getAndValidFrom(values.get(NAME).get(0));
            Integer numOfSemesters = Number
                    .getAndValidFrom(values.get(NUMBER_OF_SEMESTERS).get(0));
            return transactionManager.run(
                    connection -> {
                        if (Queries.getProgrammeByPid(programmeId, connection) != null) {
                            return new GetProgrammesResult(Queries.getAllProgrammes(connection),
                                    "Programme already exists");
                        }
                        if (Queries.postAProgramme(programmeId, name, numOfSemesters, connection)
                                == 1) {
                            return new Message("Post succeeded", HttpStatusCode.SeeOther);
                        }
                        return new Message("Post didn't succeeded", HttpStatusCode.BadRequest);
                    }
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
            return transactionManager.run(
                    connection -> new GetProgrammesResult(Queries.getAllProgrammes(connection),
                            e.getMessage()));
        }
    }

    @Override
    public Template getTemplate() {
        return Template.of("/programmes");
    }

    @Override
    public String description() {
        return "creates a new programme, given the following parameters: "
                + "pid - programme acronym, "
                + "name - programme name, "
                + "length - number of semesters.";
    }
}
