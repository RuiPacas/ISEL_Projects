package pt.isel.ls.handler.handlers.users.teachers;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.GetHandlerBase;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.users.teachers.GetTeachersResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class GetTeachers extends GetHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(GetTeachers.class);
    private TransactionManager<Result> transactionManager;

    public GetTeachers(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }


    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        return transactionManager.run(
                connection -> new GetTeachersResult(Queries.getTeachers(connection))
        );
    }

    @Override
    public Template getTemplate() {
        return Template.of("/teachers");
    }

    @Override
    public String description() {
        return "shows all teachers.";
    }
}
