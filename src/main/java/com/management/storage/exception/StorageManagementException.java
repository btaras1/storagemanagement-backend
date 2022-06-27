package com.management.storage.exception;

import java.util.function.Supplier;

public class StorageManagementException extends Exception{
    String name;
    String message;
    Integer code;


    public StorageManagementException(ApiError error) {
        super();
        this.name = error.name;
        this.message = error.message;
        this.code = error.code;
    }

}
