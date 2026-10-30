package com.auca.campus_lost_and_found_system.Repository;

import com.auca.campus_lost_and_found_system.Model.CollectionPoint;
import com.auca.campus_lost_and_found_system.Model.Custodian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface CustodianRepository extends JpaRepository<Custodian, UUID> {

@Query("SELECT c FROM Custodian c JOIN c.itemCategories ic WHERE LOWER(ic.name) = LOWER(:name)")
List<Custodian> findCustodiansByItemCategoryName(@Param("name") String name);

boolean existsByCollectionPoint(CollectionPoint collectionPoint);
}