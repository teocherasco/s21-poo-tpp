package org.example.domain;

public interface Validatable {
    void validate() throws org.example.exceptions.ValidationException;
}

