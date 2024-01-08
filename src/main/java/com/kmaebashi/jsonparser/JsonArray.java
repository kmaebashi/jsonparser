package com.kmaebashi.jsonparser;

import java.util.List;

public class JsonArray extends JsonElement {
    private List<JsonElement> array;

    public JsonArray(List<JsonElement> arrayList) {
        this.array = arrayList;
    }

    public List getArray() {
        return this.array;
    }
}
