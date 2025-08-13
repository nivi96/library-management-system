package com.example.libraryManagement.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

	@Entity
	@Data
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
	@Table(name = "book")
	public class Book {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		
		@NotBlank(message = "Title is required")
	    @Column(nullable = false)
		 private String title;

		@NotBlank(message = "Author is required")
	    private String author;


		@NotNull(message = "Copies is required")
	    @Min(value = 1, message = "Copies must be at least 1")
	    private Integer copies;

	
	    public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getTitle() {
			return title;
		}


		public void setTitle(String title) {
			this.title = title;
		}


		public String getAuthor() {
			return author;
		}


		public void setAuthor(String author) {
			this.author = author;
		}


		public Integer getCopies() {
			return copies;
		}


		public void setCopies(Integer copies) {
			this.copies = copies;
		}


		

		
	}
