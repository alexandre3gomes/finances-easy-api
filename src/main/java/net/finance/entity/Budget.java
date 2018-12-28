package net.finance.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Budget")
@Data
@NoArgsConstructor
public class Budget implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6811337145996225638L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 3, scale = 0)
	private int id;
	@ManyToOne(optional = false)
	@JoinColumn(name = "user", referencedColumnName = "id")
	private User user;
	@Column(name = "startDate", nullable = false)
	private Date startDate;
	@Column(name = "endDate", nullable = false)
	private Date endDate;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "budget_categories", joinColumns = {
			@JoinColumn(name = "budget", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "category", referencedColumnName = "id") })
	private List<Category> categories;

}
