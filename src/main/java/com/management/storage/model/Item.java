package com.management.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "item")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(exclude = {"itemStorages", "itemProcurements", "itemReceipts"})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    @NotNull(message = "Value cannot be null")
    private String value;
    @Nullable
    @Column(columnDefinition = "TEXT")
    private String description;
    @Nullable
    private Boolean guide_needed;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="itemtype_id", nullable=true)
    @NotNull(message = "Type cannot be null")
    private ItemType itemType;

    @Nullable
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="color_id", nullable=true)
    private Color color;
    @CreationTimestamp
    private LocalDate created;
    @UpdateTimestamp
    private LocalDate modified;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "item", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ItemStorage> itemStorages = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "item", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ItemProcurement> itemProcurements = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "item")
    @JsonIgnore
    private Set<ItemReceipt> itemReceipts = new HashSet<>();



}
