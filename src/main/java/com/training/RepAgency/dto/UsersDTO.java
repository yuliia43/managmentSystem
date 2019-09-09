package com.training.RepAgency.dto;

import com.training.RepAgency.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UsersDTO {
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }
}
