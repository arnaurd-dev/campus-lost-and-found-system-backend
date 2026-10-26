package com.auca.campus_lost_and_found_system.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "item_category")
public class ItemCategory {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private UUID id;

private String name;

@JsonIgnore
@ManyToMany(mappedBy = "itemCategories")
private List<Custodian> custodians;

public ItemCategory() {}

public ItemCategory(String name) {
        this.name = name;
    }

public UUID getId() {
        return id;
    }

public void setId(UUID id) {
        this.id = id;
    }

public String getName() {
        return name;
    }

public void setName(String name) {
        this.name = name;
    }

public List<Custodian> getCustodians() {
        return custodians;
    }

public void setCustodians(List<Custodian> custodians) {
        this.custodians = custodians;
    }
}