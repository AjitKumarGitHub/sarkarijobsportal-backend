package com.marbel.job.pojo;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PotdQuestion {
    
	private String id;
	private String question_en;
	private String question_hi;
	private List<String> options_en;
	private List<String> options_hi;
	private String correctAnswer;
	private String explanation_en;
	private String explanation_hi;
}
