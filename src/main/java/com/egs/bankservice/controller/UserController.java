package com.egs.bankservice.controller;

import com.egs.bankservice.exception.BankServiceException;
import com.egs.bankservice.model.user.CreateUserRequest;
import com.egs.bankservice.model.user.UserResponse;

public interface UserController {

    UserResponse addUser(CreateUserRequest createUserRequest) throws BankServiceException;

    UserResponse getUserById(long id) throws BankServiceException;

    UserResponse getUserByPersonalId(String personalId) throws BankServiceException;

    void deleteUser(long id) throws BankServiceException;
}
