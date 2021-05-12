package com.dunzo.exceptions;


public class InventoryException extends Exception {
    private int code;
    private String message;

    public InventoryException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
