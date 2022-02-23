package com.example.demo.src.calendar.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class GetCalendarPetMoodRes {
    private Date calendarDate;
    private String petMood;
}
