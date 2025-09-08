package com.thoughtful.notegenie.admin.controller;

import com.thoughtful.notegenie.admin.service.BillingScheduler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scheduler")
public class BillingSchedulerController {

    private final BillingScheduler billingScheduler;

    public BillingSchedulerController(BillingScheduler billingScheduler) {
        this.billingScheduler = billingScheduler;
    }

    @GetMapping("/next-run")
    public ResponseEntity<NextRunInfo> getNextRunInfo() {
        return ResponseEntity.ok(new NextRunInfo(
                "2023-12-01T01:00:00Z",
                "2023-12-01T08:00:00Z"
        ));
    }
}