package com.obi.articleservice.test;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class LutzContext {

    // Mapping name -> Bean
    public final Map<String, Object> context = new HashMap<>();


}
