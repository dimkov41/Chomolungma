package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.domain.entities.Peak;
import com.dimkov.bgMountains.service.PeakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PeakApiController {
    private final PeakService peakService;

    @Autowired
    public PeakApiController(PeakService peakService) {
        this.peakService = peakService;
    }

    @GetMapping(params = {"page", "size"}, value = "/peaks/all")
    public Page<Peak> findPaginated(
            @RequestParam("page") int page, @RequestParam("size") int size) {

        Page<Peak> resultPage = peakService.findPaginated(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new IllegalArgumentException();
        }

        return resultPage;
    }
}


