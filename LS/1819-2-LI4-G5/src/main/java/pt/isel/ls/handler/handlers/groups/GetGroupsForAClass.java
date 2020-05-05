package pt.isel.ls.handler.handlers.groups;

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
import pt.isel.ls.model.Group;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.groups.GetGroupsForAClassResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class GetGroupsForAClass extends GetHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(GetGroupsForAClass.class);
    private TransactionManager<Result> transactionManager;

    public GetGroupsForAClass(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        String acr = AcrClass.getAndValidFrom(values.get(ACR).get(0));
        String semester = Sem.getAndValidFrom(values.get(SEM).get(0));
        String classId = AcrClass.getAndValidFrom(values.get(CLASS_ID).get(0));
        return transactionManager.run(connection -> {
            Class cls = Queries
                    .getClassWithAcrSemAndId(acr, semester, classId, connection);
            if (cls != null) {
                LinkedList<Group> groups = Queries
                        .getGroupsForAClass(acr, semester, classId, connection);
                return new GetGroupsForAClassResult(groups, cls);
            }
            return new Message("No such class", HttpStatusCode.NotFound);
        });
    }


    @Override
    public Template getTemplate() {
        return Template.of("/courses/{acr}/classes/{sem}/{num}/groups");
    }

    @Override
    public String description() {
        return "shows all groups of a class.";
    }
}
