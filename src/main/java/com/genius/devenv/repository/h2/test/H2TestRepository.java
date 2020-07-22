package com.genius.devenv.repository.h2.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface H2TestRepository extends JpaRepository<H2TestDao, String> {
}
