package com.obi.articleservice.test;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ThirdService {

    private final FirstService firstService;
    private final SecondService secondService;

}
