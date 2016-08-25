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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 数据源配置2</><br/>
 * Date: 2016/7/14<br/>
 *
 * @author ZRS
 * @since JDK7
 */

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages ="com.zk798.user.dao.common", sqlSessionFactoryRef = "secondarySqlSessionFactory")
@Slf4j
public class SecondaryDataSourceConfig {

    @Bean(name="secondaryDataSource")  
    @ConfigurationProperties( prefix="datasource.secondary")
    public DataSource secondaryDataSource() {
        log.info("-------------------- secondaryDataSource init (ComBeziCommon) ---------------------");
        DataSource build = DataSourceBuilder.create().build();
        return build;

    }   

    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(@Qualifier("secondaryDataSource") DataSource secondaryDataSource) {
    	log.info("-------------------- secondaryTransactionManager init (ComBeziCommon) ---------------------");  
    	return new DataSourceTransactionManager(secondaryDataSource);
    }

    @Bean(name = "secondarySqlSessionFactory")
    public SqlSessionFactory secondarySqlSessionFactory(@Qualifier("secondaryDataSource") DataSource secondaryDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(secondaryDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:sqlmap/common/*.xml"));
        log.info("-------------------- secondarySqlSessionFactory init (ComBeziCommon) ---------------------"); 
        return sessionFactory.getObject();
    }

    @Bean(name="secondarySqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("secondarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        log.info("-------------------- secondarySqlSessionTemplate init (ComBeziCommon) ---------------------");
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}