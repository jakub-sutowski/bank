package com.example.bank.service;

import com.example.bank.model.dto.UserDto;
import com.example.bank.model.entity.User;
import com.example.bank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void addUserFromAllegro(UserDto userDto) {
        User user = User.builder()
                .email(userDto.getEmail())
                .build();
        userRepository.save(user);
    }
}
