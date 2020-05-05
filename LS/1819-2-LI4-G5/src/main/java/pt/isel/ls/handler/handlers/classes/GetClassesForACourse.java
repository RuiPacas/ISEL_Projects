package pt.isel.ls.handler.handlers.classes;

import static pt.isel.ls.handler.Parameters.AcrClass.ACR;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.GetHandlerBase;
import pt.isel.ls.handler.Parameters.AcrClass;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.model.Class;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.classes.GetClassesForACourseResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class GetClassesForACourse extends GetHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(GetClassesForACourse.class);
    private final TransactionManager<Result> transactionManager;

    public GetClassesForACourse(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        try {
            String acr = AcrClass.getAndValidFrom(values.get(ACR).get(0));
            return transactionManager.run(
                    connection -> {
                        if (Queries.queryThatCheckIfThereIsACourseWithAcr(acr, connection)) {
                            LinkedList<Class> classes = Queries
                                    .getClassesForACourse(acr, connection);
                            return new GetClassesForACourseResult(classes, acr);
                        }
                        return new Message("No Such Course " + acr, HttpStatusCode.NotFound);
                    }
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new Message(e.getMessage(), HttpStatusCode.NotFound);
        }
    }

    @Override
    public Template getTemplate() {
        return Template.of("/courses/{acr}/classes");
    }

    @Override
    public String description() {
        return "shows all classes for a course.";
    }


}
