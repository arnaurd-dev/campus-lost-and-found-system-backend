package com.auca.campus_lost_and_found_system.Service;

import com.auca.campus_lost_and_found_system.Model.ItemReport;
import com.auca.campus_lost_and_found_system.Repository.CustodianRepository;
import com.auca.campus_lost_and_found_system.Repository.ItemReportRepository;
import com.auca.campus_lost_and_found_system.Repository.ReporterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

public String saveItemReport(ItemReport itemReport) {
        if (itemReport.getReporter() != null && itemReport.getReporter().getId() != null) {
            reporterRepository.findById(itemReport.getReporter().getId())
                .ifPresent(itemReport::setReporter);
        }
        if (itemReport.getCustodian() != null && itemReport.getCustodian().getId() != null) {
            custodianRepository.findById(itemReport.getCustodian().getId())
                .ifPresent(itemReport::setCustodian);
        }
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
        report.setStatus(updated.getStatus());
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
        itemReportRepository.deleteById(id);
        return "Item report deleted successfully";
    }
}