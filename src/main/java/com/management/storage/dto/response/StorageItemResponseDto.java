package com.management.storage.dto.response;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class StorageItemResponseDto {

    private Long id;

    private String name;

    private String location;


    private LocalDate created;


    private LocalDate modified;
    @Builder.Default
    private Set<ItemStorageResponseDto> itemStorages = new HashSet<>();

}
