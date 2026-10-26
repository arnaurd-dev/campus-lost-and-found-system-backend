package com.auca.campus_lost_and_found_system.Controller;

import com.auca.campus_lost_and_found_system.Model.ItemReport;
import com.auca.campus_lost_and_found_system.Service.ItemReportService;
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
@RequestMapping("/api/item-reports")
public class ItemReportController {

@Autowired
private ItemReportService itemReportService;

@PostMapping("/save")
public ResponseEntity<String> saveItemReport(@RequestBody ItemReport itemReport) {
        String response = itemReportService.saveItemReport(itemReport);
        if (response.equals("Item report saved successfully")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

@GetMapping("/all")
public ResponseEntity<List<ItemReport>> getAllItemReports() {
        return new ResponseEntity<>(itemReportService.getAllItemReports(), HttpStatus.OK);
    }

@GetMapping("/{id}")
public ResponseEntity<?> getItemReportById(@PathVariable UUID id) {
    Optional<ItemReport> itemReport = itemReportService.getItemReportById(id);
        if (itemReport.isPresent()) {
            return new ResponseEntity<>(itemReport.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The item report with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

@PutMapping("/update/{id}")
public ResponseEntity<String> updateItemReport(@PathVariable UUID id, @RequestBody ItemReport itemReport) {
        String response = itemReportService.updateItemReport(id, itemReport);
        if (response.equals("Item report updated successfully")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

@DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteItemReport(@PathVariable UUID id) {
        String response = itemReportService.deleteItemReport(id);
        if (response.equals("Item report deleted successfully")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}