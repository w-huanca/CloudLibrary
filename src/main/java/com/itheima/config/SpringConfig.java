package com.itheima.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @description:
 * @author: lenovo
 * @time: 2024-03-06 13:12
 */
@Configuration
@ComponentScan("com.itheima.service")
@Import({jdbcConfig.class, MyBatisConfig.class})
@EnableTransactionManagement //事务管理器
public class SpringConfig {

    @Bean
    public DataSourceTransactionManager transactionManger(@Autowired DruidDataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

}
