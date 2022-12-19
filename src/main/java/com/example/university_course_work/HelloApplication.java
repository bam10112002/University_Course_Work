package com.example.university_course_work;

import com.example.university_course_work.database.DatabaseEngine;
import com.example.university_course_work.university.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {

        AnchorPane root = new AnchorPane();
        root.resize(1100,700);

        TableView table = new TableView();

        AtomicInteger currentView = new AtomicInteger(0);
        DatabaseEngine engin = new DatabaseEngine("", "", "");
        University university = new University(engin);
        TextField field = new TextField();
        field.setMinWidth(700);
        field.setTranslateX(200);
        field.setTranslateY(20);
        Button update = new Button("Update");
        update.setTranslateX(930);
        update.setTranslateY(20);


        InsertStatement viewInsertStatement = new InsertStatement(root, engin);
        viewInsertStatement.move(400, 200);


        Optional<TableView<Expense>> viewExpenseOp = university.ViewExpense();
        if (viewExpenseOp.isEmpty()){
            new MyAlert("Error in expense table, check logs in console");
        }
        viewExpenseOp.get().setMinSize(1100,570);
        final TableView<Expense>[] expenseTableView = new TableView[]{viewExpenseOp.get()};
        expenseTableView[0].setTranslateY(50);


        Optional<TableView<Department>> departmentViewOp = university.ViewDepartment(2);
        if (departmentViewOp.isEmpty()){
            new MyAlert("Error in expense table, check logs in console");
        }
        departmentViewOp.get().setMinSize(1100,570);
        final TableView<Department>[] DepartmentView = new TableView[]{departmentViewOp.get()};
        DepartmentView[0].setTranslateY(50);


        Optional<TableView<Payment>> ViewPaymentOp = university.ViewPayment(15000);
        if (ViewPaymentOp.isEmpty()){
            new MyAlert("Error in expense table, check logs in console");
        }
        ViewPaymentOp.get().setMinSize(1100,570);
        final TableView<Payment>[] PaymentView = new TableView[]{ViewPaymentOp.get()};
        PaymentView[0].setTranslateY(50);


        Optional<TableView<Student>> ViewStudentsOp = university.ViewStudents("В");
        if (ViewStudentsOp.isEmpty()){
            new MyAlert("Error in expense table, check logs in console");
        }
        ViewStudentsOp.get().setMinSize(1100,570);
        final TableView<Student>[] StudentView = new TableView[]{ViewStudentsOp.get()};
        StudentView[0].setTranslateY(50);


        Optional<TableView<StudentJournal>> ViewStudentsJournalOp = university.ViewStudentsJournal();
        if (ViewStudentsJournalOp.isEmpty()){
            new MyAlert("Error in expense table, check logs in console");
        }
        ViewStudentsJournalOp.get().setMinSize(1100,570);
        final TableView<StudentJournal>[] StudentJournalView = new TableView[]{ViewStudentsJournalOp.get()};
        StudentJournalView[0].setTranslateY(50);

        Optional<TableView<Schedule>> ViewScheduleOp = university.ViewScheduleForGroup("M11O-2");
        if (ViewScheduleOp.isEmpty()){
            new MyAlert("Error in expense table, check logs in console");
        }
        ViewScheduleOp.get().setMinSize(1100,570);
        final TableView<Schedule>[] ScheduleView = new TableView[]{ViewScheduleOp.get()};
        ScheduleView[0].setTranslateY(50);

        int yTranslate = 660;
        int xTranslate = 160;
        Button studentsViewBtn = new Button("Students View");
        studentsViewBtn.setMinSize(xTranslate-30, 30);
        studentsViewBtn.setTranslateY(yTranslate);
        studentsViewBtn.setTranslateX(10);
        studentsViewBtn.setOnAction(actionEvent -> {
            if (currentView.get() != 1 && currentView.get() != 2 && currentView.get() != 3
                    && currentView.get() != 5) {
                root.getChildren().addAll(field, update);
            }
            currentView.set(1);
            root.getChildren().addAll(StudentView[0]);
            field.setPromptText("firstname");
            root.getChildren().removeAll(ScheduleView[0], PaymentView[0], StudentJournalView[0],
                    DepartmentView[0], expenseTableView[0]);
            viewInsertStatement.hide();
        });


        Button scheduleForGroupBtn = new Button("Schedule For Group");
        scheduleForGroupBtn.setMinSize(xTranslate-30, 30);
        scheduleForGroupBtn.setTranslateY(yTranslate);
        scheduleForGroupBtn.setTranslateX(xTranslate);
        scheduleForGroupBtn.setOnAction(actionEvent -> {
            if (currentView.get() != 1 && currentView.get() != 2 && currentView.get() != 3
                    && currentView.get() != 5) {
                root.getChildren().addAll(field, update);
            }

            root.getChildren().addAll(ScheduleView[0]);
            currentView.set(2);
            field.setPromptText("group name");
//            root.getChildren().addAll(ScheduleView[0], field, update);
            root.getChildren().removeAll(StudentView[0], PaymentView[0], StudentJournalView[0],
                    DepartmentView[0], expenseTableView[0]);
            viewInsertStatement.hide();
        });

        Button viewPaymentBtn = new Button("Payment");
        viewPaymentBtn.setMinSize(xTranslate-30, 30);
        viewPaymentBtn.setTranslateY(yTranslate);
        viewPaymentBtn.setTranslateX(xTranslate*2);
        viewPaymentBtn.setOnAction(actionEvent -> {
            if (currentView.get() != 1 && currentView.get() != 2 && currentView.get() != 3
                    && currentView.get() != 5) {
                root.getChildren().addAll(field, update);
            }
            currentView.set(3);
            field.setPromptText("min payment");
            root.getChildren().addAll(PaymentView[0]);
            root.getChildren().removeAll(StudentView[0], ScheduleView[0], StudentJournalView[0],
                    DepartmentView[0], expenseTableView[0]);
            viewInsertStatement.hide();
        });

        Button viewStudentsJournalBtn = new Button("Students Journal");
        viewStudentsJournalBtn.setMinSize(xTranslate-30, 30);
        viewStudentsJournalBtn.setTranslateY(yTranslate);
        viewStudentsJournalBtn.setTranslateX(xTranslate*3);
        viewStudentsJournalBtn.setOnAction(actionEvent -> {
            currentView.set(4);
            root.getChildren().addAll(StudentJournalView[0]);
            root.getChildren().removeAll(StudentView[0], ScheduleView[0], PaymentView[0],
                    DepartmentView[0], expenseTableView[0], field, update);
            viewInsertStatement.hide();
        });

        Button viewDepartmentBtn = new Button("Department");
        viewDepartmentBtn.setMinSize(xTranslate-30, 30);
        viewDepartmentBtn.setTranslateY(yTranslate);
        viewDepartmentBtn.setTranslateX(xTranslate*4);
        viewDepartmentBtn.setOnAction(actionEvent -> {
            if (currentView.get() != 1 && currentView.get() != 2 && currentView.get() != 3
                    && currentView.get() != 5) {
                root.getChildren().addAll(field, update);
            }
            currentView.set(5);
            field.setPromptText("limit");
            root.getChildren().addAll(DepartmentView[0]);
            root.getChildren().removeAll(StudentView[0], ScheduleView[0], PaymentView[0],
                    StudentJournalView[0], expenseTableView[0]);
            viewInsertStatement.hide();
        });

        Button viewExpenseBTn = new Button("Faculty expense");
        viewExpenseBTn.setMinSize(xTranslate-30, 30);
        viewExpenseBTn.setTranslateY(yTranslate);
        viewExpenseBTn.setTranslateX(xTranslate*5);
        viewExpenseBTn.setOnAction(actionEvent -> {
            currentView.set(6);
            root.getChildren().add(expenseTableView[0]);
            root.getChildren().removeAll(StudentView[0], ScheduleView[0], PaymentView[0],
                    StudentJournalView[0], DepartmentView[0], field, update);
            viewInsertStatement.hide();
        });

        Button insertStatementBtn = new Button("Insert statement");
        insertStatementBtn.setMinSize(xTranslate-30, 30);
        insertStatementBtn.setTranslateY(yTranslate);
        insertStatementBtn.setTranslateX(xTranslate*6);
        insertStatementBtn.setOnAction(actionEvent -> {
            currentView.set(7);
            root.getChildren().removeAll(StudentView[0], ScheduleView[0], PaymentView[0],
                    StudentJournalView[0], DepartmentView[0], expenseTableView[0], field, update);
            viewInsertStatement.show();
        });

        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switch (currentView.get()){
                    case 1:
                        Optional<TableView<Student>> ViewStudentsOp = university.ViewStudents(field.getText());
                        if (ViewStudentsOp.isEmpty()){
                            new MyAlert("Error in expense table, check logs in console");
                        }
                        ViewStudentsOp.get().setMinSize(1100,570);
                        root.getChildren().remove(StudentView[0]);
                        StudentView[0] = ViewStudentsOp.get();
                        StudentView[0].setTranslateY(50);
                        root.getChildren().add(StudentView[0]);
                        break;
                    case 2:
                        Optional<TableView<Schedule>> ViewScheduleOp = university.ViewScheduleForGroup(field.getText());
                        if (ViewScheduleOp.isEmpty()){
                            new MyAlert("Error in expense table, check logs in console");
                        }
                        ViewScheduleOp.get().setMinSize(1100,570);
                        root.getChildren().remove(ScheduleView[0]);
                        ScheduleView[0] = ViewScheduleOp.get();
                        ScheduleView[0].setTranslateY(50);
                        root.getChildren().add(ScheduleView[0]);
                        break;
                    case 3:
                        try {
                            Optional<TableView<Payment>> ViewPaymentOp = university.ViewPayment
                                    (Integer.parseInt(field.getText()));
                            if (ViewPaymentOp.isEmpty()){
                                new MyAlert("Error in expense table, check logs in console");
                            }
                            ViewPaymentOp.get().setMinSize(1100,570);
                            root.getChildren().remove(PaymentView[0]);
                            PaymentView[0] = ViewPaymentOp.get();
                            PaymentView[0].setTranslateY(50);
                            root.getChildren().add(PaymentView[0]);
                        } catch (NumberFormatException ex) {
                            new MyAlert("Input string must be number");
                        }
                        break;
//                    case 4:
//                        Optional<TableView<StudentJournal>> ViewStudentsJournalOp = university.ViewStudentsJournal();
//                        if (ViewStudentsJournalOp.isEmpty()){
//                            new MyAlert("Error in expense table, check logs in console");
//                        }
//                        ViewStudentsJournalOp.get().setMinSize(1100,570);
//                        root.getChildren().remove(StudentJournalView[0]);
//                        StudentJournalView[0] = ViewStudentsJournalOp.get();
//                        StudentJournalView[0].setTranslateY(50);
//                        root.getChildren().add(StudentJournalView[0]);
//                        break;
                    case 5:
                        try {
                            Optional<TableView<Department>> departmentViewOp = university.ViewDepartment
                                    (Integer.parseInt(field.getText()));
                            if (departmentViewOp.isEmpty()){
                                new MyAlert("Error in expense table, check logs in console");
                            }
                            departmentViewOp.get().setMinSize(1100,570);
                            root.getChildren().remove(DepartmentView[0]);
                            DepartmentView[0] = departmentViewOp.get();
                            DepartmentView[0].setTranslateY(50);
                            root.getChildren().add(DepartmentView[0]);
                        } catch (NumberFormatException e) {
                            new MyAlert("Input string must be number");
                        }
                        break;
//                    case 6:
//                        Optional<TableView<Expense>> viewExpenseOp2 = university.ViewExpense();
//                        if (viewExpenseOp2.isEmpty()){
//                            new MyAlert("Error in expense table, check logs in console");
//                        }
//                        viewExpenseOp2.get().setMinSize(1100,570);
//                        root.getChildren().remove(expenseTableView[0]);
//                        expenseTableView[0] = viewExpenseOp.get();
//                        expenseTableView[0].setTranslateY(50);
//                        root.getChildren().add(DepartmentView[0]);
//                        break;
                }
            }
        });


        root.getChildren().addAll(studentsViewBtn, scheduleForGroupBtn, viewPaymentBtn,
                viewStudentsJournalBtn, viewDepartmentBtn, viewExpenseBTn, insertStatementBtn);

        Scene scene = new Scene(root, 1100, 700);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}