package com.auca.campus_lost_and_found_system.Repository;

import com.auca.campus_lost_and_found_system.Model.Custodian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CustodianRepository extends JpaRepository<Custodian, UUID> {
}