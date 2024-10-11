package com.hytejasvi.journalApp.Dto;

import com.mongodb.lang.NonNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class UserDto {
    private String userName;
    private String password;
    private List<String> roles;
}
