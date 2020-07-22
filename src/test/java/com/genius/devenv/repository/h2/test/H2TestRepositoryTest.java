package com.genius.devenv.repository.h2.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class H2TestRepositoryTest {
    @Autowired
    private H2TestRepository h2TestRepository;
    @Test
    public void H2_JPA_기본테스트(){
        List<H2TestDao> h2TestDaoList = h2TestRepository.findAll();
        h2TestDaoList.stream().forEach(v-> log.info("{},{},{}",v.getId(),v.getName(),v.getAge()));
        assertThat(h2TestDaoList).isNotEmpty();

    }

}