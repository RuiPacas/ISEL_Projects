package pt.isel.ls.handler.handlers.users.teachers;

import static pt.isel.ls.handler.Parameters.Email.EMAIL;
import static pt.isel.ls.handler.Parameters.Name.NAME;
import static pt.isel.ls.handler.Parameters.Number.NUMBER;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.Parameters.Email;
import pt.isel.ls.handler.Parameters.Name;
import pt.isel.ls.handler.Parameters.Number;
import pt.isel.ls.handler.PostHandlerBase;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.users.teachers.GetTeachersResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class PostTeacher extends PostHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(PostTeacher.class);
    private TransactionManager<Result> transactionManager;

    public PostTeacher(TransactionManager<Result> transactionManager) {

        this.transactionManager = transactionManager;
    }


    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        try {
            String name = Name.getAndValidFrom(values.get(NAME).get(0));
            String email = Email.getAndValidFrom(values.get(EMAIL).get(0));
            Integer number = Number.getAndValidFrom(values.get(NUMBER).get(0));
            return transactionManager.run(connection -> {
                if (Queries.queryThatChecksIfEmailIsInUse(email, connection)) {
                    return new GetTeachersResult(Queries.getTeachers(connection),
                            "Email is already in use");
                }
                if (Queries.queryThatChecksIfThereIsACertainTeacher(number, connection)) {
                    return new GetTeachersResult(Queries.getTeachers(connection),
                            "Teacher already exists");
                }
                if (Queries.postTeacher(name, number, email, connection) == 1) {
                    return new Message("Post succeeded", HttpStatusCode.SeeOther);
                }
                return new Message("Post didn't succeeded", HttpStatusCode.BadRequest);


            });
        } catch (Exception e) {
            logger.error(e.getMessage());
            return transactionManager
                    .run(connection -> new GetTeachersResult(Queries.getTeachers(connection),
                            e.getMessage()));
        }
    }

    @Override
    public Template getTemplate() {
        return Template.of("/teachers");
    }

    @Override
    public String description() {
        return "creates a new teacher, given the following parameters: "
                + "num - teacher number, "
                + "name- teacher name, "
                + "email - teacher email.";
    }
}
