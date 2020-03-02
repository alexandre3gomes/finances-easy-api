package net.finance.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "savings")
public class Savings implements Serializable {


    private static final long serialVersionUID = -6332118260679053853L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, precision = 3, scale = 0)
    private Integer id;
    @Column(name = "description", nullable = false, length = 40)
    private String description;
    @Column(name = "value", nullable = false)
    private BigDecimal value;
    @ManyToOne(optional = false)
    @JoinColumn(name = "app_user", referencedColumnName = "id")
    private User user;
    @Column(name = "created_date", nullable = false)
    private Date createdDate;
}
