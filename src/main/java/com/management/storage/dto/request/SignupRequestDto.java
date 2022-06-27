package com.management.storage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupRequestDto {

    private String username;
    private String email;
    private Set<String> role = new HashSet<>();
    private String password;

    public void addRole(String role){
        this.role.add(role);
    }
}
