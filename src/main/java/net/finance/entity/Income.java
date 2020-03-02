package net.finance.entity;
// Generated Dec 28, 2018 2:10:35 PM by Hibernate Tools 5.3.6.Final

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
	private Date date;
	@Column(name = "description")
	private String description;
}
