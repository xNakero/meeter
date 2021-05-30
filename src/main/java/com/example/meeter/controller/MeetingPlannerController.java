package com.example.meeter.controller;

import com.example.meeter.dto.PossibleMeetingsRequestDto;
import com.example.meeter.dto.PossibleMeetingsResponseDto;
import com.example.meeter.service.impl.MeetingPlannerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "**")
public class MeetingPlannerController {

    private final MeetingPlannerServiceImpl meetingPlannerService;

    @Autowired
    public MeetingPlannerController(MeetingPlannerServiceImpl meetingPlannerService) {
        this.meetingPlannerService = meetingPlannerService;
    }

    @PostMapping("meeting-times")
    public ResponseEntity<PossibleMeetingsResponseDto> getPossibleMeetingTimes(@RequestBody PossibleMeetingsRequestDto dto) {
        return new ResponseEntity<>(meetingPlannerService.organizeMeetings(dto), HttpStatus.OK);
    }
}
