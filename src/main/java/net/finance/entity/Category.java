
package net.finance.entity;
// Generated Dec 28, 2018 2:10:35 PM by Hibernate Tools 5.3.6.Final

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Category implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -993799920346983880L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 3, scale = 0)
	@NonNull
	private Integer id;
	@Column(name = "name")
	private String name;
	@OneToMany(mappedBy = "category")
	@JsonIgnore
	private Set<BudgetCategories> budgets;

}
