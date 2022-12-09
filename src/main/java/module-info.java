module com.example.university_course_work {
//    requires org.mapstruct.processor;
    requires static lombok;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.university_course_work to javafx.fxml;
    exports com.example.university_course_work;
    exports com.example.university_course_work.university;
    opens com.example.university_course_work.university to javafx.fxml;
}