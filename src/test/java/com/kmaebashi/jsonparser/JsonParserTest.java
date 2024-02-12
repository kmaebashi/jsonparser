package com.kmaebashi.jsonparser;

import com.kmaebashi.TestUtil;
import com.kmaebashi.jsonparserimpl.JsonValueImpl;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {
    @Test
    void parseTest001() throws Exception {
        JsonElement elem;
        try (var parser = JsonParser.newInstance("test_input\\test04.json");
             var writer = new FileWriter("test_output\\parseTest001.txt")) {
            elem = parser.parse();
            parseSub(elem, writer);
        }
        assertTrue(TestUtil.compareFiles("test_output\\parseTest001.txt", "test_expected\\parseTest001.txt"));

        try (var writer = new FileWriter("test_output\\parseTest001_2.txt")) {
            writer.write(elem.stringify());
        }
        try (var parser = JsonParser.newInstance("test_output\\parseTest001_2.txt");
             var writer = new FileWriter("test_output\\parseTest001_3.txt")) {
            JsonElement elem2 = parser.parse();
            writer.write(elem2.stringify());
        }
        assertTrue(TestUtil.compareFiles("test_output\\parseTest001_2.txt", "test_output\\parseTest001_3.txt"));
    }

    @Test
    void parseTest002() throws Exception {
        Reader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("test_input\\test04.json"), StandardCharsets.UTF_8));
        try (var parser = JsonParser.newInstance(reader);
             var writer = new FileWriter("test_output\\parseTest002.txt")) {
            JsonElement elem = parser.parse();
            parseSub(elem, writer);
        }
        assertTrue(TestUtil.compareFiles("test_output\\parseTest002.txt", "test_expected\\parseTest002.txt"));
    }

    void parseSub(JsonElement elem, Writer writer) throws Exception {
        if (elem instanceof JsonValue value) {
            if (value.getType() == JsonValueType.INT) {
                writer.write("INT:" + value.getInt() + " line.." + value.getLineNumber() + "\r\n");
            } else if (value.getType() == JsonValueType.REAL) {
                writer.write("REAL:" + value.getReal() + " line.." + value.getLineNumber()  + "\r\n");
            } else if (value.getType() == JsonValueType.STRING) {
                writer.write("STRING:" + value.getString() + " line.." + value.getLineNumber()  + "\r\n");
            } else if (value.getType() == JsonValueType.BOOLEAN) {
                writer.write("BOOLEAN:" + value.getBoolean() + " line.." + value.getLineNumber()  + "\r\n");
            } else if (value.getType() == JsonValueType.NULL) {
                writer.write("NULL:" +  " line.." + value.getLineNumber() + "\r\n");
            }
        } else if (elem instanceof JsonObject obj) {
            Map<String, JsonElement> map = obj.getMap();
            writer.write("{ line.." + obj.getLeftBraceLineNumber() + "\r\n");
            for (var key : map.keySet()) {
                writer.write("" + key + ":");
                parseSub(map.get(key), writer);
            }
            writer.write("} line.." + obj.getRightBraceLineNumber() + "\r\n");
        } else if (elem instanceof JsonArray array) {
            List<JsonElement> list = array.getArray();
            writer.write("[ line.." + array.getLeftBracketLineNumber() + "\r\n");
            int i = 0;
            for (var e : list) {
                writer.write("[" + i + "]");
                i++;
                parseSub(e, writer);
            }
            writer.write("] line.." + array.getRightBracketLineNumber() + "\r\n");
        }
    }

    @Test
    void keyLineNumberTest() throws Exception {
        try (var parser = JsonParser.newInstance("test_input\\key_line_number.json")) {
            JsonObject obj = (JsonObject)parser.parse();
            assertEquals(2, obj.getKeyLineNumber("key1"));
            assertEquals(6, obj.getKeyLineNumber("key2"));
        }
    }

    @Test
    void unmodifiableTest() throws Exception {
        try (var parser = JsonParser.newInstance("test_input\\test01.json")) {
            JsonObject obj = (JsonObject)parser.parse();

            try {
                obj.getMap().put("dummy", new JsonValueImpl(5));
                fail();
            } catch (Exception ex) {
                assertInstanceOf(UnsupportedOperationException.class, ex);
            }
            try {
                JsonArray array = (JsonArray)obj.getMap().get("array");
                array.getArray().add(new JsonValueImpl(5));
                fail();
            } catch (Exception ex) {
                assertInstanceOf(UnsupportedOperationException.class, ex);
            }
        }
    }

    @Test
    void createJsonTest() throws Exception {
        List<JsonElement> array = new ArrayList<>();

        array.add(JsonValue.createIntValue(5));
        array.add(JsonValue.createRealValue(5.2));
        array.add(JsonValue.createStringValue("abc"));
        array.add(JsonValue.createBooleanValue(true));
        array.add(JsonValue.createBooleanValue(false));
        array.add(JsonValue.createNullValue());
        JsonArray jsonArray = JsonArray.newInstance(array);

        Map<String, JsonElement> map = new LinkedHashMap<>();
        map.put("array", jsonArray);
        Map<String, JsonElement> subMap = new LinkedHashMap<>();
        subMap.put("int", JsonValue.createIntValue(6));
        subMap.put("real", JsonValue.createRealValue(6.3));
        map.put("sub", JsonObject.newInstance(subMap));
        map.put("int", JsonValue.createIntValue(7));
        map.put("real", JsonValue.createRealValue(7.2));
        map.put("string", JsonValue.createStringValue("def"));
        map.put("booleanTrue", JsonValue.createBooleanValue(true));
        map.put("booleanFalse", JsonValue.createBooleanValue(false));
        map.put("null", JsonValue.createNullValue());

        JsonObject obj = JsonObject.newInstance(map);

        try (var writer = new FileWriter("test_output\\createJsonTest.txt")) {
            writer.write(obj.stringify());
        }
        assertTrue(TestUtil.compareFiles("test_output\\createJsonTest.txt", "test_expected\\createJsonTest.txt"));
    }
}