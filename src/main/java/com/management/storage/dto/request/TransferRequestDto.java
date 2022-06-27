package com.management.storage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferRequestDto {
    private Long fromStorageId;
    private Long toStorageId;
    private Long itemId;
    private Integer quantity;
}
