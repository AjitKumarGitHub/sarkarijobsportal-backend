package com.marbel.job.pojo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "potd")

public class Potd {

	@Id
	private String id;
	private String date; // e.g., "2025-11-10"
	private List<PotdQuestion> potdList; // List of 20 GK/GS Questions
}
