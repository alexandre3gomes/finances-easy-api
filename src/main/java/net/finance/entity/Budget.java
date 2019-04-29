package net.finance.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Budget")
@Getter
@Setter
@NoArgsConstructor
public class Budget extends BaseAuditEntity implements Serializable {

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
	@Column(name = "start_date", nullable = false)
	private Date startDate;
	@Column(name = "end_date", nullable = false)
	private Date endDate;
	@OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
	private Set<BudgetCategories> categories = new HashSet<>();
	@OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
	private Set<BudgetPeriods> periods = new HashSet<>();
	@Column(name = "breakpoint")
	private Integer breakpoint;

	public Budget(User user, Date startDate, Date endDate, Integer breakpoint, Set<BudgetPeriods> periods,
			BudgetCategories... categories) {
		this.user = user;
		this.startDate = startDate;
		this.endDate = endDate;
		this.breakpoint = breakpoint;
		for (final BudgetCategories cat : categories) {
			cat.setBudget(this);
		}
		for (final BudgetPeriods per : periods) {
			per.setBudget(this);
		}
		this.periods = periods;
		this.categories = Stream.of(categories).collect(Collectors.toSet());
	}
}
