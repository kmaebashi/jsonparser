package com.kmaebashi.jsonparser;
import java.util.SortedMap;
import java.util.TreeMap;

public interface JsonObject extends JsonElement {
    public SortedMap<String, JsonElement> getSortedMap();

    public String stringify();
}
