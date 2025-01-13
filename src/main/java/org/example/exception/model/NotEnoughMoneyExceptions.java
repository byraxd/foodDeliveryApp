package org.example.exception.model;

public class NotEnoughMoneyExceptions extends RuntimeException {
    public NotEnoughMoneyExceptions(String message) {
        super(message);
    }
}
