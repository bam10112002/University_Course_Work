package com.example.university_course_work.university;

import com.example.university_course_work.database.DatabaseEngine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.Collection;

public class MyInsert {
    ComboBox<String> tablesCombo;
    MyInsert(DatabaseEngine engine) {
        ObservableList<String> tables = FXCollections.observableArrayList(engine.getMetaData().getTablesName());
        tablesCombo = new ComboBox<>(tables);

    }
}
