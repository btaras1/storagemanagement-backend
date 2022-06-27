package com.management.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.management.storage.model.composite.ItemProcurementId;
import com.management.storage.model.composite.ItemStorageId;
import lombok.*;

import javax.annotation.security.DenyAll;
import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name= "item_procurement")
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ItemProcurement {
    @EmbeddedId
    @JsonIgnore
    private ItemProcurementId id;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @MapsId("procurementId")
    @JoinColumn(name = "procurement_id")
    @JsonIgnore
    private Procurement procurement;

    @Column(name = "quantity")
    private Integer quantity;

}
