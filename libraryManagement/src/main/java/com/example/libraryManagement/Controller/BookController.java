package com.example.libraryManagement.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.libraryManagement.Entity.Book;
import com.example.libraryManagement.Exception.ResourceNotFoundException;
import com.example.libraryManagement.Service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {
	
	private static final Logger logger = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	private  BookService service;


	    @PostMapping
	    public ResponseEntity<?> addBook(@RequestHeader("username")  String username,@Valid @RequestBody Book book) {
	    	
	        	logger.info("Adding a new book: {}", book.getTitle());
	            Book savedBook = service.saveBook(username,book);
	            logger.debug("Book created with ID {}", savedBook.getId());
	            return ResponseEntity.ok("Book added successfully.");
	  
	    }

	    
	    @GetMapping
	    public ResponseEntity<?> getAllBooks() {
	      
	        	logger.info("Fetching all books");
	            List<Book> books = service.getAllBooks();
	            logger.debug("Found {} books", books.size());
	            return ResponseEntity.ok(books);
	        
	    }

	  
	    @GetMapping("/{id}")
	    public ResponseEntity<?> getBookById(@PathVariable Long id) {
	    	  	logger.info("Fetching book with ID {}", id);
	            Book book = service.getBookById(id);
	            if (book == null) {
	            	 logger.warn("Book with ID {} not found", id);
	            	 throw new ResourceNotFoundException("Book not found with id: " + id);
	            }
	            return ResponseEntity.ok(book);
	        }  

	    
	    @PutMapping("/{id}")
	    public ResponseEntity<?> updateBook(@PathVariable Long id,@RequestHeader("username") String username, @Valid @RequestBody Book updatedBook) {
	        	 logger.info("Updating book with ID {}", id);
	            Book book = service.updateBook(username,id, updatedBook);
	            if (book == null) {
	            	logger.warn("Cannot update. Book with ID {} not found", id);
	            	 throw new ResourceNotFoundException("Book not found with id: " + id);
	            }
	            logger.debug("Book with ID {} updated successfully", id);
	            return ResponseEntity.ok("Book with ID " + id + " updated successfully.");
	        
	        } 

	   
	    @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteBook(@RequestHeader("username") String username,@PathVariable Long id) {
	        	logger.info("Deleting book with ID {}", id);
	        	Book existingBook = service.getBookById(id);
	            if (existingBook == null) {
	            	  logger.warn("Cannot delete. Book with ID {} not found", id);
	            	  throw new ResourceNotFoundException("Book not found with id: " + id);
	            }
	            service.deleteBook(username,id);
	            logger.debug("Book with ID {} deleted successfully", id);
	            return ResponseEntity.ok("Book with ID " + id + " deleted successfully.");
	        } 
	
	
 
}
