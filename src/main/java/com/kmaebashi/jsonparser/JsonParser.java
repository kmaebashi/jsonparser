package com.kmaebashi.jsonparser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public interface JsonParser extends AutoCloseable {
    public static JsonParser newInstance(String path)
            throws FileNotFoundException, IOException {
        Reader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(path), StandardCharsets.UTF_8));
        return null;
        // return new JsonParserImpl(reader);
    }

    public static JsonParser newInstance(Reader reader) {
        return null;
        // return new JsonParserImpl(reader);
    }

    public JsonElement parse() throws IOException, JsonParseException;
    public void close() throws IOException;
}
