package pt.isel.ls.handler.handlers.users.students;

import static pt.isel.ls.handler.Parameters.Number.NUMBER;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.GetHandlerBase;
import pt.isel.ls.handler.Parameters.Number;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.model.ScGroup;
import pt.isel.ls.model.Student;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.users.students.GetStudentWithNumResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class GetStudentWithNum extends GetHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(GetStudentWithNum.class);
    private TransactionManager<Result> transactionManager;

    public GetStudentWithNum(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        Integer number = Number.getAndValidFrom(values.get(NUMBER).get(0));
        return transactionManager.run(
                connection -> {
                    Student student = Queries.getStudentWithNumber(number, connection);
                    if (student != null) {
                        LinkedList<ScGroup> scGroups = Queries.getScGroups(number, connection);
                        return new GetStudentWithNumResult(student, scGroups);
                    }
                    return new Message("No Such Student " + number, HttpStatusCode.NotFound);
                }
        );
    }

    @Override
    public Template getTemplate() {
        return Template.of("/students/{num}");
    }

    @Override
    public String description() {
        return "shows the student with the number num.";
    }
}
