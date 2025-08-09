package com.Hub.organization.controller;

import com.Hub.organization.dto.JobCreateDTO;
import com.Hub.organization.model.JobModel;
import com.Hub.organization.service.JobService;
import jdk.jshell.Snippet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.UUID;

@RestController
@RequestMapping("/job")
public class JobController {

    private final JobService jobService;

    public JobController (JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/{id}")
    public JobModel findById (@PathVariable UUID id) {
        return jobService.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> addNew(@RequestBody JobCreateDTO jobCreateDTO) {
        jobService.addNew(jobCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("New job created successfully");
    }
}
