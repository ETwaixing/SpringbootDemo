package com.waixing;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;
import com.waixing.config.SpringDataMongodbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;

import java.net.UnknownHostException;

/**
 * Created by yonglang on 2017/3/15.
 */
/*@Configuration 表示该类是bean配置的信息源
* @ComponentScan 告知spring来扫描指定包初始化bean
* @EnableAutoConfiguration  告知springboot来配置框架
* 等价于@SpringBootApplication
*/
@SpringBootApplication
public class Application implements CommandLineRunner{
    @Autowired
    private SpringDataMongodbConfig springDataMongodbConfig;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoClient mongoClient;
    @Autowired
    private MongoClientURI mongoClientURI;

    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
    //测试mongo对象注入问题
    @Override
    public void run(String... strings) throws Exception {
        System.out.println(mongoTemplate!=null);
        System.out.println(mongoClient!=null);
        System.out.println(mongoClientURI!=null);
    }

    //404 500错误页面访问设置
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (container -> {
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/resources/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/resources/500.html");
            container.addErrorPages(error404Page, error500Page);
        });
    }

    @Bean(name = "mongoClientURI")
    @Primary
    public MongoClientURI mongoClientURI() throws UnknownHostException {
        MongoClientOptions.Builder builder = MongoClientOptions.builder().connectionsPerHost(10).writeConcern(WriteConcern.ACKNOWLEDGED);
        return new MongoClientURI(springDataMongodbConfig.getUri(), builder);
    }

    @Bean(name = "mongoClient")
    @Primary
    public MongoClient mongoClient(@Autowired MongoClientURI clientURI) throws UnknownHostException {
        return new MongoClient(clientURI);
    }

}
