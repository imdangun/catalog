package com.polarbookshop.catalogservice.demo;

import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;

@Component
@Profile("testdata")
public class BookDataLoader {
	private final BookRepository bookRepository;
	
	public BookDataLoader(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void loadBookTestData() {
		bookRepository.deleteAll();
		var book1 = Book.of("1234567891", "Nothern Lights", "Lyra", 9.90);
		var book2 = Book.of("1234567892", "Polar Journey", "Iorek", 12.90);
		bookRepository.saveAll(List.of(book1, book2));	
	}
}
