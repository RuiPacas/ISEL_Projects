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
import pt.isel.ls.exception.ArgumentException;
import pt.isel.ls.handler.Parameters.Email;
import pt.isel.ls.handler.Parameters.Name;
import pt.isel.ls.handler.Parameters.Number;
import pt.isel.ls.handler.Parameters.Programme;
import pt.isel.ls.handler.PutHandlerBase;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class PutStudent extends PutHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(PutStudent.class);
    private TransactionManager<Result> transactionManager;

    public PutStudent(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        try {
            String newName = Name.getAndValidFrom(values.get(NAME).get(0));
            String newEmail = Email.getAndValidFrom(values.get(EMAIL).get(0));
            Integer newNumber = Number.getAndValidFrom(values.get(NUMBER).get(1));
            Integer actualNumber = Number.getAndValidFrom(values.get(NUMBER).get(0));
            String programme = Programme.getAndValidFrom(values.get(PROGRAMME_ID).get(0));
            return transactionManager.run(connection -> {
                if (Queries.putStudent(newName, newEmail, newNumber, actualNumber, programme,
                        connection)) {
                    return new Message("Put Succeeded", HttpStatusCode.Ok);
                }
                return new Message("Put Failed", HttpStatusCode.BadRequest);
            });
        } catch (ArgumentException e) {
            logger.error(e.getMessage());
            return new Message(e.getMessage(), HttpStatusCode.BadRequest);
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return new Message("Missing arguments, use OPTION / for more details",
                    HttpStatusCode.BadRequest);
        }
    }

    @Override
    public Template getTemplate() {
        return Template.of("/students/{num}");
    }

    @Override
    public String description() {
        return "Updates an existent student, given all required parameters : "
                + "email - new Student email, "
                + "name - new Student name, "
                + "num - new Student Number, "
                + "pid - new Student pid";
    }
}
