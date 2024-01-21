package com.kmaebashi.jsonparser;
import java.util.Map;

public interface JsonObject extends JsonElement {
    Map<String, JsonElement> getMap();
    int getLeftBraceLineNumber();
    int getRightBraceLineNumber();
    int getKeyLineNumber(String key);
    @Override
    String stringify();
}
