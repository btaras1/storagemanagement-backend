package com.management.storage.dto.response;

import com.management.storage.model.Item;
import com.management.storage.model.Storage;
import com.management.storage.model.composite.ItemStorageId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemStorageResponseDto {

    private ItemStorageId id;

    private Item item;

    private String value;

    private String description;

    private String color;

    private Boolean guideneeded;

    private Storage storage;

    private Integer quantity;

    private Integer notMountedQuantity;

    private Integer avaliableQuantity;


}
