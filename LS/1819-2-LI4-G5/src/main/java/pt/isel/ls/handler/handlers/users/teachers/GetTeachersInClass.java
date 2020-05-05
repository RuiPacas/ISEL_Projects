package pt.isel.ls.handler.handlers.users.teachers;

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
import pt.isel.ls.model.Teacher;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.users.teachers.GetTeachersInClassResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class GetTeachersInClass extends GetHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(GetTeachersInClass.class);
    private TransactionManager<Result> transactionManager;

    public GetTeachersInClass(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }


    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        String acr = AcrClass.getAndValidFrom(values.get(ACR).get(0));
        String classId = AcrClass.getAndValidFrom(values.get(CLASS_ID).get(0));
        String sem = Sem.getAndValidFrom(values.get(SEM).get(0));
        return transactionManager.run(
                connection -> {
                    Class cls = Queries.getClassWithAcrSemAndId(acr, sem, classId, connection);
                    if (cls != null) {
                        LinkedList<Teacher> teachers = Queries
                                .getTeachersWithAcrIdAndSem(acr, classId, sem, connection);
                        Class c = new Class(classId, sem, acr);
                        return new GetTeachersInClassResult(teachers, c);
                    }
                    return new Message("No Such Class", HttpStatusCode.NotFound);
                }
        );
    }

    @Override
    public Template getTemplate() {
        return Template.of("/courses/{acr}/classes/{sem}/{num}/teachers");
    }

    @Override
    public String description() {
        return "shows all teachers for a class.";
    }
}
