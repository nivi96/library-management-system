package com.example.libraryManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.libraryManagement.Entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
