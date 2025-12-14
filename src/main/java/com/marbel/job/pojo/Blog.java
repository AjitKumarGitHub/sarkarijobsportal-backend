package com.marbel.job.pojo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "blogs")
public class Blog {
   
	@Id
    private String id;
    private String title;          // Blog post title
    private String author;         // e.g., Admin or Staff Writer
    private String content;        // Full HTML or markdown text
    private String category;       // e.g., "Exam Preparation", "Updates"
    private String seoKeywords;    // SEO meta keywords
    private List<String> ImageUrl;   // Optional image
    private LocalDate publishedOn;
	 
}
