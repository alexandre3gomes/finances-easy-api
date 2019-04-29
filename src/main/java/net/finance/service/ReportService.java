package net.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.finance.bo.ReportBo;
import net.finance.dto.CategoryGroupedValuesDTO;

@RestController
@RequestMapping("/report")
public class ReportService {

	@Autowired
	private ReportBo reportBo;

	@PostMapping("/byCategory/{budgetId}")
	public ResponseEntity<List<CategoryGroupedValuesDTO>> byCategory(@PathVariable("budgetId") Integer budgetId) {
		return new ResponseEntity<>(reportBo.byCategory(budgetId), HttpStatus.OK);
	}

}
