package com.obi.articleservice.test;

import com.obi.articleservice.model.CountryArticle;

public class ContextTest {

    public void test() {
        LutzContext lutzContext = new LutzContext();

        // instanziieren den Service = bean
        FirstService firstService = new FirstService("test");
        // packen wir als bean in den Context
        lutzContext.getContext().put("firstService", firstService);

        // nochmal
        SecondService secondService = new SecondService("eaads");
        lutzContext.getContext().put("secondService", secondService);
        lutzContext.getContext().put("aString", "dhakjshdasd");
        lutzContext.getContext().put("aString", "dhakjshdasd");


//THIRD
        Object firstServiceFromContext = lutzContext.getContext().get("firstService");
        Object secondServiceFromContext = lutzContext.getContext().get("secondService");

        if(firstServiceFromContext!=null && firstServiceFromContext instanceof FirstService
                && secondServiceFromContext !=null && secondServiceFromContext instanceof SecondService) {

            FirstService dependencyFirst = (FirstService)firstServiceFromContext;
            SecondService dependecnySecond = (SecondService)secondServiceFromContext;

            ThirdService thirdService = new ThirdService(dependencyFirst, dependecnySecond);
        }

        if(firstServiceFromContext!=null && firstServiceFromContext instanceof FirstService
                && secondServiceFromContext !=null && secondServiceFromContext instanceof SecondService) {

            FirstService dependencyFirst = (FirstService)firstServiceFromContext;
            SecondService dependecnySecond = (SecondService)secondServiceFromContext;

            FourthService fourthService = new FourthService(dependencyFirst, dependecnySecond);
        }

        new CountryArticle();

    }
}
