package com.auca.campus_lost_and_found_system.Service;

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
}