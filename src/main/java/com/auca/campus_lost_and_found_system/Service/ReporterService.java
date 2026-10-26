package com.auca.campus_lost_and_found_system.Service;

import com.auca.campus_lost_and_found_system.Model.Reporter;
import com.auca.campus_lost_and_found_system.Repository.ReporterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReporterService {

@Autowired
private ReporterRepository reporterRepository;

public String saveReporter(Reporter reporter) {
        reporterRepository.save(reporter);
        return "Reporter saved successfully";
    }

public List<Reporter> getAllReporters() {
        return reporterRepository.findAll();
    }

public Optional<Reporter> getReporterById(UUID id) {
        return reporterRepository.findById(id);
    }

public String updateReporter(UUID id, Reporter updated) {
        Optional<Reporter> existing = reporterRepository.findById(id);
        if (existing.isEmpty()) {
            return "The reporter with that id does not exist";
        }
        Reporter reporter = existing.get();
        reporter.setFirstName(updated.getFirstName());
        reporter.setLastName(updated.getLastName());
        reporter.setCampusId(updated.getCampusId());
        reporter.setPhone(updated.getPhone());
        reporter.setEmail(updated.getEmail());
        reporterRepository.save(reporter);
        return "Reporter updated successfully";
    }

public String deleteReporter(UUID id) {
        Optional<Reporter> existing = reporterRepository.findById(id);
        if (existing.isEmpty()) {
            return "The reporter with that id does not exist";
        }
        reporterRepository.deleteById(id);
        return "Reporter deleted successfully";
    }
}
