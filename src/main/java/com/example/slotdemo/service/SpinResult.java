package com.example.slotdemo.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpinResult {
    private final int value;

    private final Screen screen;

    public Screen getScreen() {
        return screen;
    }
}
