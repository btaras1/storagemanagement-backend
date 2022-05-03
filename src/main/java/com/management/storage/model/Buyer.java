package com.management.storage.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "buyer")
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45)
    private String firstname;
    @Column(length = 45)
    private String lastname;
    @Column(length = 45)
    private String address;
    @Column(length = 45)
    private String city;

    @Nullable
    @Column(length = 35)
    private String mobile;
    @Temporal(TemporalType.DATE)
    private Date created;
    @Temporal(TemporalType.DATE)
    private Date modified;

    @OneToMany(mappedBy="buyer")
    @JsonIgnore
    private List<Receipt> receipts;
}
