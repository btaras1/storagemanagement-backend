package com.management.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.management.storage.model.composite.ItemProcurementId;
import com.management.storage.model.composite.ItemStorageId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name= "item_procurement")
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

    public ItemProcurement() {
    }

    public ItemProcurement(Item item, Procurement procurement, Integer quantity) {
        this.item = item;
        this.procurement = procurement;
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
