package com.auca.campus_lost_and_found_system.Repository;

import com.auca.campus_lost_and_found_system.Model.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, UUID> {
@Query("SELECT c FROM ItemCategory c WHERE c.custodians IS EMPTY")
List<ItemCategory> findUnusedItemCategories();
}
