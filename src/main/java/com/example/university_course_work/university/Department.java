package com.example.university_course_work.university;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Department {
    String name;
    int number;
    int count;
}
