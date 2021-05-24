package com.example.meeter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DayPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    private LocalTime dayStart;
    private LocalTime dayEnd;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.DETACH},
            orphanRemoval = true)
    @JoinColumn(name = "plan_id")
    private List<Meeting> meetings = new ArrayList<>();

}
