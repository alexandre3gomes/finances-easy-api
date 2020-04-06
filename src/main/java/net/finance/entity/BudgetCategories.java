package net.finance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "budget_categories")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class BudgetCategories implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7040527905669918246L;
	@Id
	@ManyToOne
	@JoinColumn(name = "budget_id", insertable = false, updatable = false)
	@JsonIgnore
	private Budget budget;
	@Id
	@ManyToOne
	@JoinColumn(name = "category_id", insertable = false, updatable = false)
	private Category category;
	@NonNull
	@Column(name = "value")
	private BigDecimal value;

	public BudgetCategories(Category category, BigDecimal value) {
		this.category = category;
		this.value = value;
	}

}
