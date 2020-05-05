package pt.isel.ls.handler.handlers.courses;

import static pt.isel.ls.handler.Parameters.AcrClass.ACR;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.GetHandlerBase;
import pt.isel.ls.handler.Parameters.AcrClass;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.model.Course;
import pt.isel.ls.model.ProgrammeCourse;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.courses.GetCourseByAcrResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class GetCourseByAcr extends GetHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(GetCourseByAcr.class);
    private TransactionManager<Result> transactionManager;

    public GetCourseByAcr(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        try {
            String acronym = AcrClass.getAndValidFrom(values.get(ACR).get(0));
            return transactionManager.run(
                    connection -> {
                        LinkedList<ProgrammeCourse> pc = new LinkedList<>();
                        Course course = Queries.getCoursesByAcr(acronym, connection, pc);
                        if (course != null) {
                            return new GetCourseByAcrResult(course, pc);
                        }
                        return new Message("No Such Course " + acronym, HttpStatusCode.NotFound);
                    });
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new Message(e.getMessage(), HttpStatusCode.NotFound);
        }
    }

    @Override
    public Template getTemplate() {
        return Template.of("/courses/{acr}");
    }

    @Override
    public String description() {
        return "shows the course with the acr acronym";
    }

}
