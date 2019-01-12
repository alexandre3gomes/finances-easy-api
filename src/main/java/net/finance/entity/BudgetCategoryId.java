package net.finance.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class BudgetCategoryId implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6169666634025586628L;
	@Column(name = "budget")
	private Integer budget;
	@Column(name = "category")
	private Integer category;

}
