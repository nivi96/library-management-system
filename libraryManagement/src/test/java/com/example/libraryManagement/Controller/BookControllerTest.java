package com.example.libraryManagement.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.example.libraryManagement.Controller.BookController;
import com.example.libraryManagement.Entity.Book;
import com.example.libraryManagement.Service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;
    
   
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Test
    void testGetAllBooks() throws Exception {
    	
    	logger.info("Starting test: testGetAllBooks");
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book));

        mockMvc.perform(get("/books"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].title").value("Test Book"));
        
        logger.info("Completed test: testGetAllBooks");

        
    }

    @Test
    void testGetBookById_Found() throws Exception {
    	
    	logger.info("Starting test: testGetBookById_Found");
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        when(bookService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/books/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.title").value("Test Book"));

        logger.info("Completed test: testGetBookById_Found");
    }

    @Test
    void testGetBookById_NotFound() throws Exception {
    	logger.info("Starting test: testGetBookById_NotFound");
        when(bookService.getBookById(1L)).thenReturn(null);

        mockMvc.perform(get("/books/1"))
               .andExpect(status().isNotFound())
               .andExpect(content().string("Book not found with id: 1"));

        logger.info("Completed test: testGetBookById_NotFound");
    }

    @Test
    void testAddBook() throws Exception {
    	
    	logger.info("Starting test: testGetBookById_NotFound");
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Updated Book");
        book.setAuthor("Author Name");
        book.setCopies(5);
        when(bookService.saveBook(eq("ADMIN"),any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/books/admin")
        		.contentType(MediaType.APPLICATION_JSON) )
                .andExpect(status().isOk())
               .andExpect(jsonPath("$.title").value("Updated Book"));

        logger.info("Completed test: testAddBook");
    }

    @Test
    void testUpdateBook_Found() throws Exception {
    	
    	logger.info("Starting test: testGetBookById_NotFound");
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Updated Book");
        book.setAuthor("Author Name");
        book.setCopies(5);

        when(bookService.updateBook(eq("ADMIN"),eq(1L), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/books/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.title").value("Updated Book"));

        logger.info("Completed test: testUpdateBook_Found");
    }

    @Test
    void testUpdateBook_NotFound() throws Exception {
    	
    	logger.info("Starting test: testUpdateBook_NotFound");
    	 Book updatedBook = new Book();
    	    updatedBook.setTitle("Updated Book");
    	    updatedBook.setAuthor("Author U");
    	    updatedBook.setCopies(5);
        when(bookService.updateBook(eq("ADMIN"),eq(1L), any(Book.class))).thenReturn(null);

        mockMvc.perform(put("/books/1"))
               .andExpect(status().isNotFound())
               .andExpect(content().string("Book not found with id: 1"));

        logger.info("Completed test: testUpdateBook_NotFound");
    }

    @Test
    void testDeleteBook_Found() throws Exception {
    	
    	logger.info("Starting test: testDeleteBook_Found");
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book to Delete");

        when(bookService.getBookById(1L)).thenReturn(book);
        doNothing().when(bookService).deleteBook("ADMIN",1L);

        mockMvc.perform(delete("/books/1"))
               .andExpect(status().isOk())
               .andExpect(content().string("Book with ID 1 deleted successfully."));

        logger.info("Completed test: testDeleteBook_Found");
    }

    @Test
    void testDeleteBook_NotFound() throws Exception {
    	logger.info("Starting test: testDeleteBook_NotFound");
        when(bookService.getBookById(1L)).thenReturn(null);

        mockMvc.perform(delete("/books/1"))
               .andExpect(status().isNotFound())
               .andExpect(content().string("Book with ID 1 not found."));

        logger.info("Completed test: testDeleteBook_NotFound");
    }
}
