package ru.vinogradov.springproject1.models;

import javax.validation.constraints.*;

public class Person {
    private int id;

    @NotEmpty(message = "Name should be not empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;

    @Min(value = 1900, message = "Should be more than 1900")
    private int year_of_birth;

    public Person() {}

    public Person(int id, String name, int year_of_birth) {
        this.id = id;
        this.name = name;
        this.year_of_birth = year_of_birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(int year_of_birth) {
        this.year_of_birth = year_of_birth;
    }
}
