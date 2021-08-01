package com.ingeneral.apidefender.rest.response;

import com.ingeneral.apidefender.data.entities.User;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {

    private Integer id;
    private String firstName;
    private String lastName;

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

}
