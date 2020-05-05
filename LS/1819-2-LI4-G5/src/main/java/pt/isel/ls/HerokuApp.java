package pt.isel.ls;

import java.util.HashMap;
import java.util.LinkedList;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.exception.ArgumentException;
import pt.isel.ls.exception.NoSuchCommandException;
import pt.isel.ls.exception.NoSuchMethodException;
import pt.isel.ls.handler.handlers.Exit;
import pt.isel.ls.handler.handlers.HomeHandler;
import pt.isel.ls.handler.handlers.Listen;
import pt.isel.ls.handler.handlers.Option;
import pt.isel.ls.handler.handlers.Time;
import pt.isel.ls.handler.handlers.classes.GetClassFromCourseWithNumInSem;
import pt.isel.ls.handler.handlers.classes.GetClassesForACourse;
import pt.isel.ls.handler.handlers.classes.GetClassesForACourseInASem;
import pt.isel.ls.handler.handlers.classes.GetClassesWithTeacher;
import pt.isel.ls.handler.handlers.classes.PostClassOnCourseWithAcr;
import pt.isel.ls.handler.handlers.courses.GetCourseByAcr;
import pt.isel.ls.handler.handlers.courses.GetCourses;
import pt.isel.ls.handler.handlers.courses.GetCoursesByPid;
import pt.isel.ls.handler.handlers.courses.PostCourse;
import pt.isel.ls.handler.handlers.courses.PostCourseWithPid;
import pt.isel.ls.handler.handlers.groups.DeleteStudentFromGroup;
import pt.isel.ls.handler.handlers.groups.GetGroupWithNumber;
import pt.isel.ls.handler.handlers.groups.GetGroupsForAClass;
import pt.isel.ls.handler.handlers.groups.PostGroupOnClass;
import pt.isel.ls.handler.handlers.groups.PostStudentToAGroup;
import pt.isel.ls.handler.handlers.programmes.GetProgrammeByPid;
import pt.isel.ls.handler.handlers.programmes.GetProgrammes;
import pt.isel.ls.handler.handlers.programmes.PostProgramme;
import pt.isel.ls.handler.handlers.users.GetUsers;
import pt.isel.ls.handler.handlers.users.students.GetStudentWithNum;
import pt.isel.ls.handler.handlers.users.students.GetStudents;
import pt.isel.ls.handler.handlers.users.students.GetStudentsForAClass;
import pt.isel.ls.handler.handlers.users.students.PostStudent;
import pt.isel.ls.handler.handlers.users.students.PostStudentToAClass;
import pt.isel.ls.handler.handlers.users.students.PutStudent;
import pt.isel.ls.handler.handlers.users.teachers.GetTeacherWithNum;
import pt.isel.ls.handler.handlers.users.teachers.GetTeachers;
import pt.isel.ls.handler.handlers.users.teachers.GetTeachersInClass;
import pt.isel.ls.handler.handlers.users.teachers.PostTeacher;
import pt.isel.ls.handler.handlers.users.teachers.PostTeacherToClass;
import pt.isel.ls.handler.handlers.users.teachers.PutTeacher;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestParser;
import pt.isel.ls.result.Result;
import pt.isel.ls.router.Router;
import pt.isel.ls.router.Router.RouterResult;
import pt.isel.ls.transactionmanager.DefaultTransactionManager;
import pt.isel.ls.transactionmanager.TransactionManager;
import pt.isel.ls.utils.MapMerge;

public class HerokuApp {

    private static final Logger log = LoggerFactory.getLogger(HerokuApp.class);
    private static final int DEFAULT_PORT = 8080;
    private static Router router = new Router();
    private static PGSimpleDataSource ds;

    public HerokuApp() {
        setUpDataSource();
        setUpRouter();
    }

    public static void main(String[] args) {
        HerokuApp app = new HerokuApp();
        app.executeCommand("LISTEN / port=" + DEFAULT_PORT);
    }

    private static void setUpDataSource() {
        String connectionString = System.getenv("JDBC_DATABASE_URL");
        if (connectionString == null) {
            log.error("JDBC_DATABASE_URL is not defined, ending");
            return;
        }
        ds = new PGSimpleDataSource();
        ds.setUrl(connectionString);
    }

    private static void setUpRouter() {
        TransactionManager<Result> ts = new DefaultTransactionManager<>(ds);
        router.add(new Exit());
        router.add(new GetCourses(ts));
        router.add(new GetCourseByAcr(ts));
        router.add(new PostClassOnCourseWithAcr(ts));
        router.add(new PostCourse(ts));
        router.add(new GetClassesForACourse(ts));
        router.add(new GetClassesForACourseInASem(ts));
        router.add(new GetClassFromCourseWithNumInSem(ts));
        router.add(new GetProgrammes(ts));
        router.add(new PostProgramme(ts));
        router.add(new GetProgrammeByPid(ts));
        router.add(new PostCourseWithPid(ts));
        router.add(new GetCoursesByPid(ts));
        router.add(new PostStudent(ts));
        router.add(new PostTeacher(ts));
        router.add(new GetUsers(ts));
        router.add(new GetTeachers(ts));
        router.add(new GetStudents(ts));
        router.add(new GetTeacherWithNum(ts));
        router.add(new GetStudentWithNum(ts));
        router.add(new PostTeacherToClass(ts));
        router.add(new GetTeachersInClass(ts));
        router.add(new GetClassesWithTeacher(ts));
        router.add(new Option(router));
        router.add(new Time());
        router.add(new PostStudentToAClass(ts));
        router.add(new GetStudentsForAClass(ts));
        router.add(new PostGroupOnClass(ts));
        router.add(new GetGroupsForAClass(ts));
        router.add(new GetGroupWithNumber(ts));
        router.add(new PostStudentToAGroup(ts));
        router.add(new DeleteStudentFromGroup(ts));
        router.add(new PutTeacher(ts));
        router.add(new PutStudent(ts));
        router.add(new Listen(router));
        router.add(new HomeHandler());
    }

    private void executeCommand(String line) {
        Request request;
        RouterResult routerResult;
        HashMap<String, LinkedList<String>> map;
        try {
            request = new Request(new RequestParser(line));
            routerResult = router.getHandler(request);
            map = getMergedMap(request, routerResult);
            routerResult.getCommandHandler().executeCommand(map);
        } catch (NoSuchMethodException | NoSuchCommandException | ArgumentException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }


    private HashMap<String, LinkedList<String>> getMergedMap(Request request,
            Router.RouterResult ch) {
        HashMap<String, LinkedList<String>> map;
        if (request.getParameters() != null) {
            map = MapMerge.mapMerge(ch.getValues(),
                    request.getParameters().getParametersMap());
        } else {
            map = MapMerge.mapMerge(ch.getValues(), new HashMap<>());
        }
        return map;
    }
}
