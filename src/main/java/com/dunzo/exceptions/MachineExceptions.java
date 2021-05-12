package com.dunzo.exceptions;

public class MachineExceptions extends Exception {

    private int code;
    private String message;

    public MachineExceptions(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
