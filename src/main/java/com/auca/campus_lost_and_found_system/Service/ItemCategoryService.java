package com.auca.campus_lost_and_found_system.Service;

import com.auca.campus_lost_and_found_system.Model.ItemCategory;
import com.auca.campus_lost_and_found_system.Repository.ItemCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemCategoryService {

@Autowired
private ItemCategoryRepository itemCategoryRepository;

public String saveItemCategory(ItemCategory itemCategory) {
        itemCategoryRepository.save(itemCategory);
        return "Item category saved successfully";
    }

public List<ItemCategory> getAllItemCategories() {
        return itemCategoryRepository.findAll();
    }

public Optional<ItemCategory> getItemCategoryById(UUID id) {
        return itemCategoryRepository.findById(id);
    }

public String updateItemCategory(UUID id, ItemCategory updated) {
        Optional<ItemCategory> existing = itemCategoryRepository.findById(id);
        if (existing.isEmpty()) {
            return "The item category with that id does not exist";
        }
        ItemCategory category = existing.get();
        category.setName(updated.getName());
        itemCategoryRepository.save(category);
        return "Item category updated successfully";
    }

public String deleteItemCategory(UUID id) {
        Optional<ItemCategory> existing = itemCategoryRepository.findById(id);
        if (existing.isEmpty()) {
            return "The item category with that id does not exist";
        }
        itemCategoryRepository.deleteById(id);
        return "Item category deleted successfully";
    }
}
