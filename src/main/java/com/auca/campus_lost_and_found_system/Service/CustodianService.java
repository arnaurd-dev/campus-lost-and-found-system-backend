package com.auca.campus_lost_and_found_system.Service;

import com.auca.campus_lost_and_found_system.Model.CollectionPoint;
import com.auca.campus_lost_and_found_system.Model.Custodian;
import com.auca.campus_lost_and_found_system.Model.ItemCategory;
import com.auca.campus_lost_and_found_system.Repository.CollectionPointRepository;
import com.auca.campus_lost_and_found_system.Repository.CustodianRepository;
import com.auca.campus_lost_and_found_system.Repository.ItemCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustodianService {

@Autowired
    private CustodianRepository custodianRepository;

@Autowired
    private CollectionPointRepository collectionPointRepository;

@Autowired
private ItemCategoryRepository itemCategoryRepository;

public String saveCustodian(Custodian custodian) {
        if (custodian.getCollectionPoint() != null && custodian.getCollectionPoint().getId() != null) {
            collectionPointRepository.findById(custodian.getCollectionPoint().getId())
                .ifPresent(custodian::setCollectionPoint);
        }
        if (custodian.getItemCategories() != null && !custodian.getItemCategories().isEmpty()) {
            List<ItemCategory> categories = new ArrayList<>();
            for (ItemCategory cat : custodian.getItemCategories()) {
                if (cat.getId() != null) {
                    itemCategoryRepository.findById(cat.getId()).ifPresent(categories::add);
                }
            }
            custodian.setItemCategories(categories);
        }
        custodianRepository.save(custodian);
        return "Custodian saved successfully";
    }

public List<Custodian> getAllCustodians() {
        return custodianRepository.findAll();
    }

public Optional<Custodian> getCustodianById(UUID id) {
        return custodianRepository.findById(id);
    }

public String updateCustodian(UUID id, Custodian updated) {
        Optional<Custodian> existing = custodianRepository.findById(id);
        if (existing.isEmpty()) {
            return "The custodian with that id does not exist";
        }
        Custodian custodian = existing.get();
        custodian.setFirstName(updated.getFirstName());
        custodian.setLastName(updated.getLastName());
        custodian.setPhone(updated.getPhone());
        custodian.setCollectionPoint(updated.getCollectionPoint());
        custodian.setItemCategories(updated.getItemCategories());
        custodianRepository.save(custodian);
        return "Custodian updated successfully";
    }

public String deleteCustodian(UUID id) {
        Optional<Custodian> existing = custodianRepository.findById(id);
        if (existing.isEmpty()) {
            return "The custodian with that id does not exist";
        }
        custodianRepository.deleteById(id);
        return "Custodian deleted successfully";
    }

public List<Custodian> getCustodiansByItemCategoryName(String name) {
        return custodianRepository.findCustodiansByItemCategoryName(name);
    }

public String addCategoryToCustodian(UUID custodianId, UUID itemCategoryId) {
        Optional<Custodian> custodianOpt = custodianRepository.findById(custodianId);
        if (custodianOpt.isEmpty()) {
            return "The custodian with that id does not exist";
        }
        Optional<ItemCategory> categoryOpt = itemCategoryRepository.findById(itemCategoryId);
        if (categoryOpt.isEmpty()) {
            return "That item category does not exist";
        }
        Custodian custodian = custodianOpt.get();
        ItemCategory category = categoryOpt.get();
        if (custodian.getItemCategories() == null) {
            custodian.setItemCategories(new ArrayList<>());
        }
        for (ItemCategory existingCat : custodian.getItemCategories()) {
            if (existingCat.getId().equals(itemCategoryId)) {
                return "The custodian already has that item category";
            }
        }
        custodian.getItemCategories().add(category);
        custodianRepository.save(custodian);
        return "Item category added to the custodian";
    }

public String moveCustodianCollectionPoint(UUID custodianId, int pointNumber) {
        Optional<Custodian> custodianOpt = custodianRepository.findById(custodianId);
        if (custodianOpt.isEmpty()) {
            return "The custodian with that id does not exist";
        }
        Optional<CollectionPoint> cpOpt = collectionPointRepository.findByPointNumber(pointNumber);
        if (cpOpt.isEmpty()) {
            return "The collection point with that pointNumber does not exist";
        }
        CollectionPoint newPoint = cpOpt.get();
        if (custodianRepository.existsByCollectionPoint(newPoint)) {
            return "That collection point is taken by another custodian";
        }
        Custodian custodian = custodianOpt.get();
        custodian.setCollectionPoint(newPoint);
        custodianRepository.save(custodian);
        return "Collection point changed successfully";
    }
}