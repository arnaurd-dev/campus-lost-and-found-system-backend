package com.auca.campus_lost_and_found_system.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "item_report")
public class ItemReport {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private UUID id;

private LocalDate reportDate;

@Enumerated(EnumType.STRING)
private ReportStatus status;

private String itemName;
private String locationFound;

@ManyToOne
@JoinColumn(name = "reporter_id")
private Reporter reporter;

@ManyToOne
@JoinColumn(name = "custodian_id")
private Custodian custodian;

public ItemReport() {}

public ItemReport(LocalDate reportDate, ReportStatus status, String itemName, String locationFound, Reporter reporter, Custodian custodian) {
        this.reportDate = reportDate;
        this.status = status;
        this.itemName = itemName;
        this.locationFound = locationFound;
        this.reporter = reporter;
        this.custodian = custodian;
    }

public UUID getId() {
        return id;
    }

public void setId(UUID id) {
        this.id = id;
    }

public LocalDate getReportDate() {
        return reportDate;
    }

public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

public ReportStatus getStatus() {
        return status;
    }

public void setStatus(ReportStatus status) {
        this.status = status;
    }

public String getItemName() {
        return itemName;
    }

public void setItemName(String itemName) {
        this.itemName = itemName;
    }

public String getLocationFound() {
        return locationFound;
    }

public void setLocationFound(String locationFound) {
        this.locationFound = locationFound;
    }

public Reporter getReporter() {
        return reporter;
    }

public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }

public Custodian getCustodian() {
        return custodian;
    }

public void setCustodian(Custodian custodian) {
        this.custodian = custodian;
    }
}