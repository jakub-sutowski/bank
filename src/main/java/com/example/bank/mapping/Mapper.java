package com.example.bank.mapping;

public interface Mapper<F, T> {
    T convert(F from);

    F reverse(T from);
}