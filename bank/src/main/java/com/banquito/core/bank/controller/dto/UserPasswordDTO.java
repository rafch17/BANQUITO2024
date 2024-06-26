package com.banquito.core.bank.controller.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserPasswordDTO {

    private String userName;
    private String password;

}
