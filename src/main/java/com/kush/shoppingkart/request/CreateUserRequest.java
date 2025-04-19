package com.kush.shoppingkart.request;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String firstName;

    private String LastName;

    private String email;

    private String password;

}
