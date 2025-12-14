package com.marbel.job.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgeLimitItem {

	private String label;     // e.g. "Minimum Age"
    private String value;     // e.g. "18 Years"
    private String category;  // e.g. "UR Male", "BC/EBC", "SC/ST" (optional)

}
