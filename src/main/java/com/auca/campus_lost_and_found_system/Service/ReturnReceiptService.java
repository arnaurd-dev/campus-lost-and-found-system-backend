package com.auca.campus_lost_and_found_system.Service;

import com.auca.campus_lost_and_found_system.Model.ItemReport;
import com.auca.campus_lost_and_found_system.Model.ReportStatus;
import com.auca.campus_lost_and_found_system.Model.ReturnReceipt;
import com.auca.campus_lost_and_found_system.Repository.ItemReportRepository;
import com.auca.campus_lost_and_found_system.Repository.ReturnReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReturnReceiptService {

@Autowired
private ReturnReceiptRepository returnReceiptRepository;

@Autowired
private ItemReportRepository itemReportRepository;

public String saveReturnReceipt(UUID itemReportId, ReturnReceipt returnReceipt) {
        Optional<ItemReport> reportOpt = itemReportRepository.findById(itemReportId);
        if (reportOpt.isEmpty()) {
            return "The item report with that id does not exist";
        }
        ItemReport report = reportOpt.get();
        if (report.getStatus() != ReportStatus.RETURNED) {
            return "Return receipts are only for RETURNED item reports";
        }
        returnReceipt.setIssuedDate(LocalDate.now());
        returnReceipt.setItemReport(report);
        returnReceiptRepository.save(returnReceipt);
        return "Return receipt saved successfully";
    }

public List<ReturnReceipt> getReturnReceiptsByReporter(String campusId) {
       return returnReceiptRepository.findByReporterCampusId(campusId);
    }
public String deleteReturnReceipt(UUID id) {
        Optional<ReturnReceipt> existing = returnReceiptRepository.findById(id);
        if (existing.isEmpty()) {
            return "The return receipt with that id does not exist";
        }
        returnReceiptRepository.deleteById(id);
        return "Return receipt deleted successfully";
    }
}