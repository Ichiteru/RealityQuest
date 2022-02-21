package com.chern.validation;

public interface Validator<T> {

    void validate(T object);
}
