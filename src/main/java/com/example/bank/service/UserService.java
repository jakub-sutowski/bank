package com.example.bank.service;

import com.example.bank.model.request.RegisterRequest;
import com.example.bank.model.entity.User;
import com.example.bank.model.response.RegisterResponse;
import com.example.bank.repository.UserRepository;
import com.example.bank.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidation userValidation;

    public static final String USER_CREATED_CODE = "7";
    public static final String USER_ALREADY_EXIST_CODE = "8";

    @Transactional
    public RegisterResponse addUserFromAllegro(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        try {
            userValidation.register(email);
            User user = User.builder()
                    .email(email)
                    .balance(10000)
                    .build();
            userRepository.save(user);
            return new RegisterResponse(USER_CREATED_CODE);
        } catch (Exception e) {
            return new RegisterResponse(USER_ALREADY_EXIST_CODE);
        }
    }
}
