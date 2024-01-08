package com.example.bank.controller;

import com.example.bank.model.request.RegisterRequest;
import com.example.bank.model.request.PaymentRequest;
import com.example.bank.model.response.PaymentResponse;
import com.example.bank.model.response.RegisterResponse;
import com.example.bank.service.BankService;
import com.example.bank.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    public ResponseEntity<RegisterResponse> addUserFromAllegro(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = userService.addUserFromAllegro(registerRequest);
        return ResponseEntity.ok(registerResponse);
    }

    @PostMapping("/pay")
    public ResponseEntity<PaymentResponse> pay(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = bankService.pay(paymentRequest);
        return ResponseEntity.ok(paymentResponse);
    }

}
