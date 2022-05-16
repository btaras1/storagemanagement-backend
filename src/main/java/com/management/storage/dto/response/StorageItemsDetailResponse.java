package com.management.storage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StorageItemsDetailResponse {

    private Long id;
    private String value;
    private String description;
    private String type;
    private String color;
    private Boolean guideneeded;
    private Integer quantity;
    private Integer notMountedQuantity;
    private Integer avaliableQuantity;
}
