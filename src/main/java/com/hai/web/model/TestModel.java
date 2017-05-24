package com.hai.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * Created by cloud on 2017/2/23.
 */
public class TestModel extends BaseModel {

    private static final long serialVersionUID = 1901163335147921598L;

    private String name;
    private String age;
    private List<String> address;
    private int order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setAge(String age) {
        this.age = age;
    }
    @JsonIgnore
    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

}
