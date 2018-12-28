package net.finance.entity;
// Generated Dec 28, 2018 2:10:35 PM by Hibernate Tools 5.3.6.Final

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Budget")
@Data
@NoArgsConstructor
public class Category implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -993799920346983880L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 3, scale = 0)
	private int id;
	@Column(name = "name")
	private String name;
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private Set<Expense> expenses = new HashSet<>(0);
	@ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
	private List<Budget> budgets;

}
