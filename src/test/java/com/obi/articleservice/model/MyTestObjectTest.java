package com.obi.articleservice.model;

public class MyTestObjectTest {

    void testDefaultConstructor(){
        MyTestObject myTestObject = new MyTestObject();
        myTestObject.setMutableProperty("test1");
        myTestObject.setMutableProperty("test2");
        myTestObject.setMutableProperty("test3");
    }

    void testRequiredArgsConstructor(){
        new MyTestObject("final property");
    }

    void testAllArgsConstructor(){
        new MyTestObject("final property", "my not final property");
    }

    void testRequiredArgsConstructorWithSetNotFinal(){
        MyTestObject myTestObject = new MyTestObject("final property");
        myTestObject.setMutableProperty("not final");
    }


}
