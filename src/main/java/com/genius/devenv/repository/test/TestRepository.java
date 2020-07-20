package com.genius.devenv.repository.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<TestDao, String>
{
    /*
    List<TestDao> findAll();

    TestDao save(TestDao testDao);

    List<TestDao> saveAll(List<TestDao> testDaoList);

     */

    TestDao getById(String id);

    Optional<TestDao> findById(String id);

    List<TestDao> getByAge(Long age);

    List<TestDao> findByAge(Long age);
}
