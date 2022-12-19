package com.example.university_course_work.university;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
public class MyAlert {
    public MyAlert(String text) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText(text);
        alert.showAndWait();
    }
}
