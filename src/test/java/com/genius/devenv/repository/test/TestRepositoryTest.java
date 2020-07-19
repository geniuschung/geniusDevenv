package com.genius.devenv.repository.test;

import lombok.extern.slf4j.Slf4j;
import org.graalvm.compiler.core.match.MatchRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class TestRepositoryTest {
    @Autowired
    private TestRepository testRepository;



    @Test
    public void JPA_리파지토리_테스트_selectAll() {

        List<TestDao> testDaoList = testRepository.findAll();
        for(TestDao testDao : testDaoList) {
            log.info("{} , {}, {}",testDao.getId(),testDao.getName(),testDao.getAge());
        }
        assertThat(testDaoList).isNotEmpty();


    }

    @Test
    public void JPA_GETBYID() {
        TestDao testDao = testRepository.getById("geniuschung");
        log.info("{} , {}, {}",testDao.getId(),testDao.getName(),testDao.getAge());

        assertThat(testDao).isNotNull();
        assertThat(testDao.getName()).isEqualTo("정승식");
    }

    @Test//(expected = NullPointerException.class)
    public void JPA_GETBYID_NODATA() {
        TestDao testDao = testRepository.getById("geniuschung11");
        assertThat(testDao).isNull();


    }

    @Test
    public void JPA_FINDBYID_NODATA() {
        Optional<TestDao> testDao = testRepository.findById("geniuschung11");
        String name = testDao.map(TestDao::getName).orElse("userName");
        String id = testDao.map(TestDao::getId).orElse("userID");
        String id2 = testDao.map(TestDao::getId).orElseGet(() -> "userId");
        assertThat(name).isEqualTo("userName");
        assertThat(name).startsWith("user");


    }

    @Test
    public void JPA_FINDBYID() {
        Optional<TestDao> testDao = testRepository.findById("geniuschung");

        String name = testDao.map(TestDao::getName).orElse("userName");
        String id = testDao.map(TestDao::getId).orElse("userID");
        String id2 = testDao.map(TestDao::getId).orElseGet(() ->elseGetMethod());
        String id3 = testDao.map(TestDao::getId).orElseGet(String::new);
        String id4 = testDao.map(TestDao::getId).orElseGet(() -> new String("userID"));





    }
    private String elseGetMethod() {
        return "";
    }


    @Test
    public void JPA_GETBYAGE_여러건() {
        List<TestDao> testDaoList = testRepository.getByAge(8L);
        for(TestDao testDao : testDaoList) {
            log.info("{} , {}, {}",testDao.getId(),testDao.getName(),testDao.getAge());
        }
        assertThat(testDaoList).isNotEmpty();

    }


}