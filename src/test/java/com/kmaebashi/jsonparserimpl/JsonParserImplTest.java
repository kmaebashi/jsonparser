package com.kmaebashi.jsonparserimpl;

import com.kmaebashi.TestUtil;
import com.kmaebashi.jsonparser.JsonElement;
import com.kmaebashi.jsonparser.JsonParseException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserImplTest {

    @Test
    void parseTest001() throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("test_input\\test02.json"), StandardCharsets.UTF_8));
             var writer = new FileWriter("test_output\\parseImplTest001.txt")) {
            JsonParserImpl paserImpl = new JsonParserImpl(reader);
            JsonElement result = paserImpl.parse();
            writer.write(result.stringify());
        }
        assertTrue(TestUtil.compareFiles("test_output\\parseImplTest001.txt", "test_expected\\parseImplTest001.txt"));
    }

    @Test
    void parseTest002() throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("test_input\\test03.json"), StandardCharsets.UTF_8));
             var writer = new FileWriter("test_output\\parseImplTest002.txt")) {
            JsonParserImpl paserImpl = new JsonParserImpl(reader);
            JsonElement result = paserImpl.parse();
            writer.write(result.stringify());
        }
        assertTrue(TestUtil.compareFiles("test_output\\parseImplTest002.txt", "test_expected\\parseImplTest002.txt"));
    }

    @Test
    void parseTest003() throws Exception {
        JsonParserImpl parser = new JsonParserImpl(new StringReader("123"));

        JsonElement elem = parser.parse();
        assertEquals("123", elem.stringify());
    }

    @Test
    void parseTest004() throws Exception {
        JsonParserImpl parser = new JsonParserImpl(new StringReader("123.456"));

        JsonElement elem = parser.parse();
        assertEquals("123.456", elem.stringify());
    }
    @Test
    void parseTest005() throws Exception {
        JsonParserImpl parser = new JsonParserImpl(new StringReader("\"abc\""));

        JsonElement elem = parser.parse();
        assertEquals("\"abc\"", elem.stringify());
    }

    @Test
    void parseTest006() throws Exception {
        JsonParserImpl parser = new JsonParserImpl(new StringReader("true"));

        JsonElement elem = parser.parse();
        assertEquals("true", elem.stringify());
    }

    @Test
    void parseTest007() throws Exception {
        JsonParserImpl parser = new JsonParserImpl(new StringReader("false"));

        JsonElement elem = parser.parse();
        assertEquals("false", elem.stringify());
    }

    @Test
    void parseTest008() throws Exception {
        JsonParserImpl parser = new JsonParserImpl(new StringReader("null"));

        JsonElement elem = parser.parse();
        assertEquals("null", elem.stringify());
    }

    @Test
    void parseErrorTest001() throws Exception {
        JsonParserImpl parser = new JsonParserImpl(new StringReader(","));

        try {
            parser.parse();
            fail();
        } catch (JsonParseException ex) {
            assertEquals("不正なトークンです(COMMA) at 1", ex.getMessage());
        }
    }

    @Test
    void parseErrorTest002() throws Exception {
        JsonParserImpl parser = new JsonParserImpl(new StringReader("[1,2,]"));

        try {
            parser.parse();
            fail();
        } catch (JsonParseException ex) {
            assertEquals("JSONでは配列の末尾に,は付けられません at 1", ex.getMessage());
        }
    }

    @Test
    void parseErrorTest003() throws Exception {
        JsonParserImpl parser = new JsonParserImpl(new StringReader("[1,2{}]"));

        try {
            parser.parse();
            fail();
        } catch (JsonParseException ex) {
            assertEquals("配列の要素の終わりがカンマでも]でもありません(LEFT_BRACE) at 1", ex.getMessage());
        }
    }

    @Test
    void parseErrorTest004() throws Exception {
        JsonParserImpl parser = new JsonParserImpl(new StringReader("{3:1}"));

        try {
            parser.parse();
            fail();
        } catch (JsonParseException ex) {
            assertEquals("オブジェクトのキーが文字列ではありません(INT) at 1", ex.getMessage());
        }
    }

    @Test
    void parseErrorTest005() throws Exception {
        JsonParserImpl parser = new JsonParserImpl(new StringReader("{\"abc\",123}"));

        try {
            parser.parse();
            fail();
        } catch (JsonParseException ex) {
            assertEquals("オブジェクトのキーの後ろがコロンではありません(COMMA) at 1", ex.getMessage());
            assertEquals(1, ex.getLineNumber());
        }
    }

    @Test
    void parseErrorTest006() throws Exception {
        JsonParserImpl parser = new JsonParserImpl(new StringReader("{\"abc\":123,\"abc\":456}"));

        try {
            parser.parse();
            fail();
        } catch (JsonParseException ex) {
            assertEquals("オブジェクトのキーが重複しています(abc) at 1", ex.getMessage());
        }
    }

    @Test
    void parseErrorTest007() throws Exception {
        JsonParserImpl parser = new JsonParserImpl(new StringReader("{\"abc\":123,}"));

        try {
            parser.parse();
            fail();
        } catch (JsonParseException ex) {
            assertEquals("JSONではオブジェクトの末尾に,は付けられません at 1", ex.getMessage());
        }
    }

    @Test
    void parseErrorTest008() throws Exception {
        JsonParserImpl parser = new JsonParserImpl(new StringReader("{\"abc\":123:}"));

        try {
            parser.parse();
            fail();
        } catch (JsonParseException ex) {
            assertEquals("オブジェクトの要素の終わりがカンマでも}でもありません(COLON) at 1", ex.getMessage());
        }
    }

}