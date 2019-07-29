package net.finance.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.finance.bo.ReportBo;
import net.finance.dto.report.CategoryAggregValuesDto;

@RestController
@RequestMapping("/report")
public class ReportService {

	@Autowired
	private ReportBo reportBo;

	@GetMapping("/byCategory/{budgetId}")
	public ResponseEntity<List<CategoryAggregValuesDto>> byCategory(@PathVariable("budgetId") final Integer budgetId) {
		return new ResponseEntity<>(reportBo.byCategory(budgetId), HttpStatus.OK);
	}

	@GetMapping("/byPeriod/{budgetId}")
	public ResponseEntity<List<BigDecimal>> incomeByPeriod(@PathVariable("budgetId") final Integer budgetId) {
		return new ResponseEntity<>(reportBo.incomeByPeriod(budgetId), HttpStatus.OK);
	}

}
