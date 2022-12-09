package com.example.university_course_work.database.types;

import lombok.Getter;

public class Reference {
    @Getter
    String column;
    @Getter
    String table;
    public Reference(String table, String column) {
        this.table = table;
        this.column = column;
    }
}
