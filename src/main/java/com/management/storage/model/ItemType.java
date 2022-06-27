package com.management.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "item_type")
@EqualsAndHashCode(exclude = {"items"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ItemType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    @NotEmpty
    private String value;

    @OneToMany(mappedBy="itemType")
    @JsonIgnore
    private List<Item> items;
}
