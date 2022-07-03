package com.egs.bankservice.service.user;


import com.egs.bankservice.exception.BankServiceException;
import com.egs.bankservice.model.user.CreateUserRequest;
import com.egs.bankservice.model.user.UserResponse;
import com.egs.bankservice.shared.dto.UserDto;

public interface UserService {

    UserDto createUser(CreateUserRequest request) throws BankServiceException;

    UserDto getUserById(long id) throws BankServiceException;

    UserDto getUserByPersonalId(String personalId) throws BankServiceException;

    void deleteUser(long id) throws BankServiceException;
}
