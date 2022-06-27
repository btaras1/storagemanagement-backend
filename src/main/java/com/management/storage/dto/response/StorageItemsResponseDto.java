package com.management.storage.dto.response;

import com.management.storage.model.Item;

public class StorageItemsResponseDto {
    public Item item;
    public Integer quantity;

    public StorageItemsResponseDto() {
    }

    public StorageItemsResponseDto(Item item, Integer quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
