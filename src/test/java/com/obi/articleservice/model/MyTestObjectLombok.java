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

}
