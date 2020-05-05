package pt.isel.ls.handler.handlers.courses;

import static pt.isel.ls.handler.Parameters.AcrClass.ACR;
import static pt.isel.ls.handler.Parameters.Name.NAME;
import static pt.isel.ls.handler.Parameters.Number.TEACHER;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.Parameters.AcrClass;
import pt.isel.ls.handler.Parameters.Name;
import pt.isel.ls.handler.Parameters.Number;
import pt.isel.ls.handler.PostHandlerBase;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.courses.GetCoursesResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;


public class PostCourse extends PostHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(PostCourse.class);
    private final TransactionManager<Result> transactionManager;

    public PostCourse(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }


    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        try {
            String name = Name.getAndValidFrom(values.get(NAME).get(0));
            String acr = AcrClass.getAndValidFrom(values.get(ACR).get(0));
            Integer teacherNumber = Number.getAndValidFrom(values.get(TEACHER).get(0));
            return transactionManager.run(
                    connection -> {
                        if (!Queries.queryThatChecksIfThereIsACertainTeacher(teacherNumber,
                                connection)) {
                            return new GetCoursesResult(Queries.getCourses(values, connection),
                                    "Teacher " + teacherNumber + " does not exist");
                        }
                        if (Queries.queryThatCheckIfThereIsACourseWithAcr(acr, connection)) {
                            return new GetCoursesResult(Queries.getCourses(values, connection),
                                    "Course " + acr + " already exists");
                        }
                        if (Queries.postCourse(name, acr, teacherNumber, connection) == 1) {
                            return new Message("Post succeeded", HttpStatusCode.SeeOther);
                        }
                        return new GetCoursesResult(Queries.getCourses(values, connection),
                                "Check your parameters");
                    }
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
            return transactionManager
                    .run(connection -> new GetCoursesResult(Queries.getCourses(values, connection),
                            e.getMessage()));
        }
    }


    @Override
    public Template getTemplate() {
        return Template.of("/courses");
    }

    @Override
    public String description() {
        return "Creates a new course, given the following parameters: "
                + "name - course name, "
                + "acr - course acronym, "
                + "teacher - number of the coordinator teacher.";
    }
}
