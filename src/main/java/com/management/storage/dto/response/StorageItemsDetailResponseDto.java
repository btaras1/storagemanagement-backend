package com.management.storage.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class StorageItemsDetailResponseDto {

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
