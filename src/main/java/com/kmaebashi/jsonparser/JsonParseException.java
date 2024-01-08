package com.kmaebashi.jsonparser;

public class JsonParseException extends Exception {
    int lineNumber;
    public JsonParseException(String message, int lineNumber) {
        super(message + " at " + lineNumber);
        this.lineNumber = lineNumber;
    }
}
