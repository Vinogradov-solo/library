package ru.vinogradov.springproject1.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.vinogradov.springproject1.dao.PersonDAO;
import ru.vinogradov.springproject1.models.Person;

@Component
public class PersonValidator implements Validator
{
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;   //делаем даункаст, приводим к персон
        // так как ТОЧНО знем для какого класса валидатор

        // посмотреть есть ли человек с таким же мейлом в базе данных
        if(personDAO.show(person.getName()).isPresent()) {
            errors.rejectValue("name", "", "This name is already taken");
        }
    }
}
