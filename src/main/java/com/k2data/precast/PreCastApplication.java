package com.k2data.precast;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author tianhao
 * @Description Project Start
 * @date 2018/12/5 10:17:53
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ServletComponentScan
@MapperScan("com.k2data.precast.mapper")
public class PreCastApplication {

    public static void main(String[] args) {
        SpringApplication.run(PreCastApplication.class, args);
    }
}
