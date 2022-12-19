package com.example.university_course_work.university;

import com.example.university_course_work.database.DatabaseEngine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.AllArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
public class University {
    DatabaseEngine engin;
    public ArrayList<String> GenerateGroups(int count, int faculty) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            list.add("M" + faculty + "O-" + i);
        }
        return list;
    }

    public Optional<TableView<Student>> ViewStudents(String firstName) {
        Optional<ResultSet> resultSet = engin.SelectStudentsByFirstname(firstName);
        if (resultSet.isPresent()) {
            try {
                ObservableList<Student> students = FXCollections.observableArrayList();
                while (resultSet.get().next()){
                    Student student = Student.builder()
                                    .name(resultSet.get().getString("name"))
                                    .group(resultSet.get().getString("group"))
                                    .avgEstimation(resultSet.get().getDouble("avg"))
                                    .build();
                    students.add(student);
                }
                if (students.size() == 0)
                    new MyAlert("Students whis name: " + firstName + "not found" );
                TableView<Student> table = new TableView<Student>(students);

                TableColumn<Student, String> nameColumn = new TableColumn<Student, String>("name");
                nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
                table.getColumns().add(nameColumn);

                TableColumn<Student, String> groupColumn = new TableColumn<Student, String>("group");
                groupColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("group"));
                table.getColumns().add(groupColumn);

                TableColumn<Student, Double> avgColumn = new TableColumn<Student, Double>("avgEstimation");
                avgColumn.setCellValueFactory(new PropertyValueFactory<Student, Double>("avgEstimation"));
                table.getColumns().add(avgColumn);

                return Optional.of(table);
            }
            catch (SQLException e) {
                System.err.println(e.getMessage());
                new MyAlert("Error SQL Select");
            }
        }
        return Optional.empty();
    }
    public Optional<TableView<Schedule>> ViewScheduleForGroup(String group) {
        Optional<ResultSet> resultSet = engin.SelectScheduleForGroup(group);
        if (resultSet.isPresent()) {
            try {
                ObservableList<Schedule> list = FXCollections.observableArrayList();
                while (resultSet.get().next()){
                    Schedule schedule = Schedule.builder()
                            .name(resultSet.get().getString("name"))
                            .date(resultSet.get().getDate("eventdate"))
                            .subject(resultSet.get().getString("subject"))
                            .build();
                    list.add(schedule);
                }
                if (list.size() == 0)
                    new MyAlert("Data in group: " + group + " not found" );

                TableView<Schedule> table = new TableView<Schedule>(list);

                TableColumn<Schedule, String> nameColumn = new TableColumn<Schedule, String>("name");
                nameColumn.setCellValueFactory(new PropertyValueFactory<Schedule, String>("name"));
                table.getColumns().add(nameColumn);

                TableColumn<Schedule, String> groupColumn = new TableColumn<Schedule, String>("date");
                groupColumn.setCellValueFactory(new PropertyValueFactory<Schedule, String>("date"));
                table.getColumns().add(groupColumn);

                TableColumn<Schedule, Double> avgColumn = new TableColumn<Schedule, Double>("subject");
                avgColumn.setCellValueFactory(new PropertyValueFactory<Schedule, Double>("subject"));
                table.getColumns().add(avgColumn);

                return Optional.of(table);
            }
            catch (SQLException e) {
                System.err.println(e.getMessage());
                new MyAlert("Error SQL Select");
            }
        }
        return Optional.empty();
    }
    public Optional<TableView<Payment>> ViewPayment(int minPayment) {
        Optional<ResultSet> resultSet = engin.SelectPayment(minPayment);
        if (resultSet.isPresent()) {
            try {
                ObservableList<Payment> list = FXCollections.observableArrayList();
                while (resultSet.get().next()){
                    Payment schedule = Payment.builder()
                            .name(resultSet.get().getString("name"))
                            .sum(resultSet.get().getInt("payment"))
                            .build();
                    list.add(schedule);
                }
                if (list.size() == 0)
                    new MyAlert("Payment not found");

                TableView<Payment> table = new TableView<Payment>(list);

                TableColumn<Payment, String> nameColumn = new TableColumn<Payment, String>("Name");
                nameColumn.setCellValueFactory(new PropertyValueFactory<Payment, String>("name"));
                table.getColumns().add(nameColumn);

                TableColumn<Payment, String> groupColumn = new TableColumn<Payment, String>("Sum payment");
                groupColumn.setCellValueFactory(new PropertyValueFactory<Payment, String>("sum"));
                table.getColumns().add(groupColumn);

                return Optional.of(table);
            }
            catch (SQLException e) {
                System.err.println(e.getMessage());
                new MyAlert("Error SQL Select");
            }
        }
        return Optional.empty();
    }

    public Optional<TableView<StudentJournal>> ViewStudentsJournal() {
        Optional<ResultSet> resultSet = engin.SelectStudentsJournal();
        if (resultSet.isPresent()) {
            try {
                ObservableList<StudentJournal> list = FXCollections.observableArrayList();
                while (resultSet.get().next()){
                    StudentJournal studentItem = StudentJournal.builder()
                            .student_id(resultSet.get().getInt("student_id"))
                            .stamp(resultSet.get().getDate("stamp"))
                            .student_name(resultSet.get().getString("student_name"))
                            .operation(resultSet.get().getString("th_op"))
                            .build();
                    list.add(studentItem);
                }
                if (list.size() == 0)
                    new MyAlert("Journal is empty");

                TableView<StudentJournal> table = new TableView<StudentJournal>(list);

                TableColumn<StudentJournal, Integer> nameColumn = new TableColumn<StudentJournal, Integer>("student_id");
                nameColumn.setCellValueFactory(new PropertyValueFactory<StudentJournal, Integer>("student_id"));
                table.getColumns().add(nameColumn);

                TableColumn<StudentJournal, String> groupColumn = new TableColumn<StudentJournal, String>("Stamp");
                groupColumn.setCellValueFactory(new PropertyValueFactory<StudentJournal, String>("stamp"));
                table.getColumns().add(groupColumn);

                TableColumn<StudentJournal, String> column3 = new TableColumn<StudentJournal, String>("student_name");
                column3.setCellValueFactory(new PropertyValueFactory<StudentJournal, String>("student_name"));
                table.getColumns().add(column3);

                TableColumn<StudentJournal, String> column4 = new TableColumn<StudentJournal, String>("operation");
                column4.setCellValueFactory(new PropertyValueFactory<StudentJournal, String>("operation"));
                table.getColumns().add(column4);
                return Optional.of(table);
            }
            catch (SQLException e) {
                System.err.println(e.getMessage());
                new MyAlert("Error SQL Select");
            }
        }
        return Optional.empty();
    }
    public Optional<TableView<Department>> ViewDepartment(int limit) {
        Optional<ResultSet> resultSet = engin.SelectDepartment(limit);
        if (resultSet.isPresent()) {
            try {
                ObservableList<Department> list = FXCollections.observableArrayList();
                while (resultSet.get().next()){
                    Department department = Department.builder()
                            .name(resultSet.get().getString("name"))
                            .number(resultSet.get().getInt("number"))
                            .count(resultSet.get().getInt("count"))
                            .build();
                    list.add(department);
                }

                if (list.size() == 0)
                    new MyAlert("Departments not found");

                TableView<Department> table = new TableView<Department>(list);

                TableColumn<Department, String> column1 = new TableColumn<Department, String>("name");
                column1.setCellValueFactory(new PropertyValueFactory<Department, String>("name"));
                table.getColumns().add(column1);

                TableColumn<Department, Integer> column2 = new TableColumn<Department, Integer>("number");
                column2.setCellValueFactory(new PropertyValueFactory<Department, Integer>("number"));
                table.getColumns().add(column2);

                TableColumn<Department, Integer> column3 = new TableColumn<Department, Integer>("count");
                column3.setCellValueFactory(new PropertyValueFactory<Department, Integer>("count"));
                table.getColumns().add(column3);

                return Optional.of(table);
            }
            catch (SQLException e) {
                System.err.println(e.getMessage());
                new MyAlert("Error SQL Select");
            }
        }
        return Optional.empty();
    }
    public Optional<TableView<Expense>> ViewExpense() {
        Optional<ResultSet> resultSet = engin.SelectExpenses();
        if (resultSet.isPresent()) {
            try {
                ObservableList<Expense> list = FXCollections.observableArrayList();
                while (resultSet.get().next()){
                    Expense expense = Expense.builder()
                            .name(resultSet.get().getString("name"))
                            .expenses(resultSet.get().getInt("expenses"))
                            .build();
                    list.add(expense);
                }
                if (list.size() == 0)
                    new MyAlert("Expenses is null");

                TableView<Expense> table = new TableView<Expense>(list);

                TableColumn<Expense, String> column1 = new TableColumn<Expense, String>("name");
                column1.setCellValueFactory(new PropertyValueFactory<Expense, String>("name"));
                table.getColumns().add(column1);

                TableColumn<Expense, Integer> column2 = new TableColumn<Expense, Integer>("expenses");
                column2.setCellValueFactory(new PropertyValueFactory<Expense, Integer>("expenses"));
                table.getColumns().add(column2);

                return Optional.of(table);
            }
            catch (SQLException e) {
                System.err.println(e.getMessage());
                new MyAlert("Error SQL Select");
            }
        }
        return Optional.empty();
    }
}
