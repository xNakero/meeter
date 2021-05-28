package com.example.meeter.dto;

import com.example.meeter.serialization.LocalTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class DayPlanResponseDto {

    @JsonProperty("plan_id")
    private Long planId;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonProperty("day_start")
    private LocalTime dayStart;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonProperty("day_end")
    private LocalTime dayEnd;
    private List<MeetingDto> meetings;
}
