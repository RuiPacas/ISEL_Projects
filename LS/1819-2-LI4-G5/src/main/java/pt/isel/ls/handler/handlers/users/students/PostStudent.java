package pt.isel.ls.handler.handlers.users.students;

import static pt.isel.ls.handler.Parameters.Email.EMAIL;
import static pt.isel.ls.handler.Parameters.Name.NAME;
import static pt.isel.ls.handler.Parameters.Number.NUMBER;
import static pt.isel.ls.handler.Parameters.Programme.PROGRAMME_ID;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.Parameters;
import pt.isel.ls.handler.Parameters.Email;
import pt.isel.ls.handler.Parameters.Name;
import pt.isel.ls.handler.Parameters.Number;
import pt.isel.ls.handler.PostHandlerBase;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.users.students.GetStudentsResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class PostStudent extends PostHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(PostStudent.class);
    private TransactionManager<Result> transactionManager;

    public PostStudent(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }


    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        try {
            String name = Name.getAndValidFrom(values.get(NAME).get(0));
            String email = Email.getAndValidFrom(values.get(EMAIL).get(0));
            String pid = Parameters.Programme.getAndValidFrom(values.get(PROGRAMME_ID).get(0));
            Integer number = Number.getAndValidFrom(values.get(NUMBER).get(0));
            return transactionManager.run(connection -> {
                if (Queries.getProgrammeByPid(pid, connection) == null) {
                    return new GetStudentsResult(Queries.getStudents(connection),
                            "Programme " + pid + " doesn't exist");
                }
                if (Queries.queryThatChecksIfEmailIsInUse(email, connection)) {
                    return new GetStudentsResult(Queries.getStudents(connection),
                            "Email is already in use");
                }
                if (Queries.queryThatChecksIfThereIsACertainStudent(number, connection)) {
                    return new GetStudentsResult(Queries.getStudents(connection),
                            "Student already exists");
                }
                if (Queries.postStudent(name, email, number, pid, connection) == 1) {
                    return new Message("Post succeeded", HttpStatusCode.SeeOther);
                }
                return new Message("Post didn't succeeded", HttpStatusCode.BadRequest);
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
            return transactionManager
                    .run(connection -> new GetStudentsResult(Queries.getStudents(connection),
                            e.getMessage()));
        }
    }

    @Override
    public Template getTemplate() {
        return Template.of("/students");
    }

    @Override
    public String description() {
        return "creates a new student, given the following parameters: "
                + "num - student number, "
                + "name- student name, "
                + "email - student email, "
                + "pid - programme acronym.";
    }
}
