package com.management.storage.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.management.storage.model.ItemStorage;
import com.management.storage.model.Procurement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class StorageItemResponse {

    private Long id;

    private String name;

    private String location;

    @Temporal(TemporalType.DATE)
    private Date created;

    @Temporal(TemporalType.DATE)
    private Date modified;

    private Set<ItemStorageResponse> itemStorages = new HashSet<>();

}
