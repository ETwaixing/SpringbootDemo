package com.waixing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * SpringDataMongodbConfig配置包
 * Created by yonglang on 2017/3/15.
 */
@Component
public class SpringDataMongodbConfig {
    /* 数据库连接字符串*/
    @Value("${spring.data.mongodb.uri}")
    private String uri;

    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
}
