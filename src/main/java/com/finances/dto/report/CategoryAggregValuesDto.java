package com.finances.dto.report;

import java.util.List;

import com.finances.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAggregValuesDto {

	private Category category;
	private List<PeriodValueDto> periodValue;

}
