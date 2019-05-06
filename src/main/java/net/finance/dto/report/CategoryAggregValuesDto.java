package net.finance.dto.report;

import java.util.List;

import lombok.Data;
import net.finance.entity.Category;

@Data
public class CategoryAggregValuesDto {

	private Category category;
	private List<PeriodValueDto> periodValue;

}
