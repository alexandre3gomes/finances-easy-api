package net.finance.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "budget_periods")
@Data
public class BudgetPeriods implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2950083882029060547L;
	@Id
	@ManyToOne(optional = false)
	@JoinColumn(name = "budget_id", referencedColumnName = "id")
	private Budget budget;
	@Id
	@Column(name = "idPeriod")
	private Integer idPeriod;
	@Column(name = "startDate", nullable = false)
	private Date startDate;
	@Column(name = "endDate", nullable = false)
	private Date endDate;

}
