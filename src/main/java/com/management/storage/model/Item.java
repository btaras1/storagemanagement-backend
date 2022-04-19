package com.management.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;

    @Nullable
    private Boolean guide_needed;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="itemtype_id", nullable=true)
    private ItemType itemType;

    @Nullable
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="color_id", nullable=true)
    private Color color;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<ItemStorage> itemStorages = new HashSet<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<ItemStorageArchive> itemStorageArchives = new HashSet<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<ItemProcurement> itemProcurements = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "door", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Product> doors = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "motor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Product> motors = new HashSet<>();
    @JsonIgnore
    @Nullable
    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Product> guides = new HashSet<>();


    public Item(String value, @Nullable Boolean guide_needed, ItemType itemType, @Nullable Color color, Set<ItemStorage> itemStorages, Set<ItemStorageArchive> itemStorageArchives, Set<ItemProcurement> itemProcurements, Set<Product> doors, Set<Product> motors, @Nullable Set<Product> guides) {
        this.value = value;
        this.guide_needed = guide_needed;
        this.itemType = itemType;
        this.color = color;
        this.itemStorages = itemStorages;
        this.itemStorageArchives = itemStorageArchives;
        this.itemProcurements = itemProcurements;
        this.doors = doors;
        this.motors = motors;
        this.guides = guides;
    }

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Nullable
    public Boolean getGuide_needed() {
        return guide_needed;
    }

    public void setGuide_needed(@Nullable Boolean guide_needed) {
        this.guide_needed = guide_needed;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    @Nullable
    public Color getColor() {
        return color;
    }

    public void setColor(@Nullable Color color) {
        this.color = color;
    }

    public Set<ItemStorage> getItemStorages() {
        return itemStorages;
    }

    public void setItemStorages(Set<ItemStorage> itemStorages) {
        this.itemStorages = itemStorages;
    }

    public Set<ItemStorageArchive> getItemStorageArchives() {
        return itemStorageArchives;
    }

    public void setItemStorageArchives(Set<ItemStorageArchive> itemStorageArchives) {
        this.itemStorageArchives = itemStorageArchives;
    }

    public Set<ItemProcurement> getItemProcurements() {
        return itemProcurements;
    }

    public void setItemProcurements(Set<ItemProcurement> itemProcurements) {
        this.itemProcurements = itemProcurements;
    }

    public Set<Product> getDoors() {
        return doors;
    }

    public void setDoors(Set<Product> doors) {
        this.doors = doors;
    }

    public Set<Product> getMotors() {
        return motors;
    }

    public void setMotors(Set<Product> motors) {
        this.motors = motors;
    }

    @Nullable
    public Set<Product> getGuides() {
        return guides;
    }

    public void setGuides(@Nullable Set<Product> guides) {
        this.guides = guides;
    }
}
