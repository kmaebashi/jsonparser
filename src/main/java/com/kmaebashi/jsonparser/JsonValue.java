package com.kmaebashi.jsonparser;

public interface JsonValue extends JsonElement {
    JsonValueType getType();

    int getInt();

    double getReal();

    String getString();

    boolean getBoolean();

    int getLineNumber();

    @Override
    String stringify();
}
