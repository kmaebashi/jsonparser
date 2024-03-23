package com.kmaebashi.jsonparser;

import java.util.List;

public class Test1 {
    public int intValue;
    public Integer intObj;
    public float floatValue;
    public Float floatObj;
    public double doubleValue;
    public Double doubleObj;
    public boolean boolValue;
    public Boolean boolObj;
    public String strValue;
    public int[] intArray;
    public List<Test1> test1List;
    public Test1 child;
    public Test1[] childArray;
    @JsonIgnore
    public int ignoreField;

    public Test1(int intValue, Integer intObj, float floatValue, Float floatObj, double doubleValue, Double doubleObj,
                 boolean boolValue, Boolean boolObj, String strValue,
                 int[] intArray, List<Test1> test1List, Test1 child, Test1[] childArray) {
        this.intValue = intValue;
        this.intObj = intObj;
        this.floatValue = floatValue;
        this.floatObj = floatObj;
        this.doubleValue = doubleValue;
        this.doubleObj = doubleObj;
        this.boolValue = boolValue;
        this.boolObj = boolObj;
        this.strValue = strValue;
        this.intArray = intArray;
        this.test1List = test1List;
        this.child = child;
        this.childArray = childArray;
        this.ignoreField = 999;
    }
    public Test1() {

    };
}
