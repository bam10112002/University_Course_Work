module com.example.university_course_work {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.university_course_work to javafx.fxml;
    exports com.example.university_course_work;
}