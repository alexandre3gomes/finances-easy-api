package net.finance.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpenseFilterDTO {

	Integer categoryId;
	Date startDate;
	Date endDate;
	String name;

}
