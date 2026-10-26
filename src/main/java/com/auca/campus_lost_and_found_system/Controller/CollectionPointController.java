package com.auca.campus_lost_and_found_system.Controller;

import com.auca.campus_lost_and_found_system.Model.CollectionPoint;
import com.auca.campus_lost_and_found_system.Service.CollectionPointService;
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
@RequestMapping("/api/collection-points")
public class CollectionPointController {

@Autowired
private CollectionPointService collectionPointService;

@PostMapping("/save")
public ResponseEntity<String> saveCollectionPoint(@RequestBody CollectionPoint collectionPoint) {
        String response = collectionPointService.saveCollectionPoint(collectionPoint);
        if (response.equals("Collection point saved successfully")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

@GetMapping("/all")
public ResponseEntity<List<CollectionPoint>> getAllCollectionPoints() {
        return new ResponseEntity<>(collectionPointService.getAllCollectionPoints(), HttpStatus.OK);
    }

@GetMapping("/{id}")
public ResponseEntity<?> getCollectionPointById(@PathVariable UUID id) {
        Optional<CollectionPoint> collectionPoint = collectionPointService.getCollectionPointById(id);
        if (collectionPoint.isPresent()) {
            return new ResponseEntity<>(collectionPoint.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The collection point with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

@PutMapping("/update/{id}")
public ResponseEntity<String> updateCollectionPoint(@PathVariable UUID id, @RequestBody CollectionPoint collectionPoint) {
        String response = collectionPointService.updateCollectionPoint(id, collectionPoint);
        if (response.equals("Collection point updated successfully")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

@DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteCollectionPoint(@PathVariable UUID id) {
        String response = collectionPointService.deleteCollectionPoint(id);
        if (response.equals("Collection point deleted successfully")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}