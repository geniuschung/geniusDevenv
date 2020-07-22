package com.genius.devenv.repository.h2.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
class H2TestMapperTest {
    @Autowired
    private H2TestMapper h2TestMapper;
    @Test
    public void h2_마이바티스_테스트(){
        h2TestMapper.selectAll().stream()
                .forEach(v -> log.info("{},{},{}",v.getId(),v.getName(),v.getName()));

    }

}