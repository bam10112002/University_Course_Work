package com.example.university_course_work.database;

import lombok.NonNull;
import com.example.university_course_work.database.types.Column;
import com.example.university_course_work.database.types.Reference;
import com.example.university_course_work.database.types.Type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MetaData {
    Map<String, HashMap<String, Column>> tables;

    MetaData(@NonNull ResultSet tablesSet, @NonNull ResultSet reference) throws SQLException {
        Map<String, Reference> refMap = new HashMap<>();
        tables = new HashMap<>();
        while (reference.next()) {
            Reference ref = new Reference(reference.getString("f_table"),
                                            reference.getString("f_column")
                                            .replaceAll("[{}]", ""));
            refMap.put(reference.getString("p_table") + reference.getString("p_column")
                    .replaceAll("[{}]", ""), ref);
        }

        while (tablesSet.next()) {
            tablesSet.getString("table_name");
            tablesSet.getString("column_name");
            tablesSet.getString("data_type");

            if (!tables.containsKey(tablesSet.getString("table_name")))
                tables.put(tablesSet.getString("table_name"), new HashMap<>());

            Column column;
            if (refMap.containsKey(tablesSet.getString("table_name") +
                                tablesSet.getString("column_name"))) {
                column = new Column(Type.convertFromString(tablesSet.getString("data_type")),
                                        tablesSet.getString("column_name"),
                                        refMap.get(tablesSet.getString("table_name") +
                                                tablesSet.getString("column_name")));
            }
            else {
                column = new Column(Type.convertFromString(tablesSet.getString("data_type")),
                        tablesSet.getString("column_name"));
            }
            tables.get(tablesSet.getString("table_name"))
                    .put(tablesSet.getString("column_name"), column);

        }
    }

    public Set<String> getTablesName() {
        return tables.keySet();
    }
    public Set<String> getColumnsName(String tableName) {
        return tables.get(tableName).keySet();
    }
    public Type getColumnType(String tableName, String columnName) {
       return tables.get(tableName).get(columnName).getType();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String table : tables.keySet()) {
            builder.append(table);
            builder.append("\n");
            for (String column : tables.get(table).keySet()) {
                builder.append("\t");
                builder.append(column);
                builder.append(" : ");
                builder.append(tables.get(table).get(column).getType());
                if (tables.get(table).get(column).HasReference()) {
                    builder.append(" Ref: ");
                    builder.append(tables.get(table).get(column).getReference().get().getTable());
                    builder.append("{");
                    builder.append(tables.get(table).get(column).getReference().get().getColumn());
                    builder.append("}");
                }
                builder.append("\n");
            }
        }
        return builder.toString();
    }
}
