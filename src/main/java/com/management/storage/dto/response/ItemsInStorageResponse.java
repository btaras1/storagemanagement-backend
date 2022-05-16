package com.management.storage.dto.response;

import lombok.Getter;

import javax.persistence.Lob;


public interface ItemsInStorageResponse {

    Long getId();
    String getValue();
    String getDescription();
    String getType();
    String getColor();
    Boolean getGuideneeded();
    Integer getQuantity();


}
