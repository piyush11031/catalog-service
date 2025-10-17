package com.polarbookshop.catalogservice;


import com.polarbookshop.catalogservice.domain.Book;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BookValidationTests {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreCorrect(){
        var book = new Book("1234567890", "Polar Book", "A book about polar bears", 10.0);
        Set<ConstraintViolation<Book>> voilation = validator.validate(book);
        assertThat(voilation).isEmpty();
    }

    @Test
    void whenIsbnDefinedButIncorrectThenValidationFails(){
        var book = new Book("a234567890", "Title", "Author", 10.0);
        Set<ConstraintViolation<Book>> validate = validator.validate(book);
        assertThat(validate).hasSize(1);
        assertThat(validate.iterator().next().getMessage()).isEqualTo("The ISBN format must be valid.");
    }
}
