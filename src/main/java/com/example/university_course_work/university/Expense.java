package com.example.university_course_work.university;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Expense {
    String name;
    int expenses;
}
