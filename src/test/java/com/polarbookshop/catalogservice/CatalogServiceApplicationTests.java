package com.polarbookshop.catalogservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.polarbookshop.catalogservice.domain.Book;

@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatalogServiceApplicationTests {
	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	void whenPostRequestThenBookCreated() {
		var expectedBook = Book.of("1231231231", "Title", "Author", 9.90);
		
		webTestClient
			.post()
			.uri("/books")
			.bodyValue(expectedBook)
			.exchange()
			.expectStatus().isCreated() // 201
			.expectBody(Book.class).value(actualBook -> {
				assertNotNull(actualBook);
				assertEquals(actualBook.isbn(), expectedBook.isbn());
			});
	}
}
