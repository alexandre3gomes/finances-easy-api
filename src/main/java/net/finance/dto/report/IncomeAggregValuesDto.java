package net.finance.dto.report;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.finance.entity.Income;

@Data
@AllArgsConstructor
public class IncomeAggregValuesDto {

	private List<Income> income;
	private Date startDate;
	private Date endDate;

}
