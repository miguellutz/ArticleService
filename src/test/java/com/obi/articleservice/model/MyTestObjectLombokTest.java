package com.obi.articleservice.model;

public class MyTestObjectLombokTest {

    void testDefaultConstructor(){
        MyTestObjectLombok myTestObject = new MyTestObjectLombok();
        myTestObject.setMutableProperty("test1");
        myTestObject.setMutableProperty("test2");
        myTestObject.setMutableProperty("test3");
    }

    void testRequiredArgsConstructor(){
        new MyTestObjectLombok("final property");
    }

    void testAllArgsConstructor(){
        new MyTestObjectLombok("final property", "my not final property");
    }

    void testRequiredArgsConstructorWithSetNotFinal(){
        MyTestObjectLombok myTestObject = new MyTestObjectLombok("final property");
        myTestObject.setMutableProperty("not final");
    }


}
