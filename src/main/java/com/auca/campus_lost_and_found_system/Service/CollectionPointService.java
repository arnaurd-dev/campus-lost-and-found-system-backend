package com.auca.campus_lost_and_found_system.Service;

import com.auca.campus_lost_and_found_system.Model.CollectionPoint;
import com.auca.campus_lost_and_found_system.Repository.CollectionPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CollectionPointService {

@Autowired
private CollectionPointRepository collectionPointRepository;

public String saveCollectionPoint(CollectionPoint collectionPoint) {
        collectionPointRepository.save(collectionPoint);
        return "Collection point saved successfully";
    }

public List<CollectionPoint> getAllCollectionPoints() {
        return collectionPointRepository.findAll();
    }

public Optional<CollectionPoint> getCollectionPointById(UUID id) {
        return collectionPointRepository.findById(id);
    }

public String updateCollectionPoint(UUID id, CollectionPoint updated) {
        Optional<CollectionPoint> existing = collectionPointRepository.findById(id);
        if (existing.isEmpty()) {
            return "The collection point with that id does not exist";
        }
        CollectionPoint point = existing.get();
        point.setPointNumber(updated.getPointNumber());
        point.setName(updated.getName());
        collectionPointRepository.save(point);
        return "Collection point updated successfully";
    }

public String deleteCollectionPoint(UUID id) {
        Optional<CollectionPoint> existing = collectionPointRepository.findById(id);
        if (existing.isEmpty()) {
            return "The collection point with that id does not exist";
        }
        collectionPointRepository.deleteById(id);
        return "Collection point deleted successfully";
    }
}
