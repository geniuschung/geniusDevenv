package com.genius.devenv.business.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/test")
public class TestController {
    @GetMapping(value="/getTestObj")
    public TestObjV1 getTestObj() throws Exception {
        if(true){
            throw new Exception("test");
        }

        return TestObjV1.builder().name("정승식").nickName("천재").age(18l).tall(180l).build();

    }
}
