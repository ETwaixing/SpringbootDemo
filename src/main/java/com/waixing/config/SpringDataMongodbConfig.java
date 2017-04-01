package com.waixing.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.stereotype.Component;

/**
 * SpringDataMongodbConfig配置包
 * Created by yonglang on 2017/3/15.
 */
@Component
public class SpringDataMongodbConfig extends AbstractMongoConfiguration{
    /* 数据库连接字符串*/
    @Value("${spring.data.mongodb.uri}")
    private String uri;
    @Value("${spring.data.mongodb.dbname}")
    private String dbname;
    @Value("${spring.data.mongodb.host}")
    private String host;
    @Value("${spring.data.mongodb.port}")
    private int port;

    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    protected String getDatabaseName() {
        return dbname;
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(host,port);
    }

    /**
     *   重写方法 去除_class字段
     */
    @Override
    public MappingMongoConverter mappingMongoConverter() throws Exception {
        MappingMongoConverter mmc = super.mappingMongoConverter();
        mmc.setTypeMapper(new DefaultMongoTypeMapper(null));
        return mmc;
    }
}
