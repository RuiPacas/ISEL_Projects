package pt.isel.ls.handler.handlers.users.teachers;

import static pt.isel.ls.handler.Parameters.Number.NUMBER;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.GetHandlerBase;
import pt.isel.ls.handler.Parameters.Number;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.model.Teacher;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.users.teachers.GetTeacherWithNumResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class GetTeacherWithNum extends GetHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(GetTeacherWithNum.class);
    private TransactionManager<Result> transactionManager;

    public GetTeacherWithNum(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        Integer number = Number.getAndValidFrom(values.get(NUMBER).get(0));
        return transactionManager.run(
                connection -> {
                    Teacher teacher = Queries.getTeacherWithNumber(number, connection);
                    if (teacher != null) {
                        LinkedList<String> coursesAcronyms = Queries
                                .getCoursesAcronymWithCoordNumber(number, connection);
                        return new GetTeacherWithNumResult(teacher, coursesAcronyms);
                    }
                    return new Message("No Such Teacher" + number, HttpStatusCode.NotFound);
                }
        );
    }

    @Override
    public Template getTemplate() {
        return Template.of("/teachers/{num}");
    }

    @Override
    public String description() {
        return " shows the teacher with number num.";
    }
}
