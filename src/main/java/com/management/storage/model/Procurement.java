package com.management.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Procurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date created;

    @OneToMany(mappedBy = "procurement")
    private Set<ItemProcurement> itemProcurements = new HashSet<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="storage_id", nullable=true)
    private Storage storage;


    public Procurement() {
    }

    public Procurement(Date created) {
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Set<ItemProcurement> getItemProcurements() {
        return itemProcurements;
    }

    public void setItemProcurements(Set<ItemProcurement> itemProcurements) {
        this.itemProcurements = itemProcurements;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
