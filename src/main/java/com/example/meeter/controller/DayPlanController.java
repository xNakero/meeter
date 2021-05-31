package com.example.meeter.controller;

import com.example.meeter.dto.DayPlanRequestDto;
import com.example.meeter.dto.DayPlanResponseDto;
import com.example.meeter.service.impl.DayPlanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")
public class DayPlanController {

    private final DayPlanServiceImpl dayPlanService;

    @Autowired
    public DayPlanController(DayPlanServiceImpl dayPlanService) {
        this.dayPlanService = dayPlanService;
    }

    @GetMapping("plans")
    public ResponseEntity<List<DayPlanResponseDto>> getDayPlans() {
        return new ResponseEntity<>(dayPlanService.getPlans(), HttpStatus.OK);
    }

    @PostMapping("plans")
    public ResponseEntity<DayPlanResponseDto> postDayPlan(@RequestBody DayPlanRequestDto dto) {
        return new ResponseEntity<>(dayPlanService.postPlan(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("plans/{planId}")
    public ResponseEntity<?> putDayPlan(@PathVariable Long planId) {
        dayPlanService.deletePlan(planId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("plans/{planId}")
    public ResponseEntity<?> deleteDayPlan(@PathVariable Long planId, @RequestBody DayPlanRequestDto dto) {
        dayPlanService.updatePlan(planId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
