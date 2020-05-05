package pt.isel.ls.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.LinkedList;
import org.junit.Test;
import pt.isel.ls.exception.NoSuchCommandException;
import pt.isel.ls.exception.NoSuchMethodException;

public class RequestTest {

    @Test
    public void shouldThrowIllegalArgumentException() {
        try {
            Request request = new Request(
                    new RequestParser("XPTO /courses/LS/classes/41d/44121/teachers"));
            fail("must throw exception");
        } catch (IllegalArgumentException | NoSuchCommandException | NoSuchMethodException e) {
            //ok
        }
    }

    @Test
    public void testResquestWithoutParameters() throws NoSuchMethodException {
        LinkedList<String> expectedPath = new LinkedList<>();

        expectedPath.add("");
        expectedPath.add("courses");
        expectedPath.add("LS");
        expectedPath.add("classes");
        expectedPath.add("41d");
        expectedPath.add("44121");
        expectedPath.add("teachers");

        Request r = new Request(new RequestParser("POST /courses/LS/classes/41d/44121/teachers"));
        assertEquals(Method.POST, r.getMethod());
        assertEquals(expectedPath.toString(), r.getPath().getPathList().toString());
        assertEquals(null, r.getParameters());
    }

    @Test
    public void testRequestWithParameters() throws NoSuchMethodException {
        LinkedList<String> expectedPath = new LinkedList<>();
        HashMap<String, String> parameters = new HashMap<>();

        expectedPath.add("");
        expectedPath.add("teachers");
        parameters.put("num", "1207");
        parameters.put("name", "Pedro Félix");
        parameters.put("email", "pedrofelix@isel.pt");

        Request r = new Request(new RequestParser(
                "POST /teachers num=1207&name=Pedro+Félix&email=pedrofelix@isel.pt"));
        assertEquals(Method.POST, r.getMethod());
        assertEquals(expectedPath.toString(), r.getPath().getPathList().toString());
        for (String s : parameters.keySet()) {
            assertEquals(parameters.get(s), r.getParameters().getParameter(s).get(0));
        }
    }

    @Test
    public void testRequestWithHeaderPlain() throws NoSuchMethodException {
        HashMap<String, String> expected = new HashMap<>();
        expected.put("accept", "plain");
        Request r = new Request(new RequestParser("GET /courses accept:text/plain"));
        assertEquals(expected, r.getHeaders().getHeadersMap());
    }

    @Test
    public void testRequestWithHeaderHtml() {
        HashMap<String, String> expected = new HashMap<>();
        expected.put("accept", "html");
        Request r = new Request(new RequestParser("GET /courses accept:text/html"));
        assertEquals(expected, r.getHeaders().getHeadersMap());
    }

    @Test
    public void testRequestWithHeaders() {
        HashMap<String, String> expected = new HashMap<>();
        expected.put("file-name", "projects.html");
        expected.put("accept", "html");
        Request r = new Request(
                new RequestParser("GET /courses accept:text/html|file-name:projects.html"));
        assertEquals(expected, r.getHeaders().getHeadersMap());
    }


}