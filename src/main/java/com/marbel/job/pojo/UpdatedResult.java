package com.marbel.job.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatedResult {

	private String date;       
    private String resultLink;        
    private String resultDate;           
    private String status;      
    private String cutOffMatks;
    private String category;
    
}
