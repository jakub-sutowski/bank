package com.example.bank.validation;

import com.example.bank.exception.exceptions.UserAlreadyExist;
import com.example.bank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidation {

    private final UserRepository userRepository;

    public void register(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExist(email);
        }
    }
}
