package com.auca.campus_lost_and_found_system.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "reporter")
public class Reporter {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private UUID id;

private String firstName;
private String lastName;

@Column(unique = true, nullable = false)
private String campusId;

private String phone;
private String email;

public Reporter() {}

public Reporter(String firstName, String lastName, String campusId, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.campusId = campusId;
        this.phone = phone;
        this.email = email;
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

public String getCampusId() {
        return campusId;
    }

public void setCampusId(String campusId) {
        this.campusId = campusId;
    }

public String getPhone() {
        return phone;
    }

public void setPhone(String phone) {
        this.phone = phone;
    }

public String getEmail() {
        return email;
    }

public void setEmail(String email) {
        this.email = email;
    }
}