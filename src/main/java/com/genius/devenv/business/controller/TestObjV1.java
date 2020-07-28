package com.genius.devenv.business.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestObjV1 {
    private String name;
    private String nickName;
    private Long age;
    private Long tall;
    public Boolean isAdult() {
        return this.tall > 19 ? Boolean.TRUE : Boolean.FALSE;
    }
}