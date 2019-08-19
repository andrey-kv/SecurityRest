package com.learnspring.SecurityRest.controllers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthBody {

    private String email;
    private String password;

}
