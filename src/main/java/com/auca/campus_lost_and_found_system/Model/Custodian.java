package com.auca.campus_lost_and_found_system.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "custodian")
public class Custodian {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private UUID id;

private String firstName;
private String lastName;
private String phone;

@ManyToOne
@JoinColumn(name = "collection_point_id", nullable = true)
private CollectionPoint collectionPoint;

@ManyToMany
@JoinTable(
        name = "custodian_item_category",
        joinColumns = @JoinColumn(name = "custodian_id"),
        inverseJoinColumns = @JoinColumn(name = "item_category_id")
    )
private List<ItemCategory> itemCategories;

public Custodian() {}

public Custodian(String firstName, String lastName, String phone, CollectionPoint collectionPoint, List<ItemCategory> itemCategories) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.collectionPoint = collectionPoint;
        this.itemCategories = itemCategories;
    }

public UUID getId() {
        return id;
    }

public void setId(UUID id) {
        this.id = id;
    }

public String getFirstName() {
        return firstName;
    }

public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

public String getLastName() {
        return lastName;
    }

public void setLastName(String lastName) {
        this.lastName = lastName;
    }

public String getPhone() {
        return phone;
    }

public void setPhone(String phone) {
        this.phone = phone;
    }

public CollectionPoint getCollectionPoint() {
        return collectionPoint;
    }

public void setCollectionPoint(CollectionPoint collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

public List<ItemCategory> getItemCategories() {
        return itemCategories;
    }

public void setItemCategories(List<ItemCategory> itemCategories) {
        this.itemCategories = itemCategories;
    }
}