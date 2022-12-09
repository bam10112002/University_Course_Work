package com.example.university_course_work.university;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class Student {

    public String name;
    public String group;
    public double avgEstimation;

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public double getAvgEstimation() {
        return avgEstimation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setAvgEstimation(double avgEstimation) {
        this.avgEstimation = avgEstimation;
    }
}
