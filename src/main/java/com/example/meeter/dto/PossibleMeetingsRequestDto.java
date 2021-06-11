package com.example.meeter.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
public class PossibleMeetingsRequestDto {

    @JsonAlias("meeting_time")
    private LocalTime meetingTime;
    @JsonAlias("day_plan_ids")
    private Long[] dayPlanIds = new Long[2];
}
