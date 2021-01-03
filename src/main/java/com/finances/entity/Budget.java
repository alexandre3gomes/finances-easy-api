package com.finances.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "Budget")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
	@Column(name = "start_date", nullable = false)
	private LocalDateTime startDate;
	@Column(name = "end_date", nullable = false)
	private LocalDateTime endDate;
	@Builder.Default
	@OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
	private Set<BudgetCategories> categories = new HashSet<>();
	@Builder.Default
	@OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
	@OrderBy("idPeriod")
	private Set<BudgetPeriods> periods = new HashSet<>();
	@Column(name = "breakperiod")
	private Integer breakPeriod;

	public Budget(User user, LocalDateTime startDate, LocalDateTime endDate, Integer breakpoint, Set<BudgetPeriods> periods,
			BudgetCategories... categories) {
		this.user = user;
		this.startDate = startDate;
		this.endDate = endDate;
		breakPeriod = breakpoint;
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
