package pt.isel.ls.handler.handlers.classes;

import static pt.isel.ls.handler.Parameters.AcrClass.ACR;
import static pt.isel.ls.handler.Parameters.Sem.SEM;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.GetHandlerBase;
import pt.isel.ls.handler.Parameters.AcrClass;
import pt.isel.ls.handler.Parameters.Sem;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.model.Class;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.classes.GetClassesForCourseInASemResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class GetClassesForACourseInASem extends GetHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(GetClassesForACourseInASem.class);
    private final TransactionManager<Result> transactionManager;

    public GetClassesForACourseInASem(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }


    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        String acr = AcrClass.getAndValidFrom(values.get(ACR).get(0));
        String semester = Sem.getAndValidFrom(values.get(SEM).get(0));
        return transactionManager.run(
                connection -> {
                    if (Queries.queryThatCheckIfThereIsACourseWithAcr(acr, connection)) {
                        LinkedList<Class> classes = Queries
                                .getClassesWithAcrAndSem(acr, semester, connection);
                        if (classes.isEmpty()) {
                            return new Message("Invalid Semester " + semester + "for " + acr,
                                    HttpStatusCode.BadRequest);
                        }
                        return new GetClassesForCourseInASemResult(classes);

                    }
                    return new Message("No Such Course " + acr, HttpStatusCode.BadRequest);
                });
    }

    @Override
    public Template getTemplate() {
        return Template.of("/courses/{acr}/classes/{sem}");
    }

    @Override
    public String description() {
        return "shows all classes of the acr course on the sem semester.";
    }
}
