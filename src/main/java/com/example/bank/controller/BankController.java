package com.example.bank.controller;

import com.example.bank.model.request.PaymentRequest;
import com.example.bank.model.request.RegisterRequest;
import com.example.bank.model.response.StatusResponse;
import com.example.bank.service.BankService;
import com.example.bank.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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

    @Operation(
            summary = "Add user from Shop",
            description = "Registers a new user from Shop."
    )
    @PostMapping("/register/shop")
    public ResponseEntity<StatusResponse> addUserFromShop(@Valid @RequestBody RegisterRequest request) {
        StatusResponse response = userService.addUserFromShop(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Process payment",
            description = "Processes a payment."
    )
    @PostMapping("/pay")
    public ResponseEntity<StatusResponse> pay(@Valid @RequestBody PaymentRequest request) {
        StatusResponse statusResponse = bankService.pay(request);
        return ResponseEntity.ok(statusResponse);
    }

}
