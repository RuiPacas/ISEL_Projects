package pt.isel.ls.router;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;
import pt.isel.ls.handler.handlers.Exit;
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
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class RouterTest {

    private static Router router = new Router();
    private static PGSimpleDataSource ds;

    @BeforeClass
    public static void initRouter() {
        String connectionString = System.getenv("POSTGRES_TEST");
        ds = new PGSimpleDataSource();
        ds.setUrl(connectionString);
        router.add(new Exit());
        router.add(new GetCourses(new TestTransactionManager<>(ds)));
        router.add(new GetCourseByAcr(new TestTransactionManager<>(ds)));
        router.add(new PostClassOnCourseWithAcr(new TestTransactionManager<>(ds)));
        router.add(new PostCourse(new TestTransactionManager<>(ds)));
        router.add(new GetClassesForACourse(new TestTransactionManager<>(ds)));
        router.add(new GetClassesForACourseInASem(new TestTransactionManager<>(ds)));
        router.add(new GetClassFromCourseWithNumInSem(new TestTransactionManager<>(ds)));
        router.add(new GetProgrammes(new TestTransactionManager<>(ds)));
        router.add(new PostProgramme(new TestTransactionManager<>(ds)));
        router.add(new GetProgrammeByPid(new TestTransactionManager<>(ds)));
        router.add(new PostCourseWithPid(new TestTransactionManager<>(ds)));
        router.add(new GetCoursesByPid(new TestTransactionManager<>(ds)));
        router.add(new PostStudent(new TestTransactionManager<>(ds)));
        router.add(new PostTeacher(new TestTransactionManager<>(ds)));
        router.add(new GetUsers(new TestTransactionManager<>(ds)));
        router.add(new GetTeachers(new TestTransactionManager<>(ds)));
        router.add(new GetStudents(new TestTransactionManager<>(ds)));
        router.add(new GetTeacherWithNum(new TestTransactionManager<>(ds)));
        router.add(new GetStudentWithNum(new TestTransactionManager<>(ds)));
        router.add(new PostTeacherToClass(new TestTransactionManager<>(ds)));
        router.add(new GetTeachersInClass(new TestTransactionManager<>(ds)));
        router.add(new GetClassesWithTeacher(new TestTransactionManager<>(ds)));
        router.add(new Option(router));
        router.add(new Time());
        router.add(new PostStudentToAClass(new TestTransactionManager<>(ds)));
        router.add(new GetStudentsForAClass(new TestTransactionManager<>(ds)));
        router.add(new PostGroupOnClass(new TestTransactionManager<>(ds)));
        router.add(new GetGroupsForAClass(new TestTransactionManager<>(ds)));
        router.add(new GetGroupWithNumber(new TestTransactionManager<>(ds)));
        router.add(new PostStudentToAGroup(new TestTransactionManager<>(ds)));
        router.add(new DeleteStudentFromGroup(new TestTransactionManager<>(ds)));
        router.add(new PutTeacher(new TestTransactionManager<>(ds)));
        router.add(new PutStudent(new TestTransactionManager<>(ds)));
    }

    @Test
    public void getHandlerTest() throws NoSuchMethodException {
        Request r = new Request(new RequestParser("POST /courses/LS/classes/41d/44121/teachers"));
        initRouter();
        Router.RouterResult v = router.getHandler(r);
        Assert.assertTrue(v.getCommandHandler() instanceof PostTeacherToClass);
        r = new Request(new RequestParser("GET /courses"));
        v = router.getHandler(r);
        Assert.assertTrue(v.getCommandHandler() instanceof GetCourses);

    }

}