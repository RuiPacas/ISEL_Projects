package pt.isel.ls.template;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import pt.isel.ls.request.Path;


public class Template {

    private static final String SEPARATOR = "/";
    String template;

    private Template(String temp) {
        template = temp;
    }

    public static Template of(String s) {
        return new Template(s);
    }

    public MatchResult match(Path p) {

        List<String> splittedTemplate = Arrays.asList(template.split(SEPARATOR));
        HashMap<String, String> toReturn = new HashMap<>();
        boolean validate = p.getPathList().size() == splittedTemplate.size();
        for (int i = 0; validate && i < splittedTemplate.size(); i++) {
            String tempString = splittedTemplate.get(i);
            String pathString = p.getPathList().get(i);
            if (i > 0 && tempString.charAt(0) == '{') {
                toReturn.put(tempString.substring(1, tempString.length() - 1), pathString);
            } else if (!tempString.equals(pathString)) {
                validate = false;
            }

        }
        return new MatchResult(toReturn, validate);
    }

    @Override
    public String toString() {
        return template;
    }

    public static class MatchResult {

        private HashMap<String, String> matches;
        private boolean validate;

        public MatchResult(HashMap<String, String> matches, boolean validate) {
            this.matches = matches;
            this.validate = validate;
        }


        public HashMap<String, String> getMatches() {
            return matches;
        }

        public boolean isValid() {
            return validate;
        }
    }

}
