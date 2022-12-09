package com.example.university_course_work.database.types;

public enum Type { VARCHAR("character varying"), INTEGER("integer"), TEXT("text"),
                    DATE("date");

    Reference reference;
    String name;

    Type(String str) {
        name = str;
        reference = null;
    }
    public static Type convertFromString(String str) {
        return switch (str) {
            case "character varying" -> Type.VARCHAR;
            case "integer", "int" -> Type.INTEGER;
            case "text" -> Type.TEXT;
            case "date" -> Type.DATE;
            default -> null;
        };
    }
    public boolean isString() {
        return !name.equals("integer");
    }
    @Override
    public String toString() {
        return name;
    }
    public void setName(String nn) { name = nn; }
}
