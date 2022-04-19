package com.management.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Nullable
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="guide_id", nullable=true)
    private Item guide;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="motor_id", nullable=true)
    private Item motor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="door_id", nullable=true)
    private Item door;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<ProductReceipt> productReceipts = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="storage_id", nullable=true)
    @JsonIgnore
    private Storage storage;

    public Product() {
    }

    public Product(Long id, Color color, @Nullable Item guide, Item motor, Item door) {
        this.id = id;
        this.guide = guide;
        this.motor = motor;
        this.door = door;
    }

    public Long getId() {
        return id;
    }

    @Nullable
    public Item getGuide() {
        return guide;
    }

    public void setGuide(@Nullable Item guide) {
        this.guide = guide;
    }

    public Item getMotor() {
        return motor;
    }

    public void setMotor(Item motor) {
        this.motor = motor;
    }

    public Item getDoor() {
        return door;
    }

    public void setDoor(Item door) {
        this.door = door;
    }

    public Set<ProductReceipt> getProductReceipts() {
        return productReceipts;
    }

    public void setProductReceipts(Set<ProductReceipt> productReceipts) {
        this.productReceipts = productReceipts;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
