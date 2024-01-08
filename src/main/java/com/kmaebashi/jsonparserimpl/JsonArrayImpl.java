package com.kmaebashi.jsonparserimpl;
import com.kmaebashi.jsonparser.JsonArray;
import com.kmaebashi.jsonparser.JsonElement;

import java.util.List;

public class JsonArrayImpl implements JsonArray {
    private List<JsonElement> array;

    public JsonArrayImpl(List<JsonElement> arrayList) {
        this.array = arrayList;
    }

    @Override
    public List<JsonElement> getArray() {
        return this.array;
    }

    @Override
    public String stringify() {
        StringBuilder sb = new StringBuilder();
        stringifySub(sb, 0);
        return sb.toString();
    }

    void stringifySub(StringBuilder sb, int indentLevel) {
        sb.append("[" + Constant.LINE_SEPARATOR);

        boolean isFirst = true;
        for (JsonElement elem : this.array) {
            if (!isFirst) {
                sb.append("," + Constant.LINE_SEPARATOR);
            }
            isFirst = false;
            if (elem instanceof JsonObjectImpl objElem) {
                Util.addIndent(sb, indentLevel + 1);
                objElem.stringifySub(sb, indentLevel + 1);
            } else if (elem instanceof JsonArrayImpl arrayElem) {
                Util.addIndent(sb, indentLevel + 1);
                arrayElem.stringifySub(sb, indentLevel + 1);
            } else if (elem instanceof JsonValueImpl valueElem) {
                Util.addIndent(sb, indentLevel + 1);
                valueElem.stringifySub(sb);
            }
        }
        sb.append(Constant.LINE_SEPARATOR);
        Util.addIndent(sb, indentLevel);
        sb.append("]");
    }
}