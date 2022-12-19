package com.example.university_course_work.database;

import lombok.Getter;

import java.sql.*;
import java.util.HashMap;
import java.util.Optional;

public class DatabaseEngine {
    Connection con;
    @Getter
    MetaData metaData;
    public DatabaseEngine(String url, String user, String pass) {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db", "admin", "root");
            updateMetaData();
        }
        catch (ClassNotFoundException | SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public Optional<ResultSet> SelectStudentsByFirstname(String name) {
        try {
            PreparedStatement st = con.prepareStatement("""
                SELECT concat(firstname,' ', lastname) as name, st_group.name as group, avg(statement.estimation)
                FROM student, st_group, statement
                    where  student.group_id = st_group.id and statement.student_id = student.id and lastname like ?
                GROUP BY st_group.name, firstname, lastname;
                """);
            st.setString(1,name+'%');
            ResultSet set = st.executeQuery();
            return Optional.ofNullable(set);

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return Optional.empty();
        }

    }
    public Optional<ResultSet> SelectScheduleForGroup(String groupName) {
        try {
            PreparedStatement st = con.prepareStatement("""
                SELECT concat(employee.lastname, ' ', employee.firstname) AS name,
                       schedule.eventdate,
                       subject.name AS subject
                FROM st_group, schedule, employee, subject
                WHERE schedule.group_id = st_group.id and employee_id = employee.id
                       and subject_id = subject.id and st_group.name = ?;
                 """);
            st.setString(1,groupName);
            return Optional.ofNullable(st.executeQuery());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }
    public Optional<ResultSet> SelectPayment(int payment) {
        try {
            PreparedStatement st = con.prepareStatement("""
                   SELECT name, payment
                   FROM (SELECT concat(lastname, ' ', firstname) AS name, sum(payment.sun) AS payment\s
                         FROM payment, student
                         WHERE student.id = payment.student_id
                   GROUP BY lastname, firstname) AS tt
                   WHERE payment > ?;
                    """);
            st.setInt(1, payment);
            return Optional.ofNullable(st.executeQuery());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }
    public Optional<ResultSet> SelectStudentsJournal() {
        try {
            Statement st = con.createStatement();
            return Optional.ofNullable(st.executeQuery("""
                   SELECT student_id, student_name, stamp, th_op from journal_students;
                    """));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }
    public Optional<ResultSet> SelectDepartment(int limit) {
        try {
            PreparedStatement st = con.prepareStatement("""
                SELECT * from (SELECT department.name, department.number, count(employee.id) as count
                from department, employee
                where employee.department_id = department.id
                group by department.name, department.number) as tt
                order by count DESC
                limit ?;
             """);
            st.setInt(1, limit);
            return Optional.ofNullable(st.executeQuery());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }
    public Optional<ResultSet> SelectExpenses() {
        try {
            Statement st = con.createStatement();
            return Optional.ofNullable(st.executeQuery("""
                    SELECT faculty.name as name, sum(sun) as expenses
                    from (SELECT DISTINCT faculty_id, payment.sun from st_group, student, payment
                            WHERE payment.student_id = student.id and student.group_id = st_group.id
                                    and st_group.faculty_id = faculty_id) as tt, faculty
                    WHERE tt.faculty_id = faculty.id
                    group by faculty.name;
                    """));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }

    public PreparedStatement Insert(String tableName, HashMap<String, String> map) {
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
        sqlBuilder.append(tableName);

        sqlBuilder.append(" (");
        for (String str : map.keySet()) {
            sqlBuilder.append(str);
            sqlBuilder.append(",");
        }
        sqlBuilder.delete(sqlBuilder.length()-1, sqlBuilder.length());
        sqlBuilder.append(")");

        sqlBuilder.append(" VALUES(");
        for (String column : map.keySet()) {
            if (metaData.getColumnType(tableName, column).isString()) {
                sqlBuilder.append("'");
                sqlBuilder.append(map.get(column));
                sqlBuilder.append("'");
            }
            else {
                sqlBuilder.append(map.get(column));
            }
            sqlBuilder.append(",");
        }
        sqlBuilder.delete(sqlBuilder.length()-1, sqlBuilder.length());
        sqlBuilder.append(");");

        try {
            return con.prepareStatement(sqlBuilder.toString());
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return null;
    }
    public Optional<ResultSet> Select(String sql) {
        try {
            Statement statement = con.createStatement();
            return Optional.of(statement.executeQuery(sql));

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return Optional.empty();
        }
    }
    private void updateMetaData() {
        try {
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            ResultSet columns = st.executeQuery("""
                select table_name, column_name, data_type
                from information_schema.columns
                where table_schema='public' 
                order by table_name;
                """);
            ResultSet references = st2.executeQuery("""
                SELECT
                    y.table_name    AS f_table,
                    array_agg(y.column_name::text) AS f_column,
                    x.table_name    AS p_table,
                    array_agg(x.column_name::text) AS p_column
                FROM information_schema.referential_constraints c
                JOIN information_schema.key_column_usage x
                    ON x.constraint_name = c.constraint_name
                    AND x.constraint_schema = c.constraint_schema
                JOIN information_schema.key_column_usage y
                    ON y.ordinal_position = x.position_in_unique_constraint
                    AND y.constraint_schema = c.unique_constraint_schema
                    AND y.constraint_name = c.unique_constraint_name
                GROUP BY f_table, x.table_name
                ORDER BY f_table, f_column;
                """);
            metaData = new MetaData(columns, references);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Transaction createTransaction() {
        return new Transaction(con);
    }
}
