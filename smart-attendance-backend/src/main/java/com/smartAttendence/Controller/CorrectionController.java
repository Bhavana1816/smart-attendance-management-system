package com.smartAttendence.Controller;

import com.smartAttendence.dto.*;
import com.smartAttendence.service.CorrectionService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/corrections")
public class CorrectionController {

    private final CorrectionService correctionService;

    public CorrectionController(
            CorrectionService correctionService
    ) {
        this.correctionService =
                correctionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public String create(
            org.springframework.security.core.Authentication authentication,
            @RequestBody CorrectionRequest request
    ) {

        correctionService.createRequest(
                authentication.getName(),
                request
        );

        return "Correction request submitted";
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('FACULTY','ADMIN')")
    public List<CorrectionResponse> pending() {

        return correctionService.pendingRequests();
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('FACULTY','ADMIN')")
    public String approve(
            @PathVariable Long id
    ) {

        correctionService.approve(id);

        return "Correction approved";
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('FACULTY','ADMIN')")
    public String reject(
            @PathVariable Long id
    ) {

        correctionService.reject(id);

        return "Correction rejected";
    }
}
