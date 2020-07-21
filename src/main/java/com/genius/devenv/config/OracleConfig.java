package com.genius.devenv.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Slf4j
@MapperScan(basePackages = "com.genius.devenv.repository.oracle" , sqlSessionFactoryRef = "oracleSqlSessionFactory")
public class OracleConfig {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "datasource.oracle")
    public DataSourceProperties oracleDataSourceProperties () {
        return new DataSourceProperties();
    }

    @Bean(name="oracleDataSource")
    public DataSource oracleDataSource() {
        if(oracleDataSourceProperties().getType() == HikariDataSource.class){
            return new HikariDataSource(new HikariConfig(getOracleConfigProperties()));
        }else{
            return oracleDataSourceProperties().initializeDataSourceBuilder().build();
        }


    }

    private Properties getOracleConfigProperties() {
        Properties prop = new Properties();
        prop.put("jdbcUrl", oracleDataSourceProperties().getUrl());
        prop.put("driverClassName",oracleDataSourceProperties().getDriverClassName());
        prop.put("username", oracleDataSourceProperties().getUsername());
        prop.put("password", oracleDataSourceProperties().getPassword());
        return prop;
    }

    @Bean(name = "oracleSqlSessionFactory")
    public SqlSessionFactory oracleSqlSessionFactory(@Qualifier("oracleDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/oracle/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
}
