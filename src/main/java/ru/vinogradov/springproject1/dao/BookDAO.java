package ru.vinogradov.springproject1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vinogradov.springproject1.models.Book;
import ru.vinogradov.springproject1.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public Optional<Book> show(String name) {
        return jdbcTemplate.query("SELECT * FROM book WHERE name = ?", new Object[]{name}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny();
    }
    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(name, author, year) VALUES (?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE book SET name = ?, author = ?, year = ? WHERE id = ?",
                updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    public void provideBook(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE id = ?", selectedPerson.getId(), id);
    }

    public void releaseBook(int id) {
        jdbcTemplate.update("UPDATE book SET person_id = NULL WHERE id = ?", id);
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT p.* FROM book b JOIN person p ON b.person_id = p.id " +
                "WHERE b.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}
