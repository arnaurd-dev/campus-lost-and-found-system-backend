package com.auca.campus_lost_and_found_system.Repository;

import com.auca.campus_lost_and_found_system.Model.ItemReport;
import com.auca.campus_lost_and_found_system.Model.ReturnReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReturnReceiptRepository extends JpaRepository<ReturnReceipt, UUID> {

@Query("SELECT r FROM ReturnReceipt r WHERE r.itemReport.reporter.campusId = :campusId ORDER BY r.issuedDate DESC")
List<ReturnReceipt> findByReporterCampusId(@Param("campusId") String campusId);
boolean existsByItemReport(ItemReport itemReport);
}
