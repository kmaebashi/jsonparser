package com.kmaebashi.jsonparserimpl;
import com.kmaebashi.jsonparser.JsonElement;
import com.kmaebashi.jsonparser.JsonObject;

import java.util.Map;

public class JsonObjectImpl implements JsonObject {
    private Map<String, JsonElement> map;
    private int leftBraceLineNumber;
    private int rightBraceLineNumber;

    public JsonObjectImpl(Map<String, JsonElement> map,
                          int leftBraceTokenLineNumber,
                          int rightBraceTokenLineNumber) {
        this.map = map;
        this.leftBraceLineNumber = leftBraceTokenLineNumber;
        this.rightBraceLineNumber = rightBraceTokenLineNumber;
    }

    @Override
    public Map<String, JsonElement> getMap() {
        return this.map;
    }

    @Override
    public int getLeftBraceLineNumber() {
        return this.leftBraceLineNumber;
    }

    @Override
    public int getRightBraceLineNumber() {
        return this.rightBraceLineNumber;
    }

    @Override
    public String stringify() {
        StringBuilder sb = new StringBuilder();
        stringifySub(sb, 0);
        return sb.toString();
    }

    void stringifySub(StringBuilder sb, int indentLevel) {
        sb.append("{" + Constant.LINE_SEPARATOR);

        boolean isFirst = true;
        for (String key : this.map.keySet()) {
            if (!isFirst) {
                sb.append("," + Constant.LINE_SEPARATOR);
            }
            isFirst = false;
            Util.addIndent(sb, indentLevel + 1);
            sb.append("\"" + key + "\":");
            JsonElement elem = map.get(key);
            if (elem instanceof JsonObjectImpl objElem) {
                objElem.stringifySub(sb, indentLevel + 1);
            } else if (elem instanceof JsonArrayImpl arrayElem) {
                arrayElem.stringifySub(sb, indentLevel + 1);
            } else if (elem instanceof JsonValueImpl valueElem) {
                valueElem.stringifySub(sb);
            }
        }
        sb.append(Constant.LINE_SEPARATOR);
        Util.addIndent(sb, indentLevel);
        sb.append("}");
    }

}
