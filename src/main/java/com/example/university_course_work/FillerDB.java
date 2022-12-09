package com.example.university_course_work;

import com.example.university_course_work.database.DatabaseEngin;
import com.example.university_course_work.database.Transaction;
import com.example.university_course_work.university.University;
import lombok.NonNull;


import java.util.HashMap;

public class FillerDB {
    private University university;
    private DatabaseEngin engine;
    FillerDB(@NonNull University university, @NonNull DatabaseEngin engine) {
        this.engine = engine;
        this.university = university;
    }

    public void groupsInsert() {
        Transaction transaction = engine.createTransaction();
        for (int i = 1; i < 12; i++) {
            for (String group : university.GenerateGroups(7, i)) {
                Integer finalI = i;
                transaction.addToTransaction(engine.Insert("st_group", new HashMap<>() {{
                    put("name", group);
                    put("faculty_id", finalI.toString());
                }}));
            }
        }
        transaction.execute();
    }
    public void auditoriumInsert() {
        Transaction transaction = engine.createTransaction();
        for (int faculty = 1; faculty < 12; faculty++) {
            for (int number = 0; number < 10; number++) {
                Integer finalJ = number;
                Integer finalFaculty = faculty;
                transaction.addToTransaction(engine.Insert("auditorium", new HashMap<>() {{
                    put("size", Integer.toString(23));
                    put("number", finalFaculty.toString() + finalJ.toString());
                    put("faculty_id", finalFaculty.toString());
                }}));
            }
        }
        transaction.execute();
    }
    public void employe() {
        Transaction transaction =engine.createTransaction();
    }
}
