package com.example.university_course_work.database.types;

import lombok.Getter;

import java.util.Optional;

public class Column {
    @Getter
    Type type;
    @Getter
    String name;

    Reference reference;
    public Column(Type type, String name, Reference reference) {
        this.type = type;
        this.name = name;
        this.reference = reference;
    }
    public Column(Type type, String name) {
        this.type = type;
        this.name = name;
        this.reference = null;
    }

    public boolean HasReference() { return !(reference == null); }
    public Optional<Reference> getReference() { return Optional.ofNullable(reference); }
}
