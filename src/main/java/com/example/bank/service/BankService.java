package com.example.bank.service;

import com.example.bank.exception.exceptions.UserNotExist;
import com.example.bank.exception.exceptions.ValidationRequestException;
import com.example.bank.model.entity.User;
import com.example.bank.model.request.PaymentRequest;
import com.example.bank.model.request.ValidateRequest;
import com.example.bank.model.response.StatusResponse;
import com.example.bank.repository.UserRepository;
import com.example.bank.type.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final TransactionHistoryService transactionHistoryService;

    @Value("${token.app.base_url}")
    private String tokenBaseUrl;

    @Value("${token.app.valid_token_url}")
    private String tokenValidUrl;

    @Transactional
    public StatusResponse pay(PaymentRequest request) {
        String email = request.getMail();
        String token = request.getToken();
        double amount = request.getAmount();

        StatusCode tokenStatus = checkToken(email, token);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotExist(email));

        if (StatusCode.CORRECT_TOKEN.equals(tokenStatus)) {
            double balance = user.getBalance();

            if (amount <= balance) {
                user.setBalance(balance - amount);
                userRepository.save(user);
                transactionHistoryService.saveTransactionHistory(user, amount, StatusCode.SUCCESS);
                return new StatusResponse(StatusCode.SUCCESS);
            } else {
                transactionHistoryService.saveTransactionHistory(user, amount, StatusCode.INSUFFICIENT_BALANCE);
                return new StatusResponse(StatusCode.INSUFFICIENT_BALANCE);
            }
        } else {
            transactionHistoryService.saveTransactionHistory(user, amount, StatusCode.INSUFFICIENT_BALANCE);
            return new StatusResponse(StatusCode.INSUFFICIENT_BALANCE);
        }
    }

    public StatusCode checkToken(String email, String token) {
        String validUrl = tokenBaseUrl + tokenValidUrl;
        ValidateRequest validateRequest = new ValidateRequest(email, token);

        ResponseEntity<StatusResponse> response = restTemplate.postForEntity(validUrl, validateRequest, StatusResponse.class);

        return Optional.ofNullable(response.getBody())
                .map(StatusResponse::getStatusCode)
                .orElseThrow(() -> new ValidationRequestException(email));
    }
}