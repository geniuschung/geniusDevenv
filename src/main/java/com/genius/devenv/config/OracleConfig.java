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
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Slf4j
@MapperScan(basePackages = "com.genius.devenv.repository.oracle" , sqlSessionFactoryRef = "oracleSqlSessionFactory")
@EnableJpaRepositories(basePackages = "com.genius.devenv.repository.oracle" ,
        entityManagerFactoryRef = "oracleEntityManagerFactory" ,
        transactionManagerRef = "oracleTransactionManager"
)
public class OracleConfig {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "datasource.oracle")
    public DataSourceProperties oracleDataSourceProperties () {
        return new DataSourceProperties();
    }

    @Bean(name="oracleDataSource")
    @Primary
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
    @Primary
    public SqlSessionFactory oracleSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(oracleDataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/oracle/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean oracleEntityManagerFactory(EntityManagerFactoryBuilder builder){
        return builder.dataSource(oracleDataSource())
                .packages("com.genius.devenv.repository.oracle")
                .persistenceUnit("oracle")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager oracleTransactionManager(@Qualifier("oracleEntityManagerFactory") EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
}
