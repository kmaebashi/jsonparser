package com.kmaebashi.jsonparser;

import com.kmaebashi.TestUtil;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class ClassMapperTest {

    @Test
    void toJsonTest001() throws Exception {
        List<Test1> test1List = new ArrayList<>();
        test1List.add(new Test1(2, 3, 2.1f, 2.2f, 3.1, 3.2, true, false, "def", null, null, null, null, null, null, null, null));
        test1List.add(new Test1(3, null, 2.2f, null, 3.3, null, false, null, "ghi", null, null, null, null, null, null, null, null));

        List<Integer> intList = new ArrayList<>();
        intList.add(1);
        intList.add(2);
        intList.add(3);

        Test1 top = new Test1(1, 2, 2.0f, 2.1f, 3.0, 3.1, true, false, "abc",
                            new int[] {1, 2, 3}, new float[] {1.1f, 2.1f, 3.1f}, new double[] {1.2, 2.2, 3.3},
                            new boolean[] {true, false}, new String[] {"ary0", "ary1"}, test1List,
                new Test1(4, 5, 2.3f, 2.4f, 3.3, 3.4, false, true, "jkl", null, null, null, null, null, null, null, null),
                new Test1[]{
                        new Test1(5, 6, 2.4f, 2.5f, 3.4, 3.5, true, false, "mno", null, null, null, null, null, null, null, null),
                        new Test1(6, 7, 2.5f, 2.6f, 3.5, 3.6, false, true, "pqr", null, null, null, null, null, null, null, null),
                });

        try (var writer = new FileWriter("test_output/toJsonTest001.txt")) {
            writer.write(ClassMapper.toJson(top));
        }
        assertTrue(TestUtil.compareFiles("test_output/toJsonTest001.txt", "test_expected/toJsonTest001.txt"));
    }

    @Test
    void toJsonTest002() throws Exception {
        Test2 test2 = new Test2();

        try (var writer = new FileWriter("test_output/toJsonTest002.txt")) {
            writer.write(ClassMapper.toJson(test2));
        }
        assertTrue(TestUtil.compareFiles("test_output/toJsonTest002.txt", "test_expected/toJsonTest002.txt"));
    }

    @Test
    void toObjectTest001() throws Exception {
        Integer int1 = ClassMapper.toObject("5", int.class);
        assertEquals(5, int1.intValue());
        Integer int2 = ClassMapper.toObject("6", Integer.class);
        assertEquals(6, int2.intValue());

        Float float1 = ClassMapper.toObject("7.0", float.class);
        assertEquals(7.0, float1.floatValue());
        Float float2 = ClassMapper.toObject("8.0", Float.class);
        assertEquals(8.0, float2.floatValue());

        Double double1 = ClassMapper.toObject("9.0", double.class);
        assertEquals(9.0, double1.floatValue());
        Double double2 = ClassMapper.toObject("10.0", Double.class);
        assertEquals(10.0, double2.doubleValue());

        Boolean bool1 = ClassMapper.toObject("true", boolean.class);
        assertEquals(true, bool1.booleanValue());
        Boolean bool2 = ClassMapper.toObject("false", boolean.class);
        assertEquals(false, bool2.booleanValue());

        String str1 = ClassMapper.toObject("\"abc\"", String.class);
        assertEquals("abc", str1);
    }

    @Test
    void toObjectArrayIntTest001() throws Exception {
        int[] array = ClassMapper.toObject("[1, 2, 3]", int[].class);
        assertEquals(3, array.length);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
    }

    @Test
    void toObjectArrayIntTest002() throws Exception {
        Integer[] array = ClassMapper.toObject("[1, 2, null]", Integer[].class);
        assertEquals(3, array.length);
        assertEquals(1, array[0].intValue());
        assertEquals(2, array[1].intValue());
        assertEquals(null, array[2]);
    }

    @Test
    void toObjectArrayFloatTest001() throws Exception {
        float[] array = ClassMapper.toObject("[1, 2.0, 3]", float[].class);
        assertEquals(3, array.length);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
    }

    @Test
    void toObjectArrayFloatTest002() throws Exception {
        Float[] array = ClassMapper.toObject("[1, 2.0, null]", Float[].class);
        assertEquals(3, array.length);
        assertEquals(1, array[0].intValue());
        assertEquals(2, array[1].intValue());
        assertEquals(null, array[2]);
    }

    @Test
    void toObjectArrayDoubleTest001() throws Exception {
        double[] array = ClassMapper.toObject("[1, 2.0, 3]", double[].class);
        assertEquals(3, array.length);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
    }

    @Test
    void toObjectArrayDoubleTest002() throws Exception {
        Double[] array = ClassMapper.toObject("[1, 2.0, null]", Double[].class);
        assertEquals(3, array.length);
        assertEquals(1, array[0].intValue());
        assertEquals(2, array[1].intValue());
        assertEquals(null, array[2]);
    }

    @Test
    void toObjectArrayBooleanTest001() throws Exception {
        boolean[] array = ClassMapper.toObject("[true, false]", boolean[].class);
        assertEquals(2, array.length);
        assertEquals(true, array[0]);
        assertEquals(false, array[1]);
    }

    @Test
    void toObjectArrayBooleanTest002() throws Exception {
        Boolean[] array = ClassMapper.toObject("[true, false, null]", Boolean[].class);
        assertEquals(3, array.length);
        assertEquals(true, array[0]);
        assertEquals(false, array[1]);
        assertEquals(null, array[2]);
    }

    @Test
    void toObjectArrayStringTest001() throws Exception {
        String[] array = ClassMapper.toObject("[\"abc\", \"def\", null]", String[].class);
        assertEquals(3, array.length);
        assertEquals("abc", array[0]);
        assertEquals("def", array[1]);
        assertEquals(null, array[2]);
    }

    @Test
    void toObjectObjectTest001() throws Exception {
        String jsonStr = """
                {
                    "intValue":1,
                    "intObj":2,
                    "floatValue":2.0,
                    "floatObj":2.1,
                    "doubleValue":3.0,
                    "doubleObj":3.1,
                    "boolValue":true,
                    "boolObj":false,
                    "strValue":"abc",
                    "intArray":[
                        1,
                        2,
                        3
                    ],
                    "floatArray":[
                        1.1,
                        2.1,
                        3.1
                    ],
                    "doubleArray":[
                        1.2,
                        2.2,
                        3.2
                    ],
                    "booleanArray":[
                        true,
                        false
                    ],
                    "stringArray":[
                        "abc",
                        "def",
                        null
                    ],
                    "child":{
                        "intValue":4,
                        "intObj":5,
                        "floatValue":2.3,
                        "floatObj":2.4,
                        "doubleValue":3.3,
                        "doubleObj":3.4,
                        "boolValue":false,
                        "boolObj":true,
                        "strValue":"jkl",
                        "intArray":null,
                        "test1List":null,
                        "child":null,
                        "childArray":null
                    },
                    "childArray":[
                        {
                            "intValue":5,
                            "intObj":6,
                            "floatValue":2.4,
                            "floatObj":2.5,
                            "doubleValue":3.4,
                            "doubleObj":3.5,
                            "boolValue":true,
                            "boolObj":false,
                            "strValue":"mno",
                            "intArray":null,
                            "test1List":null,
                            "child":null,
                            "childArray":null
                        },
                        {
                            "intValue":6,
                            "intObj":7,
                            "floatValue":2.5,
                            "floatObj":2.6,
                            "doubleValue":3.5,
                            "doubleObj":3.6,
                            "boolValue":false,
                            "boolObj":true,
                            "strValue":"pqr",
                            "intArray":null,
                            "test1List":null,
                            "child":null,
                            "childArray":null
                        }
                    ]
                }
                """;
        Test1 test1 = ClassMapper.toObject(jsonStr, Test1.class);
        assertEquals(1, test1.intValue);
        assertEquals(2, test1.intObj);
        assertEquals(2.0f, test1.floatValue, 0.001);
        assertEquals(2.1f, test1.floatObj, 0.001);
        assertEquals(3.0, test1.doubleValue, 0.001);
        assertEquals(3.1, test1.doubleObj, 0.001);
        assertEquals(true, test1.boolValue);
        assertEquals(false, test1.boolObj);
        assertEquals("abc", test1.strValue);
        assertArrayEquals(new int[] {1, 2, 3}, test1.intArray);
        assertArrayEquals(new float[] {1.1f, 2.1f, 3.1f}, test1.floatArray, 0.001f);
        assertArrayEquals(new double[] {1.2, 2.2, 3.2}, test1.doubleArray, 0.001);
        assertArrayEquals(new boolean[] {true, false}, test1.booleanArray);
        assertArrayEquals(new String[] {"abc", "def", null}, test1.stringArray);

        assertEquals(4, test1.child.intValue);
        assertEquals(5, test1.child.intObj);
        assertEquals(2.3f, test1.child.floatValue, 0.001);
        assertEquals(2.4f, test1.child.floatObj, 0.001);
        assertEquals(3.3, test1.child.doubleValue, 0.001);
        assertEquals(3.4, test1.child.doubleObj, 0.001);
        assertEquals(false, test1.child.boolValue);
        assertEquals(true, test1.child.boolObj);
        assertEquals("jkl", test1.child.strValue);
        assertEquals(null, test1.child.intArray);
        assertEquals(null, test1.child.floatArray);
        assertEquals(null, test1.child.doubleArray);
        assertEquals(null, test1.child.booleanArray);
        assertEquals(null, test1.child.test1List);
        assertEquals(null, test1.child.child);
        assertEquals(null, test1.child.childArray);

        assertEquals(2, test1.childArray.length);
        assertEquals(5, test1.childArray[0].intValue);
        assertEquals(6, test1.childArray[0].intObj);
        assertEquals(2.4f, test1.childArray[0].floatValue, 0.001);
        assertEquals(2.5f, test1.childArray[0].floatObj, 0.001);
        assertEquals(3.4, test1.childArray[0].doubleValue, 0.001);
        assertEquals(3.5, test1.childArray[0].doubleObj, 0.001);
        assertEquals(true, test1.childArray[0].boolValue);
        assertEquals(false, test1.childArray[0].boolObj);
        assertEquals("mno", test1.childArray[0].strValue);
        assertEquals(null, test1.childArray[0].intArray);
        assertEquals(null, test1.childArray[0].test1List);
        assertEquals(null, test1.childArray[0].child);
        assertEquals(null, test1.childArray[0].childArray);

        assertEquals(6, test1.childArray[1].intValue);
        assertEquals(7, test1.childArray[1].intObj);
        assertEquals(2.5f, test1.childArray[1].floatValue, 0.001);
        assertEquals(2.6f, test1.childArray[1].floatObj, 0.001);
        assertEquals(3.5, test1.childArray[1].doubleValue, 0.001);
        assertEquals(3.6, test1.childArray[1].doubleObj, 0.001);
        assertEquals(false, test1.childArray[1].boolValue);
        assertEquals(true, test1.childArray[1].boolObj);
        assertEquals("pqr", test1.childArray[1].strValue);
        assertEquals(null, test1.childArray[1].intArray);
        assertEquals(null, test1.childArray[1].test1List);
        assertEquals(null, test1.childArray[1].child);
        assertEquals(null, test1.childArray[1].childArray);
    }

    @Test
    void toObjectTypeMismatchErrorTest001() throws Exception {
        try {
            Test3 test3 = ClassMapper.toObject("{\"intValue\": 5.5}", Test3.class);
        } catch (TypeMismatchException ex) {
            assertEquals("型の不一致エラー。整数型に対してREALを設定しようとしています。(line:1)。", ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    void toObjectTypeMismatchErrorTest002() throws Exception {
        try {
            Test3 test3 = ClassMapper.toObject("{\"floatValue\": false}", Test3.class);
        } catch (TypeMismatchException ex) {
            assertEquals("型の不一致エラー。(単精度の)実数型に対してBOOLEANを設定しようとしています。(line:1)。", ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    void toObjectTypeMismatchErrorTest003() throws Exception {
        try {
            Test3 test3 = ClassMapper.toObject("{\"doubleValue\": \"abc\"}", Test3.class);
        } catch (TypeMismatchException ex) {
            assertEquals("型の不一致エラー。実数型に対してSTRINGを設定しようとしています。(line:1)。", ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    void toObjectTypeMismatchErrorTest004() throws Exception {
        try {
            Test3 test3 = ClassMapper.toObject("{\"booleanValue\": 5}", Test3.class);
        } catch (TypeMismatchException ex) {
            assertEquals("型の不一致エラー。ブーリアン型に対してINTを設定しようとしています。(line:1)。", ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    void toObjectTypeMismatchErrorTest005() throws Exception {
        try {
            Test3 test3 = ClassMapper.toObject("{\"stringValue\": 5.0}", Test3.class);
        } catch (TypeMismatchException ex) {
            assertEquals("型の不一致エラー。文字列型に対してREALを設定しようとしています。(line:1)。", ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    void toObjectTypeMismatchErrorTest006() throws Exception {
        try {
            Test3 test3 = ClassMapper.toObject("{\"intArray\": 5}", Test3.class);
        } catch (TypeMismatchException ex) {
            assertEquals("型の不一致エラー。配列に対してINTを設定しようとしています。(line:1)。", ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    void toObjectTypeMismatchErrorTest007() throws Exception {
        try {
            Test3 test3 = ClassMapper.toObject("{\"child\": 5}", Test3.class);
        } catch (TypeMismatchException ex) {
            assertEquals("型の不一致エラー。オブジェクトに対してINTを設定しようとしています。(line:1)。", ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    void toObjectTypeMismatchErrorTest008() throws Exception {
        try {
            Test3 test3 = ClassMapper.toObject("{\"intList\": [1,2,3]}", Test3.class);
        } catch (JsonDeserializeException ex) {
            assertEquals("Listコレクションへの変換はサポートしていません。(line:1)。", ex.getMessage());
            assertEquals(1, ex.getLineNumber());
            return;
        }
        fail();
    }

    @Test
    void toObjectTypeMismatchErrorTest009() throws Exception {
        try {
            Test3 test3 = ClassMapper.toObject("{\"intArray\": [1, 2.2, 3]}", Test3.class);
        } catch (TypeMismatchException ex) {
            assertEquals("型の不一致エラー。intに対してREALを設定しようとしています。(line:1)。", ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    void toObjectTypeMismatchErrorTest010() throws Exception {
        try {
            Test3 test3 = ClassMapper.toObject("{\"floatArray\": [1, true, 3]}", Test3.class);
        } catch (TypeMismatchException ex) {
            assertEquals("型の不一致エラー。floatに対してBOOLEANを設定しようとしています。(line:1)。", ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    void toObjectTypeMismatchErrorTest011() throws Exception {
        try {
            Test3 test3 = ClassMapper.toObject("{\"doubleArray\": [1, true, 3]}", Test3.class);
        } catch (TypeMismatchException ex) {
            assertEquals("型の不一致エラー。doubleに対してBOOLEANを設定しようとしています。(line:1)。", ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    void toObjectTypeMismatchErrorTest012() throws Exception {
        try {
            Test3 test3 = ClassMapper.toObject("{\"booleanArray\": [true, false, null]}", Test3.class);
        } catch (TypeMismatchException ex) {
            assertEquals("型の不一致エラー。booleanに対してNULLを設定しようとしています。(line:1)。", ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    void toObjectTypeMismatchErrorTest013() throws Exception {
        try {
            Test3 test3 = ClassMapper.toObject("{\"stringArray\": [\"ary0\", \"ary1\", 5]}", Test3.class);
        } catch (TypeMismatchException ex) {
            assertEquals("型の不一致エラー。文字列型に対してINTを設定しようとしています。(line:1)。", ex.getMessage());
            return;
        }
        fail();
    }
}