
package net.finance.entity;
// Generated Dec 28, 2018 2:10:35 PM by Hibernate Tools 5.3.6.Final

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
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
	@Column(name = "savings")
	private Boolean savings;
	@JsonIgnore
	@OneToMany(mappedBy = "category")
	private Set<BudgetCategories> budgets;

}
