package com.example.meeter.service.impl;

import com.example.meeter.dto.TimePeriodDto;
import com.example.meeter.dto.PossibleMeetingsRequestDto;
import com.example.meeter.dto.PossibleMeetingsResponseDto;
import com.example.meeter.entity.DayPlan;
import com.example.meeter.entity.User;
import com.example.meeter.exceptions.ForbiddenException;
import com.example.meeter.exceptions.NotFoundException;
import com.example.meeter.repository.DayPlanRepository;
import com.example.meeter.repository.UserRepository;
import com.example.meeter.service.MeetingPlannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MeetingPlannerServiceImpl implements MeetingPlannerService {

    private final UserRepository userRepository;

    @Autowired
    public MeetingPlannerServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PossibleMeetingsResponseDto organizeMeetings(PossibleMeetingsRequestDto dto) {
        List<DayPlan> dayPlans = retrieveDayPlans(dto);
        List<List<TimePeriodDto>> freeTimes = dayPlans.stream()
                .map(e -> getFreeTimes(e, dto.getMeetingTime()))
                .collect(Collectors.toList());
        return new PossibleMeetingsResponseDto(getPossibleMeetingTimes(freeTimes, dto.getMeetingTime()));
    }

    private List<DayPlan> retrieveDayPlans(PossibleMeetingsRequestDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(NotFoundException::new);
        List<DayPlan> dayPlans = new LinkedList<>();
        for (Long id : dto.getDayPlanIds()) {
            boolean isPlanOwner = user.getDayPlans().stream()
                    .map(DayPlan::getPlanId)
                    .anyMatch(i -> i.equals(id));
            if (!isPlanOwner) {
                throw new ForbiddenException("Plan " + (Collections.singletonList(dto).indexOf(dto) + 1));
            }
            DayPlan dayPlan = user.getDayPlans().stream()
                    .filter(e -> e.getPlanId().equals(id))
                    .findAny()
                    .orElseThrow(() -> new ForbiddenException("Plan " + (Collections.singletonList(dto).indexOf(dto) + 1)));
            dayPlans.add(dayPlan);
        }
        return dayPlans;
    }

    private List<TimePeriodDto> getFreeTimes(DayPlan dayPlan, LocalTime meetingTime) {
        List<TimePeriodDto> freeTimes = new LinkedList<>();
        int secondsForMeeting = meetingTime.toSecondOfDay();
        int secondsBeforeMeeting = getSecondsTimePeriod(dayPlan.getDayStart(), dayPlan.getMeetings().get(0).getStart());
        if (secondsBeforeMeeting >= secondsForMeeting) {
            freeTimes.add(new TimePeriodDto(dayPlan.getDayStart(), dayPlan.getMeetings().get(0).getStart()));
        }

        for (int i = 0; i < dayPlan.getMeetings().size(); i++) {
            LocalTime freeTimeEnd;
            if (i == dayPlan.getMeetings().size() - 1) {
                freeTimeEnd = dayPlan.getDayEnd();
            } else {
                freeTimeEnd = dayPlan.getMeetings().get(i + 1).getStart();
            }
            int availableSeconds = getSecondsTimePeriod(dayPlan.getMeetings().get(i).getEnd(), freeTimeEnd);
            if (availableSeconds >= secondsForMeeting) {
                freeTimes.add(new TimePeriodDto(dayPlan.getMeetings().get(i).getEnd(), freeTimeEnd));
            }
        }

        return freeTimes;
    }

    private int getSecondsTimePeriod(LocalTime earlierTime, LocalTime laterTime) {
        return (int) Duration.between(earlierTime, laterTime).toSeconds();
    }

    private List<TimePeriodDto> getPossibleMeetingTimes(List<List<TimePeriodDto>> freeTimes, LocalTime meetingTime) {
        if (freeTimes.get(0).size() == 0 || freeTimes.get(1).size() == 0) {
            return new LinkedList<>();
        }
        List<TimePeriodDto> possibleMeetings = new LinkedList<>();
        int secondsForMeeting = meetingTime.toSecondOfDay();
        List<Integer> indexes = Stream
                .of(0, 0)
                .collect(Collectors.toList());
        boolean runs = true;
        int currentlyUsedFreeTime;

        while(runs) {
            if (freeTimes.get(0).get(indexes.get(0)).getStart()
                    .isBefore(freeTimes.get(1).get(indexes.get(1)).getStart())) {
                currentlyUsedFreeTime = 1;
            } else {
                currentlyUsedFreeTime = 0;
            }
            LocalTime startFreeTime = freeTimes
                    .get(currentlyUsedFreeTime)
                    .get(indexes.get(currentlyUsedFreeTime))
                    .getStart();
            LocalTime endFreeTime = freeTimes
                    .get((currentlyUsedFreeTime + 1) % 2)
                    .get(indexes.get((currentlyUsedFreeTime + 1) % 2))
                    .getEnd();
            if (getSecondsTimePeriod(startFreeTime, endFreeTime) >= secondsForMeeting) {
                possibleMeetings.add(new TimePeriodDto(startFreeTime, endFreeTime));
            }
            indexes.set((currentlyUsedFreeTime + 1) % 2, indexes.get((currentlyUsedFreeTime + 1) % 2) + 1);
            if (indexes.get((currentlyUsedFreeTime + 1) % 2) == freeTimes.get((currentlyUsedFreeTime + 1) % 2).size()) {
                break;
            }
            startFreeTime = freeTimes
                    .get((currentlyUsedFreeTime + 1) % 2)
                    .get(indexes.get((currentlyUsedFreeTime + 1) % 2))
                    .getStart();
            endFreeTime = freeTimes
                    .get(currentlyUsedFreeTime)
                    .get(indexes.get(currentlyUsedFreeTime))
                    .getEnd();
            if (startFreeTime.toSecondOfDay() > endFreeTime.toSecondOfDay()) {
                indexes.set(currentlyUsedFreeTime, indexes.get(currentlyUsedFreeTime) + 1);
                if (indexes.get(currentlyUsedFreeTime) == freeTimes.get(currentlyUsedFreeTime).size()) {
                    runs = false;
                }
            }
        }
        return possibleMeetings;
    }
}
