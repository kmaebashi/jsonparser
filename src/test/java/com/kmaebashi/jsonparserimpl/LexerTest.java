package com.kmaebashi.jsonparserimpl;

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

class LexerTest {
    @Test
    void getTokenTest001() throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("test_input\\test01.json"), StandardCharsets.UTF_8));
             var writer = new FileWriter("test_output\\getTokenTest001.txt")) {
            Lexer lexer = new Lexer(reader);
            for (;;) {
                Token token = lexer.getToken();
                if (token.type == TokenType.END_OF_FILE) {
                    break;
                }
                writer.write("TokenType.." + token.type + ", str.." + token.tokenString
                             + ", lineNumber.." + token.lineNumber
                             + ", intValue.." + token.intValue + ", realValue.." + token.realValue + "\r\n");
            }
        }
    }

    @Test
    void getTokenError001() throws Exception {
        Lexer lexer = new Lexer(new StringReader("*"));
        try {
            Token token = lexer.getToken();
            fail();
        } catch (JsonParseException ex) {
            assertEquals(ex.getMessage(), "不正な文字(*) at 1");
        }
    }
}