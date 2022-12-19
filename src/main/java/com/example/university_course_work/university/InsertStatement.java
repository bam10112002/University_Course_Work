package com.example.university_course_work.university;

import com.example.university_course_work.database.DatabaseEngine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lombok.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

public class InsertStatement {
    AnchorPane root;
    TextField estimation;
    Button execute;
    ComboBox<String> studentsBox;
    Label studentsLabel;
    ComboBox<String> employeesBox;
    Label employeesLabel;
    DatabaseEngine engine;
    public InsertStatement(@NonNull AnchorPane root,@NonNull DatabaseEngine engine) throws SQLException {
        this.root = root;
        this.engine = engine;
        studentsLabel = new Label("Student name");
        studentsLabel.setTranslateX(200);
        studentsLabel.setTranslateY(20);
        employeesLabel = new Label("Employee name");
        employeesLabel.setTranslateX(200);
        employeesLabel.setTranslateY(50);


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
        studentsBox.setEditable(true);
        new AutoCompleteComboBoxListener<>(studentsBox);
        employeesBox = new ComboBox<String>(employees);
        employeesBox.setEditable(true);
        new AutoCompleteComboBoxListener<>(employeesBox);

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
                new MyAlert("Pleas input date in all field");
                throw new RuntimeException(e);
            }
        });
    }

    public void move(int x, int y) {
        studentsBox.setTranslateX(studentsBox.getTranslateX() + x);
        employeesBox.setTranslateX(employeesBox.getTranslateX() + x);
        estimation.setTranslateX(estimation.getTranslateX() + x);
        studentsLabel.setTranslateX(studentsLabel.getTranslateX() + x);
        employeesLabel.setTranslateX(employeesLabel.getTranslateX() + x);
        execute.setTranslateX(execute.getTranslateX() + x);

        studentsBox.setTranslateY(studentsBox.getTranslateY() + y);
        employeesBox.setTranslateY(employeesBox.getTranslateY() + y);
        estimation.setTranslateY(estimation.getTranslateY() + y);
        studentsLabel.setTranslateY(studentsLabel.getTranslateY() + y);
        employeesLabel.setTranslateY(employeesLabel.getTranslateY() + y);
        execute.setTranslateY(execute.getTranslateY() + y);
    }
    public void hide() {
        root.getChildren().removeAll(studentsBox, employeesBox, execute, estimation, studentsLabel, employeesLabel);
    }
    public void show() {
        root.getChildren().addAll(studentsBox, employeesBox, execute, estimation, studentsLabel, employeesLabel);
    }

    public void insertData(@NonNull DatabaseEngine engin) throws SQLException {
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
        new MyAlert("Data add to table");
    }
}

class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {

    private ComboBox<T> comboBox;
    private ObservableList<T> data;
    private boolean moveCaretToPos = false;
    private int caretPos;

    public AutoCompleteComboBoxListener(final ComboBox<T> comboBox) {
        this.comboBox = comboBox;
        data = comboBox.getItems();

        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                comboBox.hide();
            }
        });
        this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
    }

    @Override
    public void handle(KeyEvent event) {

        if(event.getCode() == KeyCode.UP) {
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if(event.getCode() == KeyCode.DOWN) {
            if(!comboBox.isShowing()) {
                comboBox.show();
            }
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if(event.getCode() == KeyCode.BACK_SPACE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        } else if(event.getCode() == KeyCode.DELETE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        }

        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                || event.isControlDown() || event.getCode() == KeyCode.HOME
                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
            return;
        }

        ObservableList<T> list = FXCollections.observableArrayList();
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).toString().toLowerCase().startsWith(
                    //AutoCompleteComboBoxListener.this.comboBox.getEditor().getText().toLowerCase().trim())) {
                    comboBox.getEditor().getText().toLowerCase().trim())) {
                list.add(data.get(i));
            }
        }
        String t = comboBox.getEditor().getText();

        comboBox.setItems(list);
        //comboBox.getEditor().setText(t);
        if(!moveCaretToPos) {
            caretPos = -1;
        }
        moveCaret(t.length());
        if(!list.isEmpty()) {
            comboBox.show();
        }
    }

    private void moveCaret(int textLength) {
        if(caretPos == -1) {
            comboBox.getEditor().positionCaret(textLength);
        } else {
            comboBox.getEditor().positionCaret(caretPos);
        }
        moveCaretToPos = false;
    }

    public static<T> T getComboBoxValue(ComboBox<T> comboBox){
        if (comboBox.getSelectionModel().getSelectedIndex() < 0) {
            return null;
        } else {
            return comboBox.getItems().get(comboBox.getSelectionModel().getSelectedIndex());
        }
    }
}