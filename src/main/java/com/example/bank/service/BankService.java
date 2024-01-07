package com.example.bank.service;

import com.example.bank.model.entity.TransactionHistory;
import com.example.bank.model.request.PaymentRequest;
import com.example.bank.model.request.ValidateRequest;
import com.example.bank.model.response.PaymentResponse;
import com.example.bank.model.response.ValidateResponse;
import com.example.bank.exception.exceptions.UserNotExist;
import com.example.bank.exception.exceptions.ValidationRequestException;
import com.example.bank.model.entity.User;
import com.example.bank.repository.TransactionHistoryRepository;
import com.example.bank.repository.UserRepository;
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

    public static final String SUCCESS_CODE = "1";
    public static final String INVALID_TOKEN_CODE = "2";
    public static final String INSUFFICIENT_BALANCE_CODE = "3";

    @Transactional
    public PaymentResponse pay(PaymentRequest request) {
        String email = request.getMail();
        String token = request.getToken();
        double amount = request.getAmount();
        String tokenStatus = checkToken(email, token);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotExist(email));
        if (SUCCESS_CODE.equals(tokenStatus)) {
            double balance = user.getBalance();
            if (amount <= balance) {
                user.setBalance(balance - amount);
                userRepository.save(user);
                transactionHistoryService.saveTransactionHistory(user, amount, SUCCESS_CODE);
                return new PaymentResponse(SUCCESS_CODE);
            } else {
                transactionHistoryService.saveTransactionHistory(user, amount, INSUFFICIENT_BALANCE_CODE);
                return new PaymentResponse(INSUFFICIENT_BALANCE_CODE);
            }
        } else {
            transactionHistoryService.saveTransactionHistory(user, amount, INVALID_TOKEN_CODE);
            return new PaymentResponse(INVALID_TOKEN_CODE);
        }
    }

    public String checkToken(String mail, String token) {
        String validUrl = tokenBaseUrl + tokenValidUrl;
        ValidateRequest validateRequest = new ValidateRequest(mail, token);

        ResponseEntity<ValidateResponse> validateResponse = restTemplate.postForEntity(validUrl, validateRequest, ValidateResponse.class);
        return Optional.ofNullable(validateResponse.getBody())
                .map(ValidateResponse::getStatusCode)
                .orElseThrow(ValidationRequestException::new);
    }
}
