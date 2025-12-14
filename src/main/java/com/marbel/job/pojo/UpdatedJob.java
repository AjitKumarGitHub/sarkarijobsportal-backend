package com.marbel.job.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatedJob {
 
	private String vacancy;
	private String moreDetailsLink;
	private List<LinkItem> links;
	private List<String> importantDates;
	private String postDate;
	private String date;
	
	
}
