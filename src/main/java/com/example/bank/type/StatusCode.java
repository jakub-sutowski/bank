package com.example.bank.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode {
    SUCCESS,
    CORRECT_TOKEN,
    INVALID_TOKEN,
    INSUFFICIENT_BALANCE,
    USER_CREATED,
    USER_ALREADY_EXIST
}
