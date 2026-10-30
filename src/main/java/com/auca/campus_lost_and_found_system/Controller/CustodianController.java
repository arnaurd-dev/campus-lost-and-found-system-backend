package com.auca.campus_lost_and_found_system.Controller;

import com.auca.campus_lost_and_found_system.Model.Custodian;
import com.auca.campus_lost_and_found_system.Service.CustodianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/custodians")
public class CustodianController {

@Autowired
private CustodianService custodianService;

@PostMapping("/save")
public ResponseEntity<String> saveCustodian(@RequestBody Custodian custodian) {
        String response = custodianService.saveCustodian(custodian);
        if (response.equals("Custodian saved successfully")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

@GetMapping("/all")
public ResponseEntity<List<Custodian>> getAllCustodians() {
        return new ResponseEntity<>(custodianService.getAllCustodians(), HttpStatus.OK);
    }

@GetMapping("/{id}")
public ResponseEntity<?> getCustodianById(@PathVariable UUID id) {
        Optional<Custodian> custodian = custodianService.getCustodianById(id);
        if (custodian.isPresent()) {
            return new ResponseEntity<>(custodian.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The custodian with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

@PutMapping("/update/{id}")
public ResponseEntity<String> updateCustodian(@PathVariable UUID id, @RequestBody Custodian custodian) {
        String response = custodianService.updateCustodian(id, custodian);
        if (response.equals("Custodian updated successfully")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

@DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteCustodian(@PathVariable UUID id) {
        String response = custodianService.deleteCustodian(id);
        if (response.equals("Custodian deleted successfully")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

@GetMapping("/by-item-category")
public ResponseEntity<List<Custodian>> getCustodiansByItemCategory(@RequestParam String name) {
        return new ResponseEntity<>(custodianService.getCustodiansByItemCategoryName(name), HttpStatus.OK);
    }

@PutMapping("/{custodianId}/item-categories/{itemCategoryId}")
public ResponseEntity<String> addCategoryToCustodian(@PathVariable UUID custodianId, @PathVariable UUID itemCategoryId) {
        String response = custodianService.addCategoryToCustodian(custodianId, itemCategoryId);
        if (response.equals("Item category added to the custodian")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else if (response.equals("The custodian already has that item category")) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

@PatchMapping("/{custodianId}/collection-point")
public ResponseEntity<String> moveCustodianCollectionPoint(@PathVariable UUID custodianId, @RequestParam int pointNumber) {
        String response = custodianService.moveCustodianCollectionPoint(custodianId, pointNumber);
        if (response.equals("Collection point changed successfully")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else if (response.equals("That collection point is taken by another custodian")) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}