package com.genius.devenv.repository.h2.test;

import com.genius.devenv.repository.oracle.test.TestDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface H2TestMapper {
    List<TestDao> selectAll();
}
