package com.auca.campus_lost_and_found_system.Service;

import com.auca.campus_lost_and_found_system.Model.ItemReport;
import com.auca.campus_lost_and_found_system.Model.ReportStatus;
import com.auca.campus_lost_and_found_system.Repository.CustodianRepository;
import com.auca.campus_lost_and_found_system.Repository.ItemReportRepository;
import com.auca.campus_lost_and_found_system.Repository.ReporterRepository;
import com.auca.campus_lost_and_found_system.Repository.ReturnReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemReportService {

@Autowired
private ItemReportRepository itemReportRepository;

@Autowired
private ReporterRepository reporterRepository;

@Autowired
private CustodianRepository custodianRepository;

@Autowired
private ReturnReceiptRepository returnReceiptRepository;

public String saveItemReport(ItemReport itemReport) {
        if (itemReport.getReportDate() != null && itemReport.getReportDate().isBefore(LocalDate.now())) {
            return "Item report date cannot be in the past";
        }
        if (itemReport.getReporter() != null && itemReport.getReporter().getId() != null) {
            reporterRepository.findById(itemReport.getReporter().getId())
                .ifPresent(itemReport::setReporter);
        }
        if (itemReport.getCustodian() != null && itemReport.getCustodian().getId() != null) {
            custodianRepository.findById(itemReport.getCustodian().getId())
                .ifPresent(itemReport::setCustodian);
        }
        if (itemReport.getCustodian() != null && itemReport.getReportDate() != null) {
            if (itemReportRepository.existsByCustodianAndReportDateAndStatusNot(itemReport.getCustodian(), itemReport.getReportDate(), ReportStatus.CLOSED)) {
                return "Custodian already handles a report on that date";
            }
        }
        itemReport.setStatus(ReportStatus.REPORTED);
        itemReportRepository.save(itemReport);
        return "Item report saved successfully";
    }

public List<ItemReport> getAllItemReports() {
        return itemReportRepository.findAll();
    }

public Optional<ItemReport> getItemReportById(UUID id) {
        return itemReportRepository.findById(id);
    }

public String updateItemReport(UUID id, ItemReport updated) {
        Optional<ItemReport> existing = itemReportRepository.findById(id);
        if (existing.isEmpty()) {
            return "The item report with that id does not exist";
        }
        ItemReport report = existing.get();
        report.setReportDate(updated.getReportDate());
        report.setItemName(updated.getItemName());
        report.setLocationFound(updated.getLocationFound());
        report.setReporter(updated.getReporter());
        report.setCustodian(updated.getCustodian());
        itemReportRepository.save(report);
        return "Item report updated successfully";
    }

public String deleteItemReport(UUID id) {
        Optional<ItemReport> existing = itemReportRepository.findById(id);
        if (existing.isEmpty()) {
            return "The item report with that id does not exist";
        }
        if (returnReceiptRepository.existsByItemReport(existing.get())) {
            return "That item report has return receipts, delete them first";
        }
        itemReportRepository.deleteById(id);
        return "Item report deleted successfully";
    }

public List<ItemReport> getItemReportsByStatus(ReportStatus status) {
        return itemReportRepository.findByStatusOrderByReportDateAsc(status);
    }

public List<ItemReport> getItemReportsBetweenDates(LocalDate start, LocalDate end) {
        return itemReportRepository.findByReportDateBetweenOrderByReportDateAsc(start, end);
    }

public String updateItemReportStatus(UUID id, ReportStatus newStatus) {
        Optional<ItemReport> existing = itemReportRepository.findById(id);
        if (existing.isEmpty()) {
            return "The item report with that id does not exist";
        }
        ItemReport report = existing.get();
        ReportStatus current = report.getStatus();
        boolean allowed = false;
        if (current == ReportStatus.REPORTED) {
            if (newStatus == ReportStatus.MATCHED || newStatus == ReportStatus.CLOSED) {
                allowed = true;
            }
        } else if (current == ReportStatus.MATCHED) {
            if (newStatus == ReportStatus.RETURNED || newStatus == ReportStatus.CLOSED) {
                allowed = true;
            }
        }
        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        report.setStatus(newStatus);
        itemReportRepository.save(report);
        return "Item report status updated successfully";
    }

public List<Object[]> getStatsByStatus() {
        return itemReportRepository.countReportsPerStatus();
    }
@Transactional
public String cancelDay(UUID custodianId, LocalDate date) {
        int count = itemReportRepository.cancelDayReports(custodianId, date, ReportStatus.CLOSED, ReportStatus.RETURNED);
        return count + " item reports cancelled";
    }
}