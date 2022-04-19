package com.management.storage.dto.request;

public class TransferRequest {
    private Long fromStorageId;
    private Long toStorageId;
    private Long itemId;
    private Integer quantity;

    public Long getFromStorageId() {
        return fromStorageId;
    }

    public void setFromStorageId(Long fromStorageId) {
        this.fromStorageId = fromStorageId;
    }

    public Long getToStorageId() {
        return toStorageId;
    }

    public void setToStorageId(Long toStorageId) {
        this.toStorageId = toStorageId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
