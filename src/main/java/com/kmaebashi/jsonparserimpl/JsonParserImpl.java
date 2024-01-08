package com.kmaebashi.jsonparserimpl;

import com.kmaebashi.jsonparser.JsonElement;
import com.kmaebashi.jsonparser.JsonParseException;
import com.kmaebashi.jsonparser.JsonParser;

import java.io.IOException;
import java.io.Reader;

public class JsonParserImpl implements JsonParser {
    Reader reader;
    public JsonParserImpl(Reader reader) {
        this.reader = reader;
    }

    public JsonElement parse() throws IOException, JsonParseException {
        return null;
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}
