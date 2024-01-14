package com.kmaebashi.jsonparser;

import java.util.List;

public interface JsonArray extends JsonElement {
    public List<JsonElement> getArray();
    public int getLeftBracketLineNumber();
    public int getRightBracketLineNumber();
    public String stringify();
}
