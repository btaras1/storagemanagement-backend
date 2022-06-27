package com.management.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "receipt")
@EqualsAndHashCode(exclude = {"itemReceipts", "employees", "buyer"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String documentId;
    @NotNull
    private LocalDate sold;
    @Nullable
    private LocalDate mountedDate;
    @Nullable
    private Boolean mounted = false;
    @Nullable
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    private LocalDate created;
    @UpdateTimestamp
    private LocalDate modified;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="buyer_id", nullable=true)
    @NotNull
    private Buyer buyer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_receipt",
            joinColumns = @JoinColumn(name = "receipt_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<ItemReceipt> itemReceipts = new HashSet<>();
}
