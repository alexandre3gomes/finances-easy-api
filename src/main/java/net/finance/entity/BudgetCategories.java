package net.finance.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "budget_categories")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class BudgetCategories implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7040527905669918246L;
	@EqualsAndHashCode.Include
	@EmbeddedId
	private BudgetCategoryId pk = new BudgetCategoryId();
	@NonNull
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("budget")
	private Budget budget;
	@NonNull
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("category")
	private Category category;
	@NonNull
	@Column(name = "value")
	private BigDecimal value;

}
