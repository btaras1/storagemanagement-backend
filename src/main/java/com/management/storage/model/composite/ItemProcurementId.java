package com.management.storage.model.composite;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ItemProcurementId implements Serializable {
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "procurement_id")
    private Long procurementId;



    public ItemProcurementId() {
    }

    public ItemProcurementId(Long itemId, Long procurementId) {
        this.itemId = itemId;
        this.procurementId = procurementId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemProcurementId itemProcurementId = (ItemProcurementId) o;
        return itemId.equals(itemProcurementId.itemId) &&
                procurementId.equals(itemProcurementId.procurementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, procurementId);
    }
}
