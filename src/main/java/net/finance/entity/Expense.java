package net.finance.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Budget")
@Data
@NoArgsConstructor
public class Expense implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -9181522684621652659L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 3, scale = 0)
	private int id;
	@ManyToOne(optional = false)
	@JoinColumn(name = "category", referencedColumnName = "id")
	private Category category;
	@ManyToOne(optional = false)
	@JoinColumn(name = "user", referencedColumnName = "id")
	private User user;
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	@Column(name = "value", nullable = false)
	private BigDecimal value;
	@Column(name = "expire_at", nullable = false)
	private Date expireAt;
}
