package com.management.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "procurement")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(exclude = {"itemProcurements", "storage"})
public class Procurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String documentId;
    @CreationTimestamp
    private LocalDate created;
    @UpdateTimestamp
    private LocalDate modified;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "procurement")
    private List<ItemProcurement> itemProcurements;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "storage_id")
    private Storage storage;


}
