package com.marbel.job.pojo;

import org.springframework.stereotype.Component;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "jobs")
@Component
public class JobDetails {

	@Id
    private String id;

    @Indexed
    private String title;

    @Indexed
    private String category;

    @Indexed
    private String state;

    @Indexed
    private String postDate;  // Sorting + filtering will be super fast

    @Indexed
    private String date;
    
    @Indexed
    private String slug;

    @Indexed
    private String organizationName;

    private String totalVacancy;
    private String moreDetailsLink;

    private List<PaperItem> prevYearPapers;
    private List<LinkItem> links;
    private List<String> modeOfSelections;
    private List<AgeLimitItem> ageLimits;
    private List<FeeItem> jobFeeStructure;
    private String refundPolicy;

    @Indexed
    private String shortForm;

    private String officialWebsite;
    private List<PostInfo> postDetails;

    private List<String> importantDates;

}
