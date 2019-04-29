package net.finance.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.finance.entity.Category;

@Data
@AllArgsConstructor
public class CategoryGroupedValuesDTO {

	private Category category;
	private Date startDate;
	private Date endDate;
	private BigDecimal value;

}
