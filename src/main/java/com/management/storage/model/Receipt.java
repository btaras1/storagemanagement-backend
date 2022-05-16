package com.management.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
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
@Table(name = "receipt")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date sold;
    @Temporal(TemporalType.DATE)
    @Nullable
    private Date mountedDate;
    @Nullable
    private Boolean mounted;
    @Nullable
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date created;
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date modified;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="buyer_id", nullable=true)
    private Buyer buyer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_receipt",
            joinColumns = @JoinColumn(name = "receipt_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees;

    @OneToMany(mappedBy = "receipt")
    private Set<ItemReceipt> itemReceipts = new HashSet<>();
}
