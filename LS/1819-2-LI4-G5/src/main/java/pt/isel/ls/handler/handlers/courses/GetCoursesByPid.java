package pt.isel.ls.handler.handlers.courses;

import static pt.isel.ls.handler.Parameters.Programme.PROGRAMME_ID;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.GetHandlerBase;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.model.Programme;
import pt.isel.ls.model.ProgrammeCourse;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.courses.GetCoursesByPidResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class GetCoursesByPid extends GetHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(GetCoursesByPid.class);
    private TransactionManager<Result> transactionManager;

    public GetCoursesByPid(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }


    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        try {
            String pid = pt.isel.ls.handler.Parameters.Programme
                    .getAndValidFrom(values.get(PROGRAMME_ID).get(0));
            return transactionManager.run(connection -> {
                Programme programme = Queries.getProgrammeByPid(pid, connection);
                if (programme == null) {
                    return new Message("No such Programme " + pid, HttpStatusCode.BadRequest);
                }
                LinkedList<ProgrammeCourse> programmeCourses = Queries
                        .getProgrammeCoursesByPid(pid, connection);
                return new GetCoursesByPidResult(programmeCourses, pid);
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new Message(e.getMessage(), HttpStatusCode.BadRequest);
        }
    }

    @Override
    public Template getTemplate() {
        return Template.of("/programmes/{pid}/courses");
    }

    @Override
    public String description() {
        return "shows the course structure of programme pid.";
    }
}
