package com.example.university_course_work.university;

import com.example.university_course_work.database.DatabaseEngin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

public class InsertEstimation {
    AnchorPane root;
    TextField estimation;
    Button execute;
    ComboBox<String> studentsBox;
    ComboBox<String> employeesBox;
    DatabaseEngin engine;
    public InsertEstimation(AnchorPane root, DatabaseEngin engine) throws SQLException {
        this.root = root;
        this.engine = engine;
        ObservableList<String> students = FXCollections.observableArrayList();
        Optional<ResultSet> studentSet = engine.Select("SELECT lastname FROM student");
        if (studentSet.isEmpty()) {
            new MyAlert("ERROR in 'SELECT lastname FROM student'");
            return;
        }
        while (studentSet.get().next()) {
            students.add(studentSet.get().getString("lastname"));
        }

        ObservableList<String> employees = FXCollections.observableArrayList();
        Optional<ResultSet> employeeSet = engine.Select("SELECT lastname FROM employee");
        if (employeeSet.isEmpty()) {
            new MyAlert("ERROR in 'SELECT lastname FROM employee'");
            return;
        }
        while (employeeSet.get().next()) {
            employees.add(employeeSet.get().getString("lastname"));
        }

        studentsBox = new ComboBox<String>(students);
        employeesBox = new ComboBox<String>(employees);

        studentsBox.setTranslateY(20);
        employeesBox.setTranslateY(50);

        estimation = new TextField("4");
        estimation.setTranslateY(100);

        execute = new Button();
        execute.setText("Execute");
        execute.setTranslateY(130);
        execute.setTranslateX(50);
        execute.setOnAction(actionEvent -> {
            try {
                this.insertData(engine);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void move(int x, int y) {
        studentsBox.setTranslateX(studentsBox.getTranslateX() + x);
        employeesBox.setTranslateX(employeesBox.getTranslateX() + x);
        estimation.setTranslateX(estimation.getTranslateX() + x);

        studentsBox.setTranslateY(studentsBox.getTranslateY() + y);
        employeesBox.setTranslateY(employeesBox.getTranslateY() + y);
        estimation.setTranslateY(estimation.getTranslateY() + y);
    }
    public void hide() {
        root.getChildren().removeAll(studentsBox, employeesBox, execute, estimation);
    }
    public void show() {
        root.getChildren().addAll(studentsBox, employeesBox, execute, estimation);
    }

    public void insertData(@NonNull DatabaseEngin engin) throws SQLException {
        Optional<ResultSet> studentSet = engine.Select("SELECT id FROM student WHERE lastname ='" +
                studentsBox.getValue() + "';");
        Integer studentId = null;
        if (studentSet.isPresent()) {
            studentSet.get().next();
            studentId = studentSet.get().getInt("id");
        }
        Optional<ResultSet> employeeSet = engine.Select("SELECT id FROM employee WHERE lastname ='" +
                employeesBox.getValue() + "';");
        Integer employeeId = null;
        if (employeeSet.isPresent()) {
            employeeSet.get().next();
            employeeId = employeeSet.get().getInt("id");
        }

        Integer finalStudentId = studentId;
        Integer finalEmployeeId = employeeId;
        engin.Insert("statement", new HashMap<>(){{
            put("student_id", finalStudentId.toString());
            put("employee_id", finalEmployeeId.toString());
            put("estimation", estimation.getText());
        }}).executeUpdate();
    }
}
