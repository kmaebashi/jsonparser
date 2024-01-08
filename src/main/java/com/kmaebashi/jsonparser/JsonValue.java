package com.kmaebashi.jsonparser;

public interface JsonValue extends JsonElement {


    public int getInt();

    public double getReal();

    public String getString();

    public boolean getBoolean();

    public int getLineNumber();

    public String stringify();
}
