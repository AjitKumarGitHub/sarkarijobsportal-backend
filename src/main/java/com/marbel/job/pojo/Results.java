package com.marbel.job.pojo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "results")
public class Results {

	 @Id
	    private String id;
	    
	    @Indexed
	    private String title;  
	    
	    @Indexed
	    private String date;
	    
	    @Indexed
	    private String organization;  
	    
	    private String resultLink;       
	    private String resultDate;        
	    private String status;      
	    private String totalpost;
	    private String cutOffMatks;
	    
	    @Indexed
	    private String  category;
}
