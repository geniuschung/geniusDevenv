package com.genius.devenv.repository.oracle.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
//@Transactional
class TestMapperTest {
    @Autowired
    private TestMapper testMapper;

    @Test
    void 테스트_테이블_전체조회() {
        List<TestDao> testDaoList = testMapper.selectAll();
        for(TestDao testDao : testDaoList){
            log.info("{} , {} , {}",testDao.getId(),testDao.getName(),testDao.getAge());
        }
        assertThat(testDaoList).isNotEmpty();
    }

    @Test
    void 테스트_테이블_전체조회2() {
        List<TestDao> testDaoList = testMapper.selectAll();
        for(TestDao testDao : testDaoList){
            log.info("{} , {} , {}",testDao.getId(),testDao.getName(),testDao.getAge());
        }
        assertThat(testDaoList).isNotEmpty();
    }
}