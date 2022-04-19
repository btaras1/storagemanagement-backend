package com.management.storage.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String address;
    private String city;

    @Nullable
    private String mobile;

    @OneToMany(mappedBy="buyer")
    @JsonIgnore
    private List<Receipt> receipts;

    public Buyer() {
    }

    public Buyer(String firstname, String lastname, String address, String city, @Nullable String mobile, List<Receipt> receipts) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.city = city;
        this.mobile = mobile;
        this.receipts = receipts;
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {return city;}

    public void setCity(String city) {this.city = city;}

    @Nullable
    public String getMobile() {
        return mobile;
    }

    public void setMobile(@Nullable String mobile) {
        this.mobile = mobile;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }
}
