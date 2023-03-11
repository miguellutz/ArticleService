package com.obi.articleservice.model;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
//@NoArgsConstructor  // not possible since property is final and needs to be instantiated
public class MyTestObjectLombok {

    private final String property; // always use private final to make properties immutable
    private String mutableProperty;

    // CONSTRUCTORS
    public MyTestObjectLombok() { // default constructor with default values for final properties
        this.property = "property dasdakjshdajsdh";
    }

    public void setMutableProperty(String mutableProperty) {
        this.mutableProperty = mutableProperty;
    }

    public MyTestObjectLombok(String property) {
        this.property = property;
    }

    public MyTestObjectLombok(String property, String mutableProperty) {    // manual AllArgsConstructor
        this.property = property;
        this.mutableProperty = mutableProperty;
    }
}
