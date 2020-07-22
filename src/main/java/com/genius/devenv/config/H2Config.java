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
@MapperScan(basePackages = "com.genius.devenv.repository.h2", sqlSessionFactoryRef = "h2SqlSessionFactory")
@EnableJpaRepositories(basePackages = "com.genius.devenv.repository.h2" ,
        entityManagerFactoryRef = "h2EntityManagerFactory" ,
        transactionManagerRef = "h2TransactionManager"
)
public class H2Config {

    @Bean
    @ConfigurationProperties(prefix = "datasource.h2")
    public DataSourceProperties h2DataSourceProperties () {
        return new DataSourceProperties();
    }

    @Bean(name = "h2DataSource")
    //@Primary
    public DataSource h2DataSource() {
        if(h2DataSourceProperties().getType() == HikariDataSource.class){
            return new HikariDataSource(new HikariConfig(getH2ConfigProperties()));
        }else{
            return h2DataSourceProperties().initializeDataSourceBuilder().build();
        }
    }

    private Properties getH2ConfigProperties() {
        Properties prop = new Properties();
        prop.put("jdbcUrl", h2DataSourceProperties().getUrl());
        prop.put("driverClassName",h2DataSourceProperties().getDriverClassName());
        prop.put("username", h2DataSourceProperties().getUsername());
        prop.put("password", h2DataSourceProperties().getPassword());

        return prop;
    }

    @Bean(name = "h2SqlSessionFactory")
    public SqlSessionFactory h2SqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(h2DataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/h2/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean h2EntityManagerFactory(EntityManagerFactoryBuilder builder){
        return builder.dataSource(h2DataSource())
                .packages("com.genius.devenv.repository.h2")
                .persistenceUnit("h2")
                .build();
    }

    @Bean
    public PlatformTransactionManager h2TransactionManager(@Qualifier("h2EntityManagerFactory") EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
}
