package com.management.storage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddRequest {

    private Long storageId;
    private Long itemId;
    private Integer quantity;
}
