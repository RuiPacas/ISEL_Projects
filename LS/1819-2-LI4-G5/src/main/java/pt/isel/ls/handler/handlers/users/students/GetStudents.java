package pt.isel.ls.handler.handlers.users.students;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.GetHandlerBase;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.users.students.GetStudentsResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class GetStudents extends GetHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(GetStudents.class);
    private TransactionManager<Result> transactionManager;

    public GetStudents(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }


    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        return transactionManager.run(
                connection -> new GetStudentsResult(Queries.getStudents(connection))
        );
    }

    @Override
    public Template getTemplate() {
        return Template.of("/students");
    }

    @Override
    public String description() {
        return "shows all students.";
    }
}
