package com.example.university_course_work.university;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Date;

@Getter
@AllArgsConstructor
@Builder
public class StudentJournal {
    int student_id;
    String student_name;
    Date stamp;
    String operation;
}
