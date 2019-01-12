package net.finance.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.finance.CategoryValueDTO;

@Entity
@Table(name = "Budget")
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Budget implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6811337145996225638L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 3, scale = 0)
	private Integer id;
	@ManyToOne(optional = false)
	@JoinColumn(name = "app_user", referencedColumnName = "id")
	private User user;
	@Column(name = "startDate", nullable = false)
	private Date startDate;
	@Column(name = "endDate", nullable = false)
	private Date endDate;
	@Transient
	private List<CategoryValueDTO> categories = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<BudgetCategories> budgetCategories = new HashSet<>();

	public void addCategory(final Category cat, final BigDecimal value) {
		final BudgetCategories budCat = new BudgetCategories(this, cat, value);
		budgetCategories.add(budCat);
	}

	public void removeCategory(final Category cat) {
		final Iterator<BudgetCategories> it = budgetCategories.iterator();
		while (it.hasNext()) {
			final BudgetCategories budCat = it.next();
			if (budCat.getBudget().equals(this) && budCat.getCategory().equals(cat)) {
				it.remove();
				budCat.getCategory().getBudgetCategories().remove(budCat);
				budCat.setBudget(null);
				budCat.setCategory(null);
			}
		}
	}

}
