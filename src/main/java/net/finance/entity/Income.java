package net.finance.entity;
// Generated Dec 28, 2018 2:10:35 PM by Hibernate Tools 5.3.6.Final

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "income")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Income implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5212669863497273741L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 3, scale = 0)
	private Integer id;
	@ManyToOne(optional = false)
	@JoinColumn(name = "app_user", referencedColumnName = "id")
	private User user;
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	@Column(name = "value", nullable = false)
	private BigDecimal value;
	@Column(name = "date", nullable = false)
	private LocalDateTime date;
	@Column(name = "description")
	private String description;
}
