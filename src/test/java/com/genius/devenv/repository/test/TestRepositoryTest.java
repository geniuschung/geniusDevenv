package com.genius.devenv.repository.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;

import java.util.ArrayList;
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

    @Test
    public void JPA_데이터_insert(){
        TestDao insertTestDao = TestDao.builder().id("yoyo").name("요요").age(10L).build();
        testRepository.save(insertTestDao);
        Optional<TestDao> selectTestDao = testRepository.findById("yoyo");
        selectTestDao.ifPresent(v-> log.info("{},{},{}",v.getId(),v.getName(),v.getAge()));

        assertThat(selectTestDao.map(TestDao::getId).orElse(null)).isNotNull();
        assertThat(selectTestDao.map(TestDao::getName).orElse(null)).isNotNull();
        assertThat(selectTestDao.map(TestDao::getAge).orElse(null)).isNotNull();

    }

    @Test
    public void JPA_데이터_update(){
        Optional<TestDao> selectTestDao = testRepository.findById("geniuschung");
        TestDao updateTestDao = selectTestDao.map(v -> v).orElseGet(TestDao::new);
        updateTestDao.setAge(20L);

        testRepository.save(updateTestDao);

        Optional<TestDao> selectTestDao2 = testRepository.findById("geniuschung");
        selectTestDao.ifPresent(v-> log.info("{},{},{}",v.getId(),v.getName(),v.getAge()));

        assertThat(selectTestDao2.map(TestDao::getAge).orElse(0L)).isEqualTo(20L);


    }

    @Test
    public void JPA_데이터_SaveAll(){
        TestDao insertTestDao1 = TestDao.builder().id("yoyo1").name("요요").age(10L).build();
        TestDao insertTestDao2 = TestDao.builder().id("yoyo2").name("요요").age(10L).build();
        TestDao insertTestDao3 = TestDao.builder().id("yoyo3").name("요요").age(10L).build();

        List insertList = new ArrayList<TestDao>();
        insertList.add(insertTestDao1);
        insertList.add(insertTestDao2);
        insertList.add(insertTestDao3);

        List<TestDao> insertedList = testRepository.saveAll(insertList);

        insertedList.stream().forEach(v -> log.info("{},{},{}",v.getId(),v.getName(),v.getAge()));


        List<TestDao> allList = testRepository.findAll();
        allList.stream().forEach(v -> log.info("{},{},{}",v.getId(),v.getName(),v.getAge()));
        assertThat(allList).isNotEmpty();
        assertThat(allList.size()).isEqualTo(8);

    }


}