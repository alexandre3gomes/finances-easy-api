package net.finance.dto.report;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeriodValueDto {

	private Date startDate;
	private Date endDate;
	private BigDecimal plannedValue;
	private BigDecimal actualValue;

}
