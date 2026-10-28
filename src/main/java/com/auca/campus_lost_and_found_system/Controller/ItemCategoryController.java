package com.auca.campus_lost_and_found_system.Controller;

import com.auca.campus_lost_and_found_system.Model.ItemCategory;
import com.auca.campus_lost_and_found_system.Service.ItemCategoryService;
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
@RequestMapping("/api/item-categories")
public class ItemCategoryController {

@Autowired
private ItemCategoryService itemCategoryService;

@PostMapping("/save")
public ResponseEntity<String> saveItemCategory(@RequestBody ItemCategory itemCategory) {
        String response = itemCategoryService.saveItemCategory(itemCategory);
        if (response.equals("Item category saved successfully")) {
        return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

@GetMapping("/all")
public ResponseEntity<List<ItemCategory>> getAllItemCategories() {
        return new ResponseEntity<>(itemCategoryService.getAllItemCategories(), HttpStatus.OK);
    }

@GetMapping("/{id}")
public ResponseEntity<?> getItemCategoryById(@PathVariable UUID id) {
        Optional<ItemCategory> itemCategory = itemCategoryService.getItemCategoryById(id);
        if (itemCategory.isPresent()) {
        return new ResponseEntity<>(itemCategory.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The item category with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

@PutMapping("/update/{id}")
public ResponseEntity<String> updateItemCategory(@PathVariable UUID id, @RequestBody ItemCategory itemCategory) {
        String response = itemCategoryService.updateItemCategory(id, itemCategory);
        if (response.equals("Item category updated successfully")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

@DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteItemCategory(@PathVariable UUID id) {
        String response = itemCategoryService.deleteItemCategory(id);
        if (response.equals("Item category deleted successfully")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

@GetMapping("/unused")
public ResponseEntity<List<ItemCategory>> getUnusedItemCategories() {
        return new ResponseEntity<>(itemCategoryService.getUnusedItemCategories(), HttpStatus.OK);
    }
}