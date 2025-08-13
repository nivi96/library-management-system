package com.example.libraryManagement.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.libraryManagement.Entity.Book;
import com.example.libraryManagement.Repository.BookRepository;

@SpringBootTest
public class BookServiceTest {
	
	@Mock
	  private BookRepository bookRepository;
	  
	 @Mock
	    private UserService userService;
	 
	  @InjectMocks
	    private BookService bookService;
	    
	  
	  private static final Logger logger = LoggerFactory.getLogger(BookService.class);


	    @Test
	    void testGetAllBooks() {
	        
	    	logger.info("Starting test: testGetAllBooks");
	        Book book = new Book();
	        book.setId(1L);
	        book.setTitle("Test Book");
	        
	        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));
	        
	        assertEquals(1, bookService.getAllBooks().size());
	        assertEquals("Test Book", bookService.getAllBooks().get(0).getTitle());
	        logger.info("Completed test: testGetAllBooks");

	    }

	    @Test
	    void testGetBookById() {
	    	
	    	logger.info("Starting test: testGetBookById");
	        Book book = new Book();
	        book.setId(1L);
	        book.setTitle("Sample Book");

	        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

	        Book found = bookService.getBookById(1L);
	        assertNotNull(found);
	        assertEquals("Sample Book", found.getTitle());
             
	        logger.info("Completed test: testGetBookById");
	        
	    }

	    @Test
	    void testSaveBook() {
	    	
	    	logger.info("Starting test: testSaveBook");
	    	
	        Book book = new Book();
	        book.setTitle("New Book");
	        String username = "admin";
	        when(userService.isAdmin(username)).thenReturn(true);
	        when(bookRepository.save(book)).thenReturn(book);
	        Book saved = bookService.saveBook(username,book);	      
	        assertEquals("New Book", saved.getTitle());
	        
	        logger.info("Completed test: testSaveBook");

	        
	    }

	    @Test
	    void testDeleteBook() {
	    	
	    	logger.info("Starting test: testDeleteBook");
	    	String username = "user"; 
	        when(userService.isAdmin(username)).thenReturn(false);

	        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	            bookService.deleteBook(username, 1L);
	        });

	        assertEquals("Only ADMIN can delete books", exception.getMessage());
	        logger.info("Completed test: testDeleteBook");
	        
	    }
	    
	    
}
