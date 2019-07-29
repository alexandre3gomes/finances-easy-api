package net.finance.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	@Column(name = "endDate", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@Override
	public int compareTo(final BudgetPeriods o) {
		return startDate.after(o.startDate) ? 1 : 0;
	}

}
