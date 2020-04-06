package net.finance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "budget_periods")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetPeriods implements Serializable, Comparable<BudgetPeriods> {

	/**
	 *
	 */
	private static final long serialVersionUID = -2950083882029060547L;
	@Id
	@ManyToOne(optional = false)
	@JoinColumn(name = "budget_id", referencedColumnName = "id")
	@JsonIgnore
	private Budget budget;
	@Id
	@Column(name = "idPeriod")
	private Integer idPeriod;
	@Column(name = "startDate", nullable = false)
	private LocalDateTime startDate;
	@Column(name = "endDate", nullable = false)
	private LocalDateTime endDate;

	@Override
	public int compareTo(final BudgetPeriods o) {
		return startDate.isAfter(o.startDate) ? 1 : 0;
	}

}
