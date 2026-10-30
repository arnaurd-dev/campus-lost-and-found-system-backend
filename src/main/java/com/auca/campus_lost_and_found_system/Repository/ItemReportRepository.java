package com.auca.campus_lost_and_found_system.Repository;

import com.auca.campus_lost_and_found_system.Model.Custodian;
import com.auca.campus_lost_and_found_system.Model.ItemReport;
import com.auca.campus_lost_and_found_system.Model.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ItemReportRepository extends JpaRepository<ItemReport, UUID> {

List<ItemReport> findByStatusOrderByReportDateAsc(ReportStatus status);
List<ItemReport> findByReportDateBetweenOrderByReportDateAsc(LocalDate start, LocalDate end);
boolean existsByCustodianAndReportDateAndStatusNot(Custodian custodian, LocalDate reportDate, ReportStatus status);
}