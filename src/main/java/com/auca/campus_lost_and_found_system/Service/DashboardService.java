package com.auca.campus_lost_and_found_system.Service;

import com.auca.campus_lost_and_found_system.Model.ReportStatus;
import com.auca.campus_lost_and_found_system.Repository.CollectionPointRepository;
import com.auca.campus_lost_and_found_system.Repository.CustodianRepository;
import com.auca.campus_lost_and_found_system.Repository.ItemReportRepository;
import com.auca.campus_lost_and_found_system.Repository.ReporterRepository;
import com.auca.campus_lost_and_found_system.Repository.ReturnReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

@Autowired
private ReporterRepository reporterRepository;

@Autowired
private CustodianRepository custodianRepository;

@Autowired
private CollectionPointRepository collectionPointRepository;

@Autowired
private ItemReportRepository itemReportRepository;

@Autowired
private ReturnReceiptRepository returnReceiptRepository;

public Map<String, Long> getDashboardSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("reporters", reporterRepository.count());
        summary.put("custodians", custodianRepository.count());
        summary.put("freeCollectionPoints", (long) collectionPointRepository.findFreeCollectionPoints().size());
        summary.put("reportsToday", itemReportRepository.countByReportDate(LocalDate.now()));
        summary.put("reported", itemReportRepository.countByStatus(ReportStatus.REPORTED));
        summary.put("receipts", returnReceiptRepository.count());
        return summary;
    }
}
