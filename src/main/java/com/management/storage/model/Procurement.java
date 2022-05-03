package com.management.storage.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.management.storage.view.View;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "procurement")
public class Procurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date created;
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date modified;
    @OneToMany(mappedBy = "procurement")
    private Set<ItemProcurement> itemProcurements = new HashSet<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="storage_id")
    private Storage storage;
}
