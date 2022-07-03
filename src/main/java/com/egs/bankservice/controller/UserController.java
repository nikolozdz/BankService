package com.egs.bankservice.controller;


import com.egs.bankservice.exception.BankServiceException;
import com.egs.bankservice.model.user.CreateUserRequest;
import com.egs.bankservice.model.user.UserResponse;
import com.egs.bankservice.service.user.UserService;
import com.egs.bankservice.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("create")
    public UserResponse addUser(@RequestBody CreateUserRequest createUserRequest) throws BankServiceException {
        UserDto userDto = userService.createUser(createUserRequest);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(userDto, userResponse);
        return userResponse;
    }

    @GetMapping("get/{id}")
    public UserResponse getUserById(@PathVariable(value = "id") long id) throws BankServiceException {
        UserDto userDto = userService.getUserById(id);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(userDto, userResponse);
        return userResponse;
    }

    @GetMapping("getByPersonId")
    public UserResponse getUserByPersonalId(@RequestParam(value = "personalId") String personalId) throws BankServiceException {
        UserDto userDto = userService.getUserByPersonalId(personalId);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(userDto, userResponse);
        return userResponse;
    }

    @DeleteMapping("delete/{id}")
    public void deleteUser(@PathVariable(value = "id") long id) throws BankServiceException {
        userService.deleteUser(id);
    }
}

