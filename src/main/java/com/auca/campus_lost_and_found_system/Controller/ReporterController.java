package com.auca.campus_lost_and_found_system.Controller;

import com.auca.campus_lost_and_found_system.Model.Reporter;
import com.auca.campus_lost_and_found_system.Service.ReporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/reporters")
public class ReporterController {

@Autowired
private ReporterService reporterService;

@PostMapping("/save")
public ResponseEntity<String> saveReporter(@RequestBody Reporter reporter) {
        String response = reporterService.saveReporter(reporter);
        if (response.equals("Reporter saved successfully")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

@GetMapping("/all")
public ResponseEntity<List<Reporter>> getAllReporters() {
    return new ResponseEntity<>(reporterService.getAllReporters(), HttpStatus.OK);
    }

@GetMapping("/{id}")
    public ResponseEntity<?> getReporterById(@PathVariable UUID id) {
        Optional<Reporter> reporter = reporterService.getReporterById(id);
        if (reporter.isPresent()) {
            return new ResponseEntity<>(reporter.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The reporter with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

@PutMapping("/update/{id}")
    public ResponseEntity<String> updateReporter(@PathVariable UUID id, @RequestBody Reporter reporter) {
        String response = reporterService.updateReporter(id, reporter);
        if (response.equals("Reporter updated successfully")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

@DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReporter(@PathVariable UUID id) {
        String response = reporterService.deleteReporter(id);
        if (response.equals("Reporter deleted successfully")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
