package com.marbel.job.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatedAdmitCard {
 
	    private String examDate; 
	    private String downloadLink;     
	    private String date;
	    private String status;
	    private String instructions;
	    private String category;
}
