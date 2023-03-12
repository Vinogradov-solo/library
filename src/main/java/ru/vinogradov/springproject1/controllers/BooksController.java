package ru.vinogradov.springproject1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vinogradov.springproject1.dao.BookDAO;
import ru.vinogradov.springproject1.dao.PersonDAO;
import ru.vinogradov.springproject1.models.Book;
import ru.vinogradov.springproject1.models.Person;
import ru.vinogradov.springproject1.util.BookValidator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;
    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person) {
        model.addAttribute("book", bookDAO.show(id));

        Optional<Person> bookOwner = bookDAO.getBookOwner(id);

        if(bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        }
        else
            model.addAttribute("people", personDAO.index());

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book")Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("books") @Valid Book book,
                         BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "/books/new";

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("person") @Valid Person person,
//                         BindingResult bindingResult,
//                         @PathVariable("id") int id) {
//        personValidator.validate(person, bindingResult);
//        if (bindingResult.hasErrors())
//            return "/people/edit";
//
//        personDAO.update(id, person);
//        return "redirect:/people";
//    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDAO.releaseBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookDAO.provideBook(id, selectedPerson);
        return "redirect:/books/" + id;
    }
}
