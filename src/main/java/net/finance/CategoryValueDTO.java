package net.finance;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.finance.entity.Category;

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
