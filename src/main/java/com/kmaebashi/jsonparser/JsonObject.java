package com.kmaebashi.jsonparser;
import java.util.Map;

public interface JsonObject extends JsonElement {
    public Map<String, JsonElement> getMap();
    public int getLeftBraceLineNumber();
    public int getRightBraceLineNumber();
    public int getKeyLineNumber(String key);
    public String stringify();
}
