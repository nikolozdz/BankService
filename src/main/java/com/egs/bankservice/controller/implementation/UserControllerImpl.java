package com.egs.bankservice.controller.implementation;


import com.egs.bankservice.controller.UserController;
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
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("create")
    @Override
    public UserResponse addUser(@RequestBody CreateUserRequest createUserRequest) throws BankServiceException {
        UserDto userDto = userService.createUser(createUserRequest);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(userDto, userResponse);
        return userResponse;
    }

    @GetMapping("get/{id}")
    @Override
    public UserResponse getUserById(@PathVariable(value = "id") long id) throws BankServiceException {
        UserDto userDto = userService.getUserById(id);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(userDto, userResponse);
        return userResponse;
    }

    @GetMapping("getByPersonId")
    @Override
    public UserResponse getUserByPersonalId(@RequestParam(value = "personalId") String personalId) throws BankServiceException {
        UserDto userDto = userService.getUserByPersonalId(personalId);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(userDto, userResponse);
        return userResponse;
    }

    @DeleteMapping("delete/{id}")
    @Override
    public void deleteUser(@PathVariable(value = "id") long id) throws BankServiceException {
        userService.deleteUser(id);
    }
}

