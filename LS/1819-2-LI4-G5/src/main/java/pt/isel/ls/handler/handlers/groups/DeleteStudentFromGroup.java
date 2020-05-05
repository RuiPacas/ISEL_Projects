package pt.isel.ls.handler.handlers.groups;

import static pt.isel.ls.handler.Parameters.AcrClass.ACR;
import static pt.isel.ls.handler.Parameters.AcrClass.CLASS_ID;
import static pt.isel.ls.handler.Parameters.Number.GROUP_NUMBER;
import static pt.isel.ls.handler.Parameters.Number.NUM_STU;
import static pt.isel.ls.handler.Parameters.Sem.SEM;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.DeleteHandlerBase;
import pt.isel.ls.handler.Parameters.AcrClass;
import pt.isel.ls.handler.Parameters.Number;
import pt.isel.ls.handler.Parameters.Sem;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class DeleteStudentFromGroup extends DeleteHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(DeleteStudentFromGroup.class);
    private TransactionManager<Result> transactionManager;

    public DeleteStudentFromGroup(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        String acr = AcrClass.getAndValidFrom(values.get(ACR).get(0));
        String semester = Sem.getAndValidFrom(values.get(SEM).get(0));
        String classId = AcrClass.getAndValidFrom(values.get(CLASS_ID).get(0));
        Integer groupNumber = Number.getAndValidFrom(values.get(GROUP_NUMBER).get(0));
        Integer studentNumber = Number.getAndValidFrom(values.get(NUM_STU).get(0));
        return transactionManager.run(
                connection -> {
                    if (Queries.deleteStudentFromGroup(studentNumber, classId, semester, acr,
                            groupNumber, connection) == 1) {
                        return new Message("Delete Successful", HttpStatusCode.Ok);
                    }
                    return new Message("Delete unsuccessful", HttpStatusCode.BadRequest);
                }
        );
    }

    @Override
    public Template getTemplate() {
        return Template.of("/courses/{acr}/classes/{sem}/{num}/groups/{gno}/students/{numStu}");
    }

    @Override
    public String description() {
        return "Removes a student from a group. ";
    }
}
