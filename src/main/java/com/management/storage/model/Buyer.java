package com.management.storage.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "buyer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(exclude = {"receipts", "created", "modified"})
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 45)
    private String firstname;
    @Column(length = 45)
    private String lastname;
    @Column(length = 45)
    private String address;
    @NotNull
    @Column(length = 45)
    private String city;

    @Nullable
    @Column(length = 35)
    private String mobile;

    @CreationTimestamp
    private LocalDate created;
    @UpdateTimestamp
    private LocalDate modified;

    @OneToMany(mappedBy="buyer")
    @JsonIgnore
    private List<Receipt> receipts;
}
