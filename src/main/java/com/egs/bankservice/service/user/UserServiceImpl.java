package com.egs.bankservice.service.user;

import com.egs.bankservice.entity.user.User;
import com.egs.bankservice.exception.BankServiceException;
import com.egs.bankservice.model.user.CreateUserRequest;
import com.egs.bankservice.repository.UserRepository;
import com.egs.bankservice.shared.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDto createUser(CreateUserRequest request) throws BankServiceException {
        if (userRepository.existsByPersonalId(request.getPersonalId())) {
            String errorMessage = "User already exists with personalId = " + request.getPersonalId();
            log.error(errorMessage);
            throw new BankServiceException(errorMessage);
        }
        User user = new User();
        BeanUtils.copyProperties(request,user);
        User storedUser = userRepository.save(user);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(storedUser, userDto);
        return userDto;
    }

    @Override
    @Transactional
    public UserDto getUserById(long id) throws BankServiceException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            String message = "Cannot find user by id: " + id;
            log.warn(message);
            throw new BankServiceException(message);
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user.get(), userDto);
        return userDto;
    }

    @Override
    @Transactional
    public UserDto getUserByPersonalId(String personalId) throws BankServiceException {
        User user = userRepository.getUserByPersonalId(personalId);
        if (user == null){
            String message = "Cannot find user by personalId: " + personalId;
            log.warn(message);
            throw new BankServiceException(message);
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    @Override
    @Transactional
    public void deleteUser(long id) throws BankServiceException {
        if (!userRepository.existsById(id)) {
            String errorMessage= "There is no user with id: " + id;
            log.warn(errorMessage);
            throw new BankServiceException("There is no user with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
