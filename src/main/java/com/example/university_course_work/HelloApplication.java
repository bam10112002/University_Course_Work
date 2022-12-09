package com.example.university_course_work;

import com.example.university_course_work.database.DatabaseEngin;
import com.example.university_course_work.university.Expense;
import com.example.university_course_work.university.InsertEstimation;
import com.example.university_course_work.university.University;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {

        AnchorPane root = new AnchorPane();
        root.resize(600,500);

        DatabaseEngin engin = new DatabaseEngin("", "", "");
        University university = new University(engin);

        InsertEstimation insertEstimation = new InsertEstimation(root, engin);
        insertEstimation.show();


//       Optional<TableView<Expense>> table = university.ViewExpense();
//       if (!table.isPresent()){
//           throw new RuntimeException("exeption");
//       }
//       table.get().setMinSize(600,500);
//
//        root.getChildren().add(table.get());

        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}