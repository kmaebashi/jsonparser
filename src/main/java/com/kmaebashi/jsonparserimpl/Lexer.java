package com.kmaebashi.jsonparserimpl;

import com.kmaebashi.jsonparser.JsonParseException;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

public class Lexer {
    private int currentLineNumber = 1;
    private Reader reader;

    private enum Status {
        INITIAL,
        MINUS,
        INT_PART,
        DECIMAL_POINT,
        AFTER_DECIMAL_POINT,
        EXP_SIGN,
        EXP,
        ALNUM,
        STRING,
        STRING_ESCAPE,
        STRING_UNICODE,
        STRING_UNICODE2,
        STRING_UNICODE3
    };

    private HashMap<String, TokenType> keywordTable = new HashMap<String,TokenType>() {
        {
            put("true", TokenType.TRUE);
            put("false", TokenType.FALSE);
            put("null", TokenType.NULL);
        }
    };
    private int lookAheadCharacter;
    private boolean lookingAhead = false;

    public Lexer(Reader reader) {
        this.reader = reader;
    }

    private int getc() throws IOException {
        if (this.lookingAhead) {
            this.lookingAhead = false;
            return this.lookAheadCharacter;
        } else {
            return reader.read();
        }
    }

    private void ungetc(int ch) {
        this.lookAheadCharacter = ch;
        this.lookingAhead = true;
    }

