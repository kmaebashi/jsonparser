package com.kmaebashi.jsonparser;

import java.util.List;

public interface JsonArray extends JsonElement {
    List<JsonElement> getArray();
    int getLeftBracketLineNumber();
    int getRightBracketLineNumber();
    @Override
    String stringify();
}
