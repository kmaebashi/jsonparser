package com.kmaebashi.jsonparserimpl;

import com.kmaebashi.jsonparser.JsonArray;
import com.kmaebashi.jsonparser.JsonElement;
import com.kmaebashi.jsonparser.JsonObject;
import com.kmaebashi.jsonparser.JsonParseException;
import com.kmaebashi.jsonparser.JsonParser;
import com.kmaebashi.jsonparser.JsonValue;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

public class JsonParserImpl implements JsonParser {
    Reader reader;
    Lexer lexer;
    private Token lookAheadToken;
    private boolean lookingAhead = false;

    public JsonParserImpl(Reader reader) {
        this.reader = reader;
    }

    private Token getToken() throws IOException, JsonParseException {
        if (this.lookingAhead) {
            this.lookingAhead = false;
            return lookAheadToken;
        } else {
            return lexer.getToken();
        }
    }

    private void ungetToken(Token token) {
        lookAheadToken = token;
        lookingAhead = true;
    }

    public JsonElement parse() throws IOException, JsonParseException {
        this.lexer = new Lexer(this.reader);

        return parseJsonElement();
    }

    JsonElement parseJsonElement() throws IOException, JsonParseException {
        Token token = getToken();

        if (token.type == TokenType.INT) {
            return new JsonValue(token.intValue, token.lineNumber);
        } else if (token.type == TokenType.REAL) {
            return new JsonValue(token.realValue, token.lineNumber);
        } else if (token.type == TokenType.STRING) {
            return new JsonValue(token.tokenString, token.lineNumber);
        } else if (token.type == TokenType.TRUE) {
            return new JsonValue(true, token.lineNumber);
        } else if (token.type == TokenType.FALSE) {
            return new JsonValue(false, token.lineNumber);
        } else if (token.type == TokenType.NULL) {
            return new JsonValue(token.lineNumber);
        } else if (token.type == TokenType.LEFT_BRACKET) {
            return parseArray();
        } else if (token.type == TokenType.LEFT_BRACE) {
            return parseObject();
        } else {
            throw new JsonParseException("不正なトークンです(" + token.type + ")", token.lineNumber);
        }
    }

    private JsonArray parseArray() throws IOException, JsonParseException {
        ArrayList<JsonElement> arrayList = new ArrayList<>();

        Token token;
        for (;;) {
            token = lexer.getToken();
            if (token.type == TokenType.RIGHT_BRACKET) {
                break;
            }
            ungetToken(token);
            JsonElement elem = parseJsonElement();
            arrayList.add(elem);

            token = lexer.getToken();
            if (token.type != TokenType.COMMA) {
                break;
            }
        }
        if (token.type != TokenType.RIGHT_BRACKET) {
            throw new JsonParseException("配列の要素の終わりがカンマでも]でもありません(" + token.type + ")",
                                         token.lineNumber);
        }
        return new JsonArray(Collections.unmodifiableList(arrayList));
    }

    private JsonObject parseObject() throws IOException, JsonParseException {
        SortedMap<String, JsonElement> sortedMap = new TreeMap<>();

        Token token;
        for (;;) {
            token = lexer.getToken();
            if (token.type == TokenType.RIGHT_BRACE) {
                break;
            }
            ungetToken(token);
            Token keyToken = getToken();
            if (keyToken.type != TokenType.STRING) {
                throw new JsonParseException("オブジェクトのキーが文字列ではありません(" + keyToken.type + ")",
                                             token.lineNumber);
            }
            Token colonToken = getToken();
            if (colonToken.type != TokenType.COLON) {
                throw new JsonParseException("オブジェクトのキーの後ろがコロンではありません(" + keyToken.type + ")",
                        token.lineNumber);
            }
            JsonElement elem = parseJsonElement();
            if (sortedMap.containsKey(keyToken.tokenString)) {
                throw new JsonParseException("オブジェクトのキーが重複しています(" + keyToken.tokenString + ")",
                        token.lineNumber);
            }
            sortedMap.put(keyToken.tokenString, elem);

            token = lexer.getToken();
            if (token.type != TokenType.COMMA) {
                break;
            }
        }
        if (token.type != TokenType.RIGHT_BRACE) {
            throw new JsonParseException("オブジェクトの要素の終わりがカンマでも}でもありません(" + token.type + ")",
                    token.lineNumber);
        }
        return new JsonObject(Collections.unmodifiableSortedMap(sortedMap));
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}
