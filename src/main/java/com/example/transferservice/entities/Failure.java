package com.example.transferservice.entities;

public class Failure {
    private final String type;
    private final String message;

    public Failure(String type, String message) {

        this.type = type;
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }
}
