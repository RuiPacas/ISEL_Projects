package pt.isel.ls.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestParser;

public class TemplateTest {

    @Test
    public void testMatchingAPath() throws NoSuchMethodException {
        //Arrange
        Template t = Template.of("/courses/{acr}/classes/{sem}/{num}/teachers");
        Request r = new Request(new RequestParser("POST /courses/LS/classes/1819V/44121/teachers"));
        //Act
        Template.MatchResult m = t.match(r.getPath());
        HashMap<String, String> map = m.getMatches();
        //Arrange
        Assert.assertTrue(m.isValid());
        assertEquals(map.get("acr"), "LS");
        assertEquals(map.get("sem"), "1819V");
        assertEquals(map.get("num"), "44121");
    }

    @Test
    public void shouldFailBecauseRequestDoesntFollowTemplate() throws NoSuchMethodException {
        //Arrange
        Template t = Template.of("/courses/{acr}/classes/{sem}/{num}/teachers");
        Request r = new Request(new RequestParser("POST /courses/LS/classes/1819V44121/teachers"));
        //Act
        Template.MatchResult m = t.match(r.getPath());
        HashMap<String, String> map = m.getMatches();
        //Arrange
        Assert.assertFalse(m.isValid());
        assertNotSame(map.get("acr"), "LS");
        assertNotSame(map.get("sem"), "1819V");
        assertNotSame(map.get("num"), "44121");
    }
}