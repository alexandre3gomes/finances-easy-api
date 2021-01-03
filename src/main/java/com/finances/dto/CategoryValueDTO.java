package com.finances.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.finances.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryValueDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1108594438788546509L;
	private Category category;
	private BigDecimal value;

}
