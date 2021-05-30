package com.example.meeter.dto;

import com.example.meeter.serialization.LocalTimeDeserializer;
import com.example.meeter.serialization.LocalTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class DayPlanRequestDto {

    private List<TimePeriodDto> meetings;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonProperty("day_start")
    private LocalTime dayStart;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonProperty("day_end")
    private LocalTime dayEnd;
}
