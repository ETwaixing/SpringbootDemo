package com.waixing.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**用户实体类 用注解进行标记
 * Created by yonglang on 2017/3/15.
 */
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String name;
    private String age;
    public User() {
    }
    public User(String id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getAge() {
        return age;
    }
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
