package com.finances.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "expense")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Builder
public class Expense implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -9181522684621652659L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 3, scale = 0)
	private Integer id;
	@ManyToOne(optional = false)
	@JoinColumn(name = "category", referencedColumnName = "id")
	private Category category;
	@ManyToOne(optional = false)
	@JoinColumn(name = "app_user", referencedColumnName = "id")
	private User user;
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	@Column(name = "value", nullable = false)
	private BigDecimal value;
	@Column(name = "expire_at", nullable = false)
	private LocalDateTime expireAt;
	@Column(name = "description")
	private String description;
}
