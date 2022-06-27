package com.management.storage.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ItemsCountResponseDto {
    private String value;
    private Integer quantity;
}
