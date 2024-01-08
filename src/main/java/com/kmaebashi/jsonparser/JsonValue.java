package com.kmaebashi.jsonparser;

public class JsonValue extends JsonElement {
    private JsonValueType type;
    private int intValue;
    private double realValue;
    private String stringValue;
    private boolean booleanValue;

    public JsonValue(int intValue) {
        this.type = JsonValueType.INT;
        this.intValue = intValue;
    }

    public JsonValue(double realValue) {
        this.type = JsonValueType.REAL;
        this.realValue = realValue;
    }

    public JsonValue(String stringValue) {
        this.type = JsonValueType.STRING;
        this.stringValue = stringValue;
    }

    public JsonValue(boolean booleanValue) {
        this.type = JsonValueType.BOOLEAN;
        this.booleanValue = booleanValue;
    }

    public JsonValue() {
        this.type = JsonValueType.NULL;
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
}
