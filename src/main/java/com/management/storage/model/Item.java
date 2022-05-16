package com.management.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "item")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String value;
    @Nullable
    @Column(columnDefinition = "TEXT")
    private String description;
    @Nullable
    private Boolean guide_needed;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="itemtype_id", nullable=true)
    private ItemType itemType;

    @Nullable
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="color_id", nullable=true)
    private Color color;
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date created;
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date modified;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ItemStorage> itemStorages = new HashSet<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ItemProcurement> itemProcurements = new HashSet<>();

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private Set<ItemReceipt> itemReceipts = new HashSet<>();
}
