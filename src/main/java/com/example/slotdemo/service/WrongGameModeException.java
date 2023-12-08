package com.example.slotdemo.service;

public class WrongGameModeException extends Exception {
    public WrongGameModeException(String message) {
        super(message);
    }
}
