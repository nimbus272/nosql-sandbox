package com.example.nosqlsandbox.model;

import java.util.List;

import lombok.Data;

@Data
public class MongoRequest {

    private String username;
    private String email;
    private String phoneNumber;
    private List<String> posts;
    private int age;

}
