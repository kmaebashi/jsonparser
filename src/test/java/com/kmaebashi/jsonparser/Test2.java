package com.kmaebashi.jsonparser;
import java.util.List;
import java.util.ArrayList;

public class Test2 {
    public List<Integer> intList;
    public List<Float> floatList;
    public List<Double> doubleList;
    public List<Boolean>  booleanList;
    public List<String> stringList;
    public List<Object> objList;

    public Test2() {
        this.intList = new ArrayList<>();
        this.intList.add(1);
        this.intList.add(2);
        this.intList.add(3);
        this.floatList = new ArrayList<>();
        this.floatList.add(1.1f);
        this.floatList.add(2.1f);
        this.floatList.add(3.1f);
        this.doubleList = new ArrayList<>();
        this.doubleList.add(3.1);
        this.doubleList.add(3.2);
        this.doubleList.add(3.3);
        this.booleanList = new ArrayList<>();
        this.booleanList.add(true);
        this.booleanList.add(false);
        this.booleanList.add(null);
        this.stringList = new ArrayList<>();
        this.stringList.add("abc");
        this.stringList.add("def");
        this.stringList.add("ghi");
        this.objList = new ArrayList<>();
        this.objList.add(1);
        this.objList.add(2.1f);
        this.objList.add(3.1);
        this.objList.add(true);
        this.objList.add("jkl");
    }
}
