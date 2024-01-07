package com.example.bank.controller;

import com.example.bank.model.dto.UserDto;
import com.example.bank.model.request.PaymentRequest;
import com.example.bank.model.response.PaymentResponse;
import com.example.bank.service.BankService;
import com.example.bank.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/bank")
public class BankController {
    private final BankService bankService;
    private final UserService userService;

    @PostMapping("/register/allegro")
    public ResponseEntity<Void> addUserFromAllegro(@RequestBody UserDto userDto) {
        userService.addUserFromAllegro(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/pay")
    public ResponseEntity<PaymentResponse> pay(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = bankService.pay(paymentRequest);
        return ResponseEntity.ok(paymentResponse);
    }

}
