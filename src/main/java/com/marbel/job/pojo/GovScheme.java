package com.marbel.job.pojo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "govschemes")
public class GovScheme {
  
	@Id
    private String id;
    private String schemeName;       // e.g., PM Kisan Yojana
    private String launchedBy;       // e.g., Ministry of Agriculture
    private String objective;        // main purpose of scheme
    private String eligibility;      // who can apply
    private String benefits;         // what user gets
    private String applyLink;        // official link
    private String seoKeywords;
    private String description;
}
