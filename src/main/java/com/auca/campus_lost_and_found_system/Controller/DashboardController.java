package com.auca.campus_lost_and_found_system.Controller;

import com.auca.campus_lost_and_found_system.Service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

@Autowired
private DashboardService dashboardService;

@GetMapping("/summary")
public ResponseEntity<Map<String, Long>> getDashboardSummary() {
        return new ResponseEntity<>(dashboardService.getDashboardSummary(), HttpStatus.OK);
    }
}