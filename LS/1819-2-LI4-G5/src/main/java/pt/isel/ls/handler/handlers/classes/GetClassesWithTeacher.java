package pt.isel.ls.handler.handlers.classes;

import static pt.isel.ls.handler.Parameters.Number.NUMBER;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.GetHandlerBase;
import pt.isel.ls.handler.Parameters.Number;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.model.ClassTeacher;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.classes.GetClassesWithTeacherResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class GetClassesWithTeacher extends GetHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(GetClassesWithTeacher.class);
    private final TransactionManager<Result> transactionManager;

    public GetClassesWithTeacher(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        Integer numDoc = Number.getAndValidFrom(values.get(NUMBER).get(0));
        return transactionManager.run(
                connection -> {
                    if (Queries.queryThatChecksIfThereIsACertainTeacher(numDoc, connection)) {
                        LinkedList<ClassTeacher> classTeachers = Queries
                                .getClassTeachersWithTeacherNumber(numDoc, connection);
                        return new GetClassesWithTeacherResult(classTeachers);
                    }
                    return new Message("No such teacher " + numDoc, HttpStatusCode.BadRequest);
                }
        );
    }

    @Override
    public Template getTemplate() {
        return Template.of("/teachers/{num}/classes");
    }

    @Override
    public String description() {
        return "shows all classes for the teacher with num number.";
    }
}
