package pt.isel.ls.handler.handlers.classes;

import static pt.isel.ls.handler.Parameters.AcrClass.ACR;
import static pt.isel.ls.handler.Parameters.AcrClass.CLASS_ID;
import static pt.isel.ls.handler.Parameters.Sem.SEM;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.Parameters.AcrClass;
import pt.isel.ls.handler.Parameters.Sem;
import pt.isel.ls.handler.PostHandlerBase;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.classes.GetClassesForACourseResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;


public class PostClassOnCourseWithAcr extends PostHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(PostClassOnCourseWithAcr.class);
    private final TransactionManager<Result> transactionManager;

    public PostClassOnCourseWithAcr(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        String[] acr = {null};
        try {
            acr[0] = AcrClass.getAndValidFrom(values.get(ACR).get(0));
            String classId = AcrClass.getAndValidFrom(values.get(CLASS_ID).get(0));
            String semester = Sem.getAndValidFrom(values.get(SEM).get(0));
            return transactionManager.run(
                    connection -> {
                        if (Queries.getClassWithAcrSemAndId(acr[0], semester, classId, connection)
                                != null) {
                            return new GetClassesForACourseResult(
                                    Queries.getClassesForACourse(acr[0], connection),
                                    acr[0], "Class already exists");
                        }

                        if (Queries.queryThatCheckIfThereIsACourseWithAcr(acr[0], connection)
                                && Queries.postClassOnCourseWithAcr(classId, semester, acr[0],
                                connection)
                                == 1) {
                            return new Message("Post succeeded", HttpStatusCode.SeeOther);
                        }
                        return new Message("Post didn't succeeded", HttpStatusCode.BadRequest);
                    });
        } catch (Exception e) {
            logger.error(e.getMessage());
            if (acr[0] == null) {
                return new Message(e.getMessage(), HttpStatusCode.BadRequest);
            }
            return transactionManager.run(connection ->
                    new GetClassesForACourseResult(Queries.getClassesForACourse(acr[0], connection),
                            acr[0], e.getMessage()));
        }
    }


    @Override
    public Template getTemplate() {
        return Template.of("/courses/{acr}/classes");
    }

    @Override
    public String description() {
        return " creates a new class on the course with acr acronym, "
                + "given the following parameters: "
                + "sem - semester identifier, "
                + "num - class number.";
    }


}
