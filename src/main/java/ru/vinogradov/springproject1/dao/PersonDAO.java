package ru.vinogradov.springproject1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vinogradov.springproject1.models.Book;
import ru.vinogradov.springproject1.models.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
       return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public Optional<Person> show(String name) {
        return jdbcTemplate.query("SELECT * FROM person WHERE name = ?", new Object[]{name}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(name, year_of_birth) VALUES (?, ?)",
                person.getName(), person.getYear_of_birth());
    }

    public void update(int id, Person updatedPerson) {
       jdbcTemplate.update("UPDATE person SET name = ?, year_of_birth = ? WHERE id = ?",
               updatedPerson.getName(), updatedPerson.getYear_of_birth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }
}
