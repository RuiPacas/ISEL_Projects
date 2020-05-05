package pt.isel.ls.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class IterablesUtil {

    public static <T, R> Iterable<R> map(Iterable<T> src, Function<T, R> mapper) {
        List<R> toReturn = new LinkedList<>();
        for (T t : src) {
            toReturn.add(mapper.apply(t));
        }
        return toReturn;
    }
}
