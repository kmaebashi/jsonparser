package com.kmaebashi.jsonparser;

import java.util.ArrayList;

public class JsonArray extends JsonElement {
    private ArrayList<JsonElement> array = new ArrayList<>();

    public ArrayList getArray() {
        return this.array;
    }
}
