package com.auca.campus_lost_and_found_system.Repository;

import com.auca.campus_lost_and_found_system.Model.Reporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReporterRepository extends JpaRepository<Reporter, UUID> {

List<Reporter> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
@Query("SELECT DISTINCT ir.reporter FROM ItemReport ir WHERE ir.custodian.id = :custodianID")
List<Reporter> findReportersByCustodianId(@Param("custodianID") UUID custodianId);
}