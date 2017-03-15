package com.waixing.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * SpringDataMongodbConfig配置包
 * Created by yonglang on 2017/3/15.
 */
@Component
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class SpringDataMongodbConfig {
    /* 数据库连接字符串*/
    private String uri;
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
}
