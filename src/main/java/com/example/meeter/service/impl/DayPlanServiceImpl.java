package com.example.meeter.service.impl;

import com.example.meeter.dto.DayPlanRequestDto;
import com.example.meeter.dto.DayPlanResponseDto;
import com.example.meeter.entity.DayPlan;
import com.example.meeter.entity.User;
import com.example.meeter.exceptions.ForbiddenException;
import com.example.meeter.exceptions.NotFoundException;
import com.example.meeter.mapper.DayPlanMapper;
import com.example.meeter.repository.DayPlanRepository;
import com.example.meeter.repository.UserRepository;
import com.example.meeter.service.DayPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DayPlanServiceImpl implements DayPlanService {

    private final UserRepository userRepository;
    private final DayPlanMapper dayPlanMapper;
    private final DayPlanRepository dayPlanRepository;

    @Autowired
    public DayPlanServiceImpl(UserRepository userRepository, DayPlanMapper dayPlanMapper, DayPlanRepository dayPlanRepository) {
        this.userRepository = userRepository;
        this.dayPlanMapper = dayPlanMapper;
        this.dayPlanRepository = dayPlanRepository;
    }


    @Override
    public List<DayPlanResponseDto> getPlans() {
        User user = getUserPrincipal();
        return user.getDayPlans().stream()
                .map(dayPlanMapper::mapToDayPlanResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public DayPlanResponseDto postPlan(DayPlanRequestDto dto) {
        User user = getUserPrincipal();
        DayPlan dayPlan = dayPlanMapper.mapToDayPlan(dto);
        dayPlan.setUser(user);
        dayPlanRepository.save(dayPlan);
        return dayPlanMapper.mapToDayPlanResponseDto(dayPlan);
    }

    @Override
    public void deletePlan(Long planId) {
        User user = getUserPrincipal();
        DayPlan dayPlan = dayPlanRepository.findById(planId).orElseThrow(NotFoundException::new);
        if (!user.getDayPlans().contains(dayPlan)) {
            throw new ForbiddenException();
        }
        dayPlanRepository.delete(dayPlan);
    }

    @Override
    public void updatePlan(Long planId, DayPlanRequestDto dto) {
        User user = getUserPrincipal();
        DayPlan dayPlan = dayPlanRepository.findById(planId).orElseThrow(NotFoundException::new);
        if (!user.getDayPlans().contains(dayPlan)) {
            throw new ForbiddenException();
        }
        dayPlanMapper.updateDayPlan(dayPlan, dto);
        dayPlanRepository.save(dayPlan);
    }

    private User getUserPrincipal() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByUsername(username)
                .orElseThrow(NotFoundException::new);
    }
}
