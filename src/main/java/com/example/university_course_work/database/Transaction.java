package com.example.university_course_work.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayDeque;

public class Transaction {
    ArrayDeque<PreparedStatement> que = new ArrayDeque<>();
    Connection connection;
    Transaction(Connection connection) {
        this.connection = connection;
    }

    public void addToTransaction(PreparedStatement statement) {
        que.push(statement);
    }
    public boolean execute() {
        try {
            connection.setAutoCommit(false);
            connection.commit();
            while (!que.isEmpty()){
                que.getFirst().executeUpdate();
            }
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex2) {
                System.err.println(ex2.getMessage());
                return false;
            }
            System.err.println(ex.getMessage());
            return false;
        }
    }
}
