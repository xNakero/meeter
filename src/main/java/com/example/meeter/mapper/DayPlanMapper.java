package com.example.meeter.mapper;

import com.example.meeter.dto.DayPlanRequestDto;
import com.example.meeter.dto.DayPlanResponseDto;
import com.example.meeter.entity.DayPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
@Service
public interface DayPlanMapper {

    @Mapping(target = "meetings", source = "meetings")
    DayPlan mapToDayPlan(DayPlanRequestDto dayPlanRequestDto);

    @Mapping(target = "meetings", source = "meetings")
    DayPlanResponseDto mapToDayPlanResponseDto(DayPlan dayPlan);

    @Mapping(target = "meetings", source = "meetings")
    void updateDayPlan(@MappingTarget DayPlan dayPlan, DayPlanRequestDto dto);
}
