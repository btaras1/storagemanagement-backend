package com.management.storage.exception;

public enum ApiError {

    BUYER_NOT_FOUND("NOT FOUND", "Buyer not found", 404),
    COLOR_NOT_FOUND("NOT FOUND", "Color not found", 404),
    EMPLOYEE_NOT_FOUND("NOT FOUND", "Employee not found", 404),
    ITEM_NOT_FOUND("NOT FOUND", "Item not found", 404),
    ITEM_TYPE_NOT_FOUND("NOT FOUND", "Item type not found", 404),
    PROCUREMENT_NOT_FOUND("NOT FOUND", "Procurement not found", 404),
    RECEIPT_NOT_FOUND("NOT FOUND", "Receipt not found", 404),
    ROLE_NOT_FOUND("NOT FOUND", "Role not found", 404),
    STORAGE_NOT_FOUND("NOT FOUND", "Storage not found", 404),
    USER_NOT_FOUND("NOT FOUND", "User not found", 404);

    public String name;
    public String message;
    public Integer code;


    ApiError(String name, String message, Integer code) {
        this.name = name;
        this.message = message;
        this.code = code;
    }


}
