package com.management.storage.dto.response;

public interface FullDetailItemsInStorageDto {
    Long getStorageid();
    Long getReceiptid();
    Long getItemid();

    String getValue();
    String getType();
    String getColor();
    String getDescription();
    Boolean getGuideneeded();

    Integer getQuantity();
    Integer getMountedquantity();
}
