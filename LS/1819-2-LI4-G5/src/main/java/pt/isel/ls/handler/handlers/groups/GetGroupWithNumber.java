package pt.isel.ls.handler.handlers.groups;

import static pt.isel.ls.handler.Parameters.AcrClass.ACR;
import static pt.isel.ls.handler.Parameters.AcrClass.CLASS_ID;
import static pt.isel.ls.handler.Parameters.Number.GROUP_NUMBER;
import static pt.isel.ls.handler.Parameters.Sem.SEM;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.GetHandlerBase;
import pt.isel.ls.handler.Parameters.AcrClass;
import pt.isel.ls.handler.Parameters.Number;
import pt.isel.ls.handler.Parameters.Sem;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.model.Class;
import pt.isel.ls.model.Group;
import pt.isel.ls.model.Student;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.groups.GetGroupWithNumberResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class GetGroupWithNumber extends GetHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(GetGroupWithNumber.class);
    private TransactionManager<Result> transactionManager;

    public GetGroupWithNumber(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        String acr = AcrClass.getAndValidFrom(values.get(ACR).get(0));
        String semester = Sem.getAndValidFrom(values.get(SEM).get(0));
        String classId = AcrClass.getAndValidFrom(values.get(CLASS_ID).get(0));
        Integer groupNumber = Number.getAndValidFrom(values.get(GROUP_NUMBER).get(0));
        return transactionManager.run(connection -> {
            if (Queries.checkIfGroupNumberExistsInAClass(acr, semester, classId, groupNumber,
                    connection)) {
                LinkedList<Student> students = Queries
                        .getStudentClassWithAcrSemAndIdAndGroupNr(acr, semester, classId,
                                groupNumber, connection);
                Class c = Queries.getClassWithAcrSemAndId(acr, semester, classId, connection);
                Group g = new Group(groupNumber);
                return new GetGroupWithNumberResult(students, c, g);
            }
            return new Message("No Such Group", HttpStatusCode.BadRequest);
        });
    }

    @Override
    public Template getTemplate() {
        return Template.of("/courses/{acr}/classes/{sem}/{num}/groups/{gno}");
    }

    @Override
    public String description() {
        return "shows the details for the group with number gno.";
    }
}
