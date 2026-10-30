package com.auca.campus_lost_and_found_system.Controller;

import com.auca.campus_lost_and_found_system.Model.ReturnReceipt;
import com.auca.campus_lost_and_found_system.Service.ReturnReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/return-receipts")
public class ReturnReceiptController {

@Autowired
private ReturnReceiptService returnReceiptService;

@PostMapping("/save")
public ResponseEntity<String> saveReturnReceipt(@RequestParam UUID itemReportId, @RequestBody ReturnReceipt returnReceipt) {
        String response = returnReceiptService.saveReturnReceipt(itemReportId, returnReceipt);
        if (response.equals("Return receipt saved successfully")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else if (response.equals("Return receipts are only for RETURNED item reports")) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

@GetMapping("/reporter/{campusId}")
public ResponseEntity<List<ReturnReceipt>> getReturnReceiptsByReporter(@PathVariable String campusId) {
        return new ResponseEntity<>(returnReceiptService.getReturnReceiptsByReporter(campusId), HttpStatus.OK);
    }
@DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteReturnReceipt(@PathVariable UUID id) {
        String response = returnReceiptService.deleteReturnReceipt(id);
        if (response.equals("Return receipt deleted successfully")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}