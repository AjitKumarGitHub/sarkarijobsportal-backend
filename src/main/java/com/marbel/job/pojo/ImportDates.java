package com.marbel.job.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImportDates {

	 private String startDate;
	 private String lastDate;
	 private String feePaymentDate;
	 private String SubmitFormDate;
	 private String examDate;
	 private String admitCardDate;
	 private String ResultDate;
	 private String checkDetails;
}
