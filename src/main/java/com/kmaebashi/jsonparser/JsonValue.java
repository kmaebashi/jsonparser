package com.kmaebashi.jsonparser;

public class JsonValue extends JsonElement {
    private JsonValueType type;
    private int intValue;
    private double realValue;
    private String stringValue;
    private boolean booleanValue;
    private int lineNumber;

    public JsonValue(int intValue, int lineNumber) {
        this.type = JsonValueType.INT;
        this.intValue = intValue;
        this.lineNumber = lineNumber;
    }

    public JsonValue(double realValue, int lineNumber) {
        this.type = JsonValueType.REAL;
        this.realValue = realValue;
        this.lineNumber = lineNumber;
    }

    public JsonValue(String stringValue, int lineNumber) {
        this.type = JsonValueType.STRING;
        this.stringValue = stringValue;
        this.lineNumber = lineNumber;
    }

    public JsonValue(boolean booleanValue, int lineNumber) {
        this.type = JsonValueType.BOOLEAN;
        this.booleanValue = booleanValue;
        this.lineNumber = lineNumber;
    }

    public JsonValue(int lineNumber) {
        this.type = JsonValueType.NULL;
        this.lineNumber = lineNumber;
    }

    public int getInt() {
        return this.intValue;
    }

    public double getReal() {
        return this.realValue;
    }

    public String getString() {
        return this.stringValue;
    }

    public boolean getBoolean() {
        return this.booleanValue;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }
}
