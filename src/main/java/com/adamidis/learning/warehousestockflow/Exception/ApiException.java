package com.adamidis.learning.warehousestockflow.Exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
