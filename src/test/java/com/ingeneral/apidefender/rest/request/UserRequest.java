package com.ingeneral.apidefender.rest.request;

import com.ingeneral.apidefender.data.entities.User;
import lombok.Getter;

@Getter
public class UserRequest {

    private Integer id;
    private String firstName;
    private String lastName;
    private String age;

    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setAge(Integer.parseInt(this.age));
        return user;
    }
}
