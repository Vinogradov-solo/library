package ru.vinogradov.springproject1.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.vinogradov.springproject1.dao.BookDAO;
import ru.vinogradov.springproject1.dao.PersonDAO;
import ru.vinogradov.springproject1.models.Book;
import ru.vinogradov.springproject1.models.Person;

@Component
public class BookValidator implements Validator
{
    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;   //делаем даункаст, приводим к персон
        // так как ТОЧНО знем для какого класса валидатор

        // посмотреть есть ли человек с таким же именем в базе данных
        if(bookDAO.show(book.getName()).isPresent()) {
            errors.rejectValue("name", "", "This name is already taken");
        }

        if(bookDAO.show(book.getAuthor()).isPresent()) {
            errors.rejectValue("author", "", "This author is already taken");
        }
    }
}
