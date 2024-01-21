# jsonparser
## これは何?
JavaによるJSONパーサです。

## 使い方
```
JsonElement elem;
try (var parser = JsonParser.newInstance("test.json")) {
    JsonElement elem = parser.parse();
    ...
}
```
- JsonParser.newInstance()にファイル名を渡してインスタンスを作成し、parse()を呼び出すことでJsonElementが取得できます。
- JsonElementは、サブインタフェースとして、JsonArray, JsonObject, JsonValueを持ちます。これらはそれぞれJsonの配列、オブジェクト、(各種の型の)値を表します。
- JsonArrayはJsonElementのListを、JsonObjectはStringをキーとしてJsonElementを値とするMapを保持しています。
- newInstance()には、ファイル名のほか、java.io.Readerを渡すこともできます。
- 詳細はテストコード(src/test/com/kmaebashi/jsonparser/JsonParserTest.java)を参照してください。
- 各JsonValueは元Jsonの行番号を持ちます。また、JsonArrayとJsonObjectは、開き括弧と閉じ括弧の行番号を持ち、さらにJsonObjectは各キーの行番号も持ちます。これを利用するプログラム側で適切なエラーメッセージを表示するための機能です。

## ライセンスについて
NYSL Version 0.9982とします。作者は一切の著作権を主張しませんので、改変するなり煮るなり焼くなり好きにしてください。
http://www.kmonos.net/nysl/
