package com.kmaebashi.jsonparser;
import java.util.SortedMap;
import java.util.TreeMap;

public class JsonObject extends JsonElement {
    private SortedMap<String, JsonElement> map;

    public JsonObject(SortedMap sortedMap) {
        this.map = sortedMap;
    }

    public SortedMap getSortedMap() {
        return this.map;
    }
}
