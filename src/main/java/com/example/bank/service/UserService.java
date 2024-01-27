package com.example.bank.service;

import com.example.bank.exception.exceptions.UserAlreadyExist;
import com.example.bank.model.entity.User;
import com.example.bank.model.request.RegisterRequest;
import com.example.bank.model.response.StatusResponse;
import com.example.bank.repository.UserRepository;
import com.example.bank.type.StatusCode;
import com.example.bank.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidation userValidation;

    @Transactional
    public StatusResponse addUserFromShop(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        try {
            userValidation.register(email);
            User user = User.builder()
                    .email(email)
                    .balance(10000)
                    .build();
            userRepository.save(user);
            return new StatusResponse(StatusCode.USER_CREATED);
        } catch (UserAlreadyExist e) {
            return new StatusResponse(StatusCode.USER_ALREADY_EXIST);
        }
    }
}
