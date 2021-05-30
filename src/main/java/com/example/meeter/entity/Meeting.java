package com.example.meeter.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meetingId;
    @NonNull
    private LocalTime start;
    @NonNull
    private LocalTime end;
}