    Token getToken() throws IOException, JsonParseException {
        int ch;
        Status currentStatus = Status.INITIAL;
        TokenType tokenType;
        StringBuilder currentToken = new StringBuilder();
        int unicodeCount = 0;
        StringBuilder unicodeHexStr = new StringBuilder();
        ArrayList<Integer> unicodeCodePoints = new ArrayList<>();

        getCFor: for (;;) {
            ch = getc();

            switch (currentStatus) {
                case INITIAL:
                    if (ch == '[') {
                        return new Token(TokenType.LEFT_BRACKET, "[", this.currentLineNumber);
                    } else if (ch == ']') {
                        return new Token(TokenType.RIGHT_BRACKET, "]", this.currentLineNumber);
                    } else if (ch == '{') {
                        return new Token(TokenType.LEFT_BRACE, "{", this.currentLineNumber);
                    } else if (ch == '}') {
                        return new Token(TokenType.RIGHT_BRACE, "}", this.currentLineNumber);
                    } else if (ch == ':') {
                        return new Token(TokenType.COLON, ":", this.currentLineNumber);
                    } else if (ch == ',') {
                        return new Token(TokenType.COMMA, ",", this.currentLineNumber);
                    } else if (Character.isJavaIdentifierStart(ch)) {
                        currentToken.append((char)ch);
                        currentStatus = Status.ALNUM;
                    } else if (ch == '-') {
                        currentToken.append((char) ch);
                        currentStatus = Status.MINUS;
                    } else if (Character.isDigit(ch)) {
                        currentToken.append((char) ch);
                        currentStatus = Status.INT_PART;
                    } else if (ch == '\"') {
                        currentStatus = Status.STRING;
                    } else if (Character.isWhitespace(ch)) {
                        if (ch == '\n') {
                            this.currentLineNumber++;
                        }
                    } else if (ch == -1) {
                        return new Token(TokenType.END_OF_FILE, null, this.currentLineNumber);
                    } else {
                        throw new JsonParseException("不正な文字(" + (char)ch + ")",
                                                     this.currentLineNumber);
                    }
                    break;
                case MINUS:
                    if (Character.isDigit(ch)) {
                        currentToken.append((char)ch);
                        currentStatus = Status.INT_PART;
                    } else {
                        throw new JsonParseException("マイナスの後ろに数字がありません(" + (char)ch + ")",
                                                     this.currentLineNumber);
                    }
                    break;
                case INT_PART:
                    if (Character.isDigit(ch)) {
                        currentToken.append((char)ch);
                    } else if (ch == '.') {
                        currentToken.append((char)ch);
                        currentStatus = Status.DECIMAL_POINT;
                    } else if (ch == 'E' || ch == 'e') {
                        currentToken.append('e');
                        currentStatus = Status.EXP_SIGN;
                    } else {
                        ungetc(ch);
                        int intValue = Integer.parseInt(currentToken.toString());
                        return new Token(TokenType.INT, currentToken.toString(), intValue,
                                         this.currentLineNumber);
                    }
                    break;
                case DECIMAL_POINT:
                    if (Character.isDigit(ch)) {
                        currentToken.append((char)ch);
                        currentStatus = Status.AFTER_DECIMAL_POINT;
                    } else {
                        throw new JsonParseException("小数点の後ろに数字がありません(" + (char)ch + ")",
                                                     this.currentLineNumber);
                    }
                    break;
                case AFTER_DECIMAL_POINT:
                    if (Character.isDigit(ch)) {
                        currentToken.append((char)ch);
                    } else if (ch == 'E' || ch == 'e') {
                        currentToken.append('e');
                        currentStatus = Status.EXP_SIGN;
                    } else {
                        ungetc(ch);
                        double doubleValue = Double.parseDouble(currentToken.toString());
                        return new Token(TokenType.REAL, currentToken.toString(), doubleValue,
                                         this.currentLineNumber);
                    }
                    break;
                case EXP_SIGN:
                    if (ch == '+' || ch == '-') {
                        currentToken.append((char)ch);
                    } else {
                        ungetc(ch);
                    }
                    currentStatus = Status.EXP;
                    break;
                case EXP:
                    if (Character.isDigit(ch)) {
                        currentToken.append((char)ch);
                    } else {
                        ungetc(ch);
                        double doubleValue = Double.parseDouble(currentToken.toString());
                        return new Token(TokenType.REAL, currentToken.toString(), doubleValue,
                                this.currentLineNumber);
                    }
                    break;
                case ALNUM:
                    if (Character.isJavaIdentifierStart(ch)) {
                        currentToken.append((char)ch);
                    } else {
                        ungetc(ch);
                        String tokenString = currentToken.toString();
                        if (this.keywordTable.containsKey(tokenString)) {
                            tokenType = this.keywordTable.get(tokenString);
                            return new Token(tokenType, tokenString, this.currentLineNumber);
                        } else {
                            throw new JsonParseException("不正なキーワード(" + tokenString + ")",
                                                         this.currentLineNumber);
                        }
                    }
                    break;
                case STRING:
                    if (ch == '\\') {
                        currentStatus = Status.STRING_ESCAPE;
                    } else if (ch == '\"') {
                        return new Token(TokenType.STRING, currentToken.toString(), this.currentLineNumber);
                    } else {
                        currentToken.append((char)ch);
                    }
                    break;
                case STRING_ESCAPE:
                    if (ch == '\"' || ch == '\\' || ch == '/') {
                        currentToken.append((char)ch);
                        currentStatus = Status.STRING;
                    } else if (ch == 'b') {
                        currentToken.append("\b");
                        currentStatus = Status.STRING;
                    } else if (ch == 'f') {
                        currentToken.append("\f");
                        currentStatus = Status.STRING;
                    } else if (ch == 'n') {
                        currentToken.append("\n");
                        currentStatus = Status.STRING;
                    } else if (ch == 'r') {
                        currentToken.append("\r");
                        currentStatus = Status.STRING;
                    } else if (ch == 't') {
                        currentToken.append("\t");
                        currentStatus = Status.STRING;
                    } else if (ch == 'u') {
                        currentStatus = Status.STRING_UNICODE;
                    } else {
                        throw new JsonParseException("不正なエスケープ文字です(" + (char)ch + ")",
                                                     this.currentLineNumber);
                    }
                    break;
                case STRING_UNICODE:
                    if ((ch >= '0' && ch <= '9')
                        || (ch >= 'a' && ch <= 'f')
                        || (ch >= 'A' && ch <= 'F')) {
                        unicodeHexStr.append((char) ch);
                        unicodeCount++;
                        if (unicodeCount == 4) {
                            int codePoint = Integer.parseInt(unicodeHexStr.toString(), 16);
                            unicodeCodePoints.add(codePoint);
                            unicodeHexStr = new StringBuilder();
                            unicodeCount = 0;
                            currentStatus = Status.STRING_UNICODE2;
                        }
                    } else {
                        throw new JsonParseException("\\uの後ろには16進数4桁が来なければいけません(" + (char)ch + ")",
                                                     this.currentLineNumber);
                    }
                    break;
                case STRING_UNICODE2:
                    if (ch == '\\') {
                        currentStatus = Status.STRING_UNICODE3;
                    } else {
                        int[] codePoints = arrayListToIntArray(unicodeCodePoints);
                        String str = new String(codePoints, 0, codePoints.length);
                        currentToken.append(str);
                        ungetc(ch);
                        currentStatus = Status.STRING;
                    }
                    break;
                case STRING_UNICODE3:
                    if (ch == 'u') {
                        currentStatus = Status.STRING_UNICODE;
                    } else {
                        ungetc(ch);
                        currentStatus = Status.STRING_ESCAPE;
                    }
                    break;
                default:
                    assert(false);
            }
        }
    }

    private int[] arrayListToIntArray(ArrayList<Integer> arrayList) {
        int[] ret = new int[arrayList.size()];

        for (int i = 0; i < arrayList.size(); i++) {
            ret[i] = arrayList.get(i);
        }
        return ret;
    }
}
