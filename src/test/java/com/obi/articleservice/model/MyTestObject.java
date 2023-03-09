package com.obi.articleservice.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
//@NoArgsConstructor  // not possible since property is final and needs to be instantiated
public class MyTestObject {

    private final String property; // always use private final to make properties immutable
    private String mutableProperty;


    // CONSTRUCTORS
    public MyTestObject() { // default constructor with default values for final properties
        this.property = "property dasdakjshdajsdh";
    }
    public MyTestObject(String property) { // requiredArgsConstructor with all final properties
        this.property = property;
    }

    public MyTestObject(String property, String  mutableProperty) { // allArgsConstructor with all properties
        this.property = property;
        this.mutableProperty = mutableProperty;
    }



}
