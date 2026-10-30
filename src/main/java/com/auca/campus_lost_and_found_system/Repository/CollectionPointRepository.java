package com.auca.campus_lost_and_found_system.Repository;

import com.auca.campus_lost_and_found_system.Model.CollectionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CollectionPointRepository extends JpaRepository<CollectionPoint, UUID> {

@Query("SELECT cp FROM CollectionPoint cp WHERE NOT EXISTS (SELECT c FROM Custodian c WHERE c.collectionPoint = cp) ORDER BY cp.pointNumber ASC")
    List<CollectionPoint> findFreeCollectionPoints();

    Optional<CollectionPoint> findByPointNumber(int pointNumber);
}
