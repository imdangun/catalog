package com.polarbookshop.catalogservice.web;

import static org.mockito.BDDMockito.given; // 직접 추가
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.polarbookshop.catalogservice.domain.BookNotFoundException;
import com.polarbookshop.catalogservice.domain.BookService;

@WebMvcTest(BookController.class)
@Import(BookControllerMvcTests.MockConfig.class)
public class BookControllerMvcTests {
	@Autowired	
	private MockMvc mockMvc;
	
	@Autowired
	private BookService bookService;
	
	@TestConfiguration
    static class MockConfig {
        @Bean
        BookService bookService() {
            return Mockito.mock(BookService.class);
        }
    }
	
	@Test
	void whenGetBookNotExistingThenShouldReturn404() throws Exception {
		String isbn = "73737313940";
		given(bookService.viewBookDetails(isbn))
			.willThrow(BookNotFoundException.class);
		mockMvc
			.perform(get("/books/" + isbn))
			.andExpect(status().isNotFound());		
	}
}
