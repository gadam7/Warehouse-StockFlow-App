package com.adamidis.learning.warehousestockflow.Enum;

public enum EventType {
    LOGIN_ATTEMPT("You tried to log in to your account"),
    LOGIN_ATTEMPT_FAILURE("A failed login attempt was made on your account"),
    LOGIN_ATTEMPT_SUCCESS("A successful login attempt was made on your account"),
    PROFILE_UPDATE("Your profile information was updated"),
    PROFILE_PICTURE_UPDATE("Your profile picture was updated"),
    ROLE_UPDATE("Your user role was updated"),
    ACCOUNT_SETTINGS_UPDATE("Your account settings were updated"),
    PASSWORD_UPDATE("Your password was updated"),
    MFA_UPDATE("Your multi-factor authentication settings were updated"),
    ACCOUNT_LOCKED("Your account was locked due to multiple failed login attempts"),
    ACCOUNT_UNLOCKED("Your account was unlocked"),
    USER_CREATED("A new user account was created"),
    USER_DELETED("Your user account was deleted");

    private final String description;

    EventType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
