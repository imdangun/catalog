package com.polarbookshop.catalogservice.domain;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class BookValidationTests {
	private static Validator validator;
	
	@BeforeAll // static method 여야 한다.
	static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	void whenAllFieldCorrectThenValidationSucceeds() {
		var book = Book.of("1234567890", "Title", "Author", 9.90);
		Set<ConstraintViolation<Book>> violations = validator.validate(book);		
		assertTrue(violations.isEmpty());
	}
	
	@Test
	void whenIsbnDefinedButIncorrectThenValidationFails() {
		var book = Book.of("a234567890", "Title", "Author", 9.90);
		Set<ConstraintViolation<Book>> violations = validator.validate(book);
		assertEquals(1, violations.size());
		assertEquals(violations.iterator().next().getMessage(),
			"The ISBN format must be valid.");
	}
}
