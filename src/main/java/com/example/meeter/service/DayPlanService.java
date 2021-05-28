package com.example.meeter.service;

import com.example.meeter.dto.DayPlanRequestDto;
import com.example.meeter.dto.DayPlanResponseDto;
import com.example.meeter.entity.DayPlan;

import java.util.List;

public interface DayPlanService {

    List<DayPlanResponseDto> getPlans();

    DayPlanResponseDto postPlan(DayPlanRequestDto dto);

    void deletePlan(Long planId);

    void updatePlan(Long planId, DayPlanRequestDto dto);
}
