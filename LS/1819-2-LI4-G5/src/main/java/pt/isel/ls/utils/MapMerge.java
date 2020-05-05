package pt.isel.ls.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import pt.isel.ls.request.Request;
import pt.isel.ls.router.Router;

public class MapMerge {

    public static HashMap<String, LinkedList<String>> mapMerge(HashMap<String, String> firstMap,
            HashMap<String, LinkedList<String>> secondMap) {

        HashMap<String, LinkedList<String>> maptoReturn = new HashMap<>();
        for (Map.Entry<String, String> entry : firstMap.entrySet()) {
            LinkedList<String> list = new LinkedList<>();
            list.add(entry.getValue());
            maptoReturn.put(entry.getKey(), list);
        }
        for (Map.Entry<String, LinkedList<String>> entry : secondMap.entrySet()) {
            LinkedList<String> list = maptoReturn
                    .computeIfAbsent(entry.getKey(), (k) -> new LinkedList<>());
            list.addAll(entry.getValue());
        }
        return maptoReturn;
    }

    public static HashMap<String, LinkedList<String>> getMergedMap(Request request,
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
