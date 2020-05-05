package pt.isel.ls.handler.handlers.users.students;

import static pt.isel.ls.handler.Parameters.AcrClass.ACR;
import static pt.isel.ls.handler.Parameters.AcrClass.CLASS_ID;
import static pt.isel.ls.handler.Parameters.Sem.SEM;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.GetHandlerBase;
import pt.isel.ls.handler.Parameters.AcrClass;
import pt.isel.ls.handler.Parameters.Sem;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.model.Class;
import pt.isel.ls.model.Student;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.users.students.GetStudentsForAClassResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class GetStudentsForAClass extends GetHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(GetStudentsForAClass.class);
    private TransactionManager<Result> transactionManager;

    public GetStudentsForAClass(
            TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        String acr = AcrClass.getAndValidFrom(values.get(ACR).get(0));
        String semester = Sem.getAndValidFrom(values.get(SEM).get(0));
        String classId = AcrClass.getAndValidFrom(values.get(CLASS_ID).get(0));
        return transactionManager.run(
                connection -> {
                    Class cls = Queries
                            .getClassWithAcrSemAndId(acr, semester, classId, connection);
                    if (cls != null) {
                        LinkedList<Student> students = Queries
                                .getStudentClassWithAcrSemAndId(acr, semester, classId, connection);
                        return new GetStudentsForAClassResult(students, cls);
                    }
                    return new Message("No Such Class", HttpStatusCode.NotFound);
                }
        );
    }

    @Override
    public Template getTemplate() {
        return Template.of("/courses/{acr}/classes/{sem}/{num}/students");
    }

    @Override
    public String description() {
        return "shows all students of a class.";
    }
}
