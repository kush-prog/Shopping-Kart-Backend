package com.kush.shoppingkart.Service;

import com.kush.shoppingkart.dtos.UserDto;
import com.kush.shoppingkart.model.User;
import com.kush.shoppingkart.request.CreateUserRequest;
import com.kush.shoppingkart.request.UserUpdateResquest;

public interface UserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateResquest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertToDto(User user);
}
