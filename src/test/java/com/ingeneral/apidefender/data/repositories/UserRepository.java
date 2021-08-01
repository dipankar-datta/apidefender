package com.ingeneral.apidefender.data.repositories;

import com.ingeneral.apidefender.data.entities.User;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Repository
public class UserRepository {

    List<User> users;

    @PostConstruct
    public void init() {
        users = new ArrayList<>();

        var u1 = new User();
        u1.setId(1);
        u1.setFirstName("Bruce");
        u1.setLastName("Wayne");
        u1.setAge(34);

        users.add(u1);

        var u2 = new User();
        u2.setId(2);
        u2.setFirstName("Clark");
        u2.setLastName("Kent");
        u2.setAge(35);
        users.add(u2);

        var u3 = new User();
        u3.setId(3);
        u3.setFirstName("Peter");
        u3.setLastName("Parker");
        u3.setAge(25);
        users.add(u3);

    }

    public List<User> getUsers() {
        return this.users;
    }
}
