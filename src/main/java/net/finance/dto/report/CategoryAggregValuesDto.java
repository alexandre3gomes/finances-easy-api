package net.finance.dto.report;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.finance.entity.Category;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAggregValuesDto {

	private Category category;
	private List<PeriodValueDto> periodValue;

}
