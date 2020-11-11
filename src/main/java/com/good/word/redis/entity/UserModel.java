package com.good.word.redis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.good.word.redis.valid.RepeatedKeyDefinition;
import com.good.word.redis.valid.UnionKey;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wangxianlei
 * @since 1.0
 */
@TableName("user")
public class UserModel implements Serializable, RepeatedKeyDefinition {

    private String id;

    @UnionKey
    @NotEmpty(message = "姓名不能为空")
    private String name;
    @UnionKey
    @Max(value=100,message="非法年龄")
    private int age;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    public UserModel() {
    }

    public UserModel(String id, String name, int age, Date birth) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birth = birth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "UserModel{" +
          "id='" + id + '\'' +
          ", name='" + name + '\'' +
          ", age=" + age +
          ", birth=" + birth +
          '}';
    }

    @Override
    public String uniqKey() {
        return this.getName() + '*' + this.getAge();
    }
}
