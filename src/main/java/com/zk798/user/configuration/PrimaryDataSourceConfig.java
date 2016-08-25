package com.zk798.user.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 数据源配置1</><br/>
 * Date: 2016/7/14<br/>
 *
 * @author ZRS
 * @since JDK7
 */
@Configuration
//为了支持注解事务，增加了@EnableTransactionManagement注解，并且反回了一个PlatformTransactionManagerBean
@EnableTransactionManagement
@MapperScan(basePackages ="com.zk798.user.dao.wfs",sqlSessionFactoryRef = "primarySqlSessionFactory")
@Slf4j
public class PrimaryDataSourceConfig {

    @Bean(name="primaryDataSource")  
    @Primary
    @ConfigurationProperties(prefix="datasource.primary")
    public DataSource primaryDataSource() {  
        log.info("-------------------- primaryDataSource init (ComBeziWfs)---------------------");  
        return DataSourceBuilder.create().build();  
    }  

    @Bean(name = "primaryTransactionManager")
    @Primary
    public PlatformTransactionManager primaryTransactionManager() {
    	log.info("-------------------- primaryTransactionManager init (ComBeziWfs)---------------------");
        return new DataSourceTransactionManager(primaryDataSource());
    }

    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource primaryDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(primaryDataSource);
        
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:sqlmap/wfs/**/*.xml"));

        log.info("-------------------- primarySqlSessionFactory init (ComBeziWfs)---------------------");        
        return sessionFactory.getObject();
    }

    @Bean(name="primarySqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        log.info("-------------------- primarySqlSessionTemplate init (ComBeziWfs)---------------------");
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}