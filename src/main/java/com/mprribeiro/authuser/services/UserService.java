package com.mprribeiro.authuser.services;

import com.mprribeiro.authuser.dtos.UserDTO;
import com.mprribeiro.authuser.enums.UserStatus;
import com.mprribeiro.authuser.enums.UserType;
import com.mprribeiro.authuser.models.UserModel;
import com.mprribeiro.authuser.patterns.notification.Notification;
import com.mprribeiro.authuser.repositories.UserRepository;
import com.mprribeiro.authuser.services.exceptions.ArgumentAlreadyTakenException;
import com.mprribeiro.authuser.services.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel getUserById(UUID id) {
        Optional<UserModel> userModel = userRepository.findById(id);
        return userModel.orElseThrow(() -> new UserNotFoundException("Id " + id + " does not exist!"));
    }

    public void deleteUserById(UUID id) {
        final var userModel = userRepository.findById(id);

        if (!userModel.isPresent()) {
            log.error("User {} not found! ", id);
            throw new UserNotFoundException("Id " + id + " does not exist!");
        }

        userRepository.deleteById(id);
    }

    private boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserModel createUser(UserDTO userDTO) {
        var notification = validate(userDTO.getUsername(), userDTO.getEmail());

        if (notification.hasError()) {
            throw new ArgumentAlreadyTakenException(notification.toString());
        }

        var userModel = new UserModel();
        BeanUtils.copyProperties(userDTO, userModel);
        userModel.setStatus(UserStatus.ACTIVE);
        userModel.setType(UserType.STUDENT);

        return userRepository.save(userModel);
    }

    private Notification validate(String username, String email) {
        var notification = new Notification();

        if(existsByUsername(username)){
            log.warn("Username {} is already taken ", username);
            notification.addError("Username " + username + " is not available!");
        }

        if(existsByEmail(email)){
            log.warn("Email {} is already taken ", email);
            notification.addError("Email " + email + " not available!");
        }

        return notification;
    }
}
