package com.kmaebashi.jsonparser;

import org.junit.jupiter.api.Test;

import javax.print.attribute.IntegerSyntax;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassMapperTest {

    @Test
    void toJsonTest001() throws Exception {
        List<Test1> test1List = new ArrayList<>();
        test1List.add(new Test1(2, 3, 2.1f, 2.2f, 3.1, 3.2, true, false, "def", null, null, null, null));
        test1List.add(new Test1(3, null, 2.2f, null, 3.3, null, false, null, "ghi", null, null, null, null));

        Test1 top = new Test1(1, 2, 2.0f, 2.1f, 3.0, 3.1, true, false, "abc", new int[] {1, 2, 3}, test1List,
                new Test1(4, 5, 2.3f, 2.4f, 3.3, 3.4, false, true, "jkl", null, null, null, null),
                new Test1[]{
                        new Test1(5, 6, 2.4f, 2.5f, 3.4, 3.5, true, false, "mno", null, null, null, null),
                        new Test1(6, 7, 2.5f, 2.6f, 3.5, 3.6, false, true, "pqr", null, null, null, null),
                });

        System.out.println(ClassMapper.toJson(top));
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
                    "floatObj":2.0999999046325684,
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
                    "test1List":[
                        {
                            "intValue":2,
                            "intObj":3,
                            "floatValue":2.0999999046325684,
                            "floatObj":2.200000047683716,
                            "doubleValue":3.1,
                            "doubleObj":3.2,
                            "boolValue":true,
                            "boolObj":false,
                            "strValue":"def",
                            "intArray":null,
                            "test1List":null,
                            "child":null,
                            "childArray":null
                        },
                        {
                            "intValue":3,
                            "intObj":null,
                            "floatValue":2.200000047683716,
                            "floatObj":null,
                            "doubleValue":3.3,
                            "doubleObj":null,
                            "boolValue":false,
                            "boolObj":null,
                            "strValue":"ghi",
                            "intArray":null,
                            "test1List":null,
                            "child":null,
                            "childArray":null
                        }
                    ],
                    "child":{
                        "intValue":4,
                        "intObj":5,
                        "floatValue":2.299999952316284,
                        "floatObj":2.4000000953674316,
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
                            "floatValue":2.4000000953674316,
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
                            "floatObj":2.5999999046325684,
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
    }

    @Test
    void genericTest() {
        List<Integer> list = new ArrayList<>();

        Class c = list.getClass();
        TypeVariable[] tv = c.getTypeParameters();
        System.out.println("tv.length.." + tv.length);
        System.out.println("tv[0].getGenericDeclaration().." + tv[0].getGenericDeclaration());
        System.out.println("tv[0].getName().." + tv[0].getName());
    }
}