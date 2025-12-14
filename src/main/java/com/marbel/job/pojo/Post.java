package com.marbel.job.pojo;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import java.util.List;

@Data
@Document(collection = "posts")
public class Post {
	@Id
	private String id;

	@Indexed(unique = true)
	private String slug;
	
	 @Indexed
	 private String language;
	 
	 @Indexed
	 private String groupId;

	private String title;
	private String content;
	private String excerpt; // small part
	private SectionListItem sectionList;
	private SectionItem section1;
	private SectionItem section2;
	private SectionItem section3;
	private String imageUrl;
	private String category;
	private String writerName;
	private List<String> linkUrls;

	private Instant createdAt = Instant.now();
	private Instant updatedAt = Instant.now();
}
