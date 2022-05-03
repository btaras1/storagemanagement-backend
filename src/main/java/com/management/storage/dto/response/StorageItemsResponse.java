package com.management.storage.dto.response;

import com.management.storage.model.Item;

import java.util.Objects;

public class StorageItemsResponse {
    public Item item;
    public Integer quantity;

    public StorageItemsResponse() {
    }

    public StorageItemsResponse(Item item, Integer quantity) {
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
