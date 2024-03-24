# jsonparser
## これは何?
JavaによるJSONパーサです。

## 使い方
```
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

```
// オブジェクトからJSONへの変換
String jsonStr = ClassMapper.toJson(obj);

// JSONからオブジェクトへの変換
Test1 test1 = ClassMapper.toObject(Test1.class);
}
```
- ClassMapperのstaticメソッドを使うことで、通常のクラスからJSONへの変換、JSONから通常のクラスへの変換ができます。
- JSONオブジェクトのキーが、同名のクラスのpublicフィールドにマップされます。getterやsetterは使いません(使えません)。
- JavaのListはJSONの配列にマップしますが、逆向きは不可です。Listの要素の方が実行時には特定できないためです。配列を使ってください。
- 詳細はテストコード(src/test/com/kmaebashi/jsonparser/ClassMapperTest.java)を参照してください。
- 
| JSONの型 | クラスの型 | 備考 |
| --- | --- | --- |
| 整数型 | int | |
| 整数型 | Integer | |
| 実数型 | float | |
| 実数型 | Float | |
| 実数型 | double | |
| 実数型 | Double | |
| 論理型 | boolean | |
| 論理型 | Boolean | |
| 文字列型 | String | |
| 配列 | [] | Javaの配列 |
| 配列 | List | クラス→JSONのみ可。逆向きは不可 |
| オブジェクト | 任意のクラス | JSONオブジェクトのキーが同名のpublicフィールドにマップされる |



## ライセンスについて
NYSL Version 0.9982とします。作者は一切の著作権を主張しませんので、改変するなり煮るなり焼くなり好きにしてください。
http://www.kmonos.net/nysl/
