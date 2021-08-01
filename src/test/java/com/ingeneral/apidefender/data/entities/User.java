package com.ingeneral.apidefender.data.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {

    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
}
