package com.management.storage.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date sold;

    @Nullable
    private Date mounted;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="buyer_id", nullable=true)
    private Buyer buyer;

    @Nullable
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="employee_id", nullable=true)
    private Employee employee;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductReceipt> productReceipts = new HashSet<>();


    public Receipt() {
    }

    public Receipt(Date sold, @Nullable Date mounted, Buyer buyer, @Nullable Employee employee) {
        this.sold = sold;
        this.mounted = mounted;
        this.buyer = buyer;
        this.employee = employee;

    }

    public Long getId() {
        return id;
    }

    public Date getSold() {
        return sold;
    }

    public void setSold(Date sold) {
        this.sold = sold;
    }

    @Nullable
    public Date getMounted() {
        return mounted;
    }

    public void setMounted(@Nullable Date mounted) {
        this.mounted = mounted;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    @Nullable
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(@Nullable Employee employee) {
        this.employee = employee;
    }

    public Set<ProductReceipt> getProductReceipts() {
        return productReceipts;
    }

    public void setProductReceipts(Set<ProductReceipt> productReceipts) {
        this.productReceipts = productReceipts;
    }
}
