package com.example.meeter.service;

import com.example.meeter.dto.PossibleMeetingsRequestDto;
import com.example.meeter.dto.PossibleMeetingsResponseDto;

public interface MeetingPlannerService {

    PossibleMeetingsResponseDto organizeMeetings(PossibleMeetingsRequestDto dto);
}
