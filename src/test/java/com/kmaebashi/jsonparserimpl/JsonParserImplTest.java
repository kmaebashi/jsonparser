package com.kmaebashi.jsonparserimpl;

import com.kmaebashi.jsonparser.JsonElement;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserImplTest {

    @Test
    void parseTest001() throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("test_input\\test01.json"), StandardCharsets.UTF_8));
             var writer = new FileWriter("test_output\\parseTest001.txt")) {
            JsonParserImpl paserImpl = new JsonParserImpl(reader);
            JsonElement result = paserImpl.parse();
            writer.write(result.toString());
        }
    }
}