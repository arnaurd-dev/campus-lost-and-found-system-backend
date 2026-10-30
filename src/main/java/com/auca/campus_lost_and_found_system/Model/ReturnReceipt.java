package com.auca.campus_lost_and_found_system.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "return_receipt")
public class ReturnReceipt {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private UUID id;

private String itemName;
private String verificationNote;
private int itemsReturned;
private LocalDate issuedDate;

@ManyToOne
@JoinColumn(name = "item_report_id")
private ItemReport itemReport;

public ReturnReceipt() {}

public ReturnReceipt(String itemName, String verificationNote, int itemsReturned, LocalDate issuedDate, ItemReport itemReport) {
        this.itemName = itemName;
        this.verificationNote = verificationNote;
        this.itemsReturned = itemsReturned;
        this.issuedDate = issuedDate;
        this.itemReport = itemReport;
    }

public UUID getId() {
        return id;
    }

public void setId(UUID id) {
        this.id = id;
    }

public String getItemName() {
    return itemName;
    }

public void setItemName(String itemName) {
        this.itemName = itemName;
    }

public String getVerificationNote() {
        return verificationNote;
    }

public void setVerificationNote(String verificationNote) {
        this.verificationNote = verificationNote;
    }

public int getItemsReturned() {
        return itemsReturned;
    }

public void setItemsReturned(int itemsReturned) {
        this.itemsReturned = itemsReturned;
    }

public LocalDate getIssuedDate() {
        return issuedDate;
    }

public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

public ItemReport getItemReport() {
        return itemReport;
    }

public void setItemReport(ItemReport itemReport) {
        this.itemReport = itemReport;
    }
}
