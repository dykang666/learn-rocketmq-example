package com.example.rockermqproviderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/6/20 19:29
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class User {
    private  String name;
    private  int age;
}
