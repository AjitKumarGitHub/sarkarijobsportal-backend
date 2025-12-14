package com.marbel.job.pojo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "admitcards")
public class AdmitCards {

	 @Id
	    private String id;
	 
	    @Indexed
	    private String title;
	    
	    @Indexed
	    private String organization;  
	    
	    private String posts;
	    private String examDate; 
	    private String downloadLink; 
	    
	    @Indexed
	    private String date;
	    
	    @Indexed
	    private String status;
	    
	    @Indexed
	    private String category;
	    
	    private String instructions;
	   
}
