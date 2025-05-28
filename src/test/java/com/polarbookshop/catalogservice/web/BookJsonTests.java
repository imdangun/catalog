package com.polarbookshop.catalogservice.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polarbookshop.catalogservice.domain.Book;

@JsonTest
public class BookJsonTests {
	@Autowired
	private JacksonTester<Book> json;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Test
	void testSerialize() throws Exception {
		var book = new Book("1234567890", "Title", "Author", 9.90);
		var jsonContent = json.write(book);
		JsonNode root = objectMapper.readTree(jsonContent.getJson());

        assertEquals(book.isbn(), root.path("isbn").asText());
        assertEquals(book.title(), root.path("title").asText());
        assertEquals(book.author(), root.path("author").asText());
        assertEquals(book.price(), root.path("price").asDouble(), 0.01); 
	}
	
	@Test
	void testDeserialize() throws Exception {
		var content = """
				{
					"isbn": "1234567890",
					"title": "Title",
					"author": "Author",
					"price": 9.90
				}
				""";
		 Book book = json.parseObject(content);

        assertNotNull(book);
        assertEquals("1234567890", book.isbn());
        assertEquals("Title", book.title());
        assertEquals("Author", book.author());
        assertEquals(9.90, book.price(), 0.01);
	}
}
