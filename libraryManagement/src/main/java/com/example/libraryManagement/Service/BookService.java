package com.example.libraryManagement.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.libraryManagement.Entity.Book;
import com.example.libraryManagement.Repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private  BookRepository repository;
	
	 private static final Logger logger = LoggerFactory.getLogger(BookService.class);
	

    public Book saveBook(String username,Book book) {
    	if (!userService.isAdmin(username)) {
            logger.warn("Unauthorized attempt to add book by user: {}", username);
            throw new RuntimeException("Only ADMIN can add books");
        }
    	 logger.info("Saving book {}", book.getTitle());
        return repository.save(book);
    }

    public List<Book> getAllBooks() {
    	logger.info(" Fetching all books from database");
        return repository.findAll();
    }

    public Book getBookById(Long id) {
    	 logger.info("Looking for book with ID {}", id);
        return repository.findById(id).orElse(null);
    }

    public Book updateBook(String username,Long id, Book updatedBook) {
    	if (!userService.isAdmin(username)) {
            logger.warn("Unauthorized attempt to update book by user: {}", username);
            throw new RuntimeException("Only ADMIN can update books");
        }
    	logger.info("Updating book with ID {}", id);
        return repository.findById(id)
                .map(existing -> {
                    existing.setTitle(updatedBook.getTitle());
                    existing.setAuthor(updatedBook.getAuthor());
                    existing.setCopies(updatedBook.getCopies());
                    return repository.save(existing);
                })
                .orElse(null);
    }

    public void deleteBook(String username,Long id) {
    	if (!userService.isAdmin(username)) {
            logger.warn("Unauthorized attempt to delete book by user: {}", username);
            throw new RuntimeException("Only ADMIN can delete books");
        }
    	logger.info(" Deleting book with ID {}", id);
        repository.deleteById(id);
    }
}
