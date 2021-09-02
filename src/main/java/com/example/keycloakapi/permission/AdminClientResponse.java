package com.example.keycloakapi.permission;

public final class AdminClientResponse {
    private final int status;
    private final String message;
    private final String path;

    public AdminClientResponse(int status, String reason) {
        this.status = status;
        this.message = reason;
        this.path = "";
    }

    public AdminClientResponse(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
