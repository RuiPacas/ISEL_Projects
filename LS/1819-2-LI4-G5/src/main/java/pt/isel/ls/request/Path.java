package pt.isel.ls.request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import pt.isel.ls.exception.ArgumentException;

/**
 * Class responsible for creating a Path
 */
public class Path {

    private static final String PATH_SEPARATOR = "/";
    private final String path;
    private List<String> pathList;

    public Path(String s) {
        try {
            this.path = URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new ArgumentException();
        }
        pathList = Arrays.asList(s.split(PATH_SEPARATOR));
    }

    public List<String> getPathList() {
        return pathList;
    }

    @Override
    public String toString() {
        return path;
    }
}
