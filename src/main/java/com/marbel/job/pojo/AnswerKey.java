package com.marbel.job.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "answerkeys")
public class AnswerKey {

	 @Id
	 private String id;
	 private String title;
	 private String organization;
	 private String date;
	 private String answeKeyDate;
	 private String answerKeyLink;
	 private String status;
	 private String cutOffMatks;
	 private String category;
	
}
