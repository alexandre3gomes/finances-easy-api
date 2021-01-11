package com.finances.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "app_users")
data class User(
        val name: String = "",
        private var username: String = "",
        private var password: String = ""
) : UserDetails {

    constructor(id: Int, name: String, username: String, password: String): this(name, username, password)  {
        this.id = id
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Transient
    var token: String? = null

    @OneToMany(mappedBy = "user")
    @Transient
    private val budgets: List<Budget> = mutableListOf()

    @OneToMany(mappedBy = "user")
    @Transient
    private val expenses: List<Expense> = mutableListOf()

    @OneToMany(mappedBy = "user")
    @Transient
    private val incomes: List<Income> = mutableListOf()

    @OneToMany(mappedBy = "user")
    @Transient
    private val savings: List<Savings> = mutableListOf()

    override fun getAuthorities() = listOf<GrantedAuthority>()

    override fun getPassword() = password

    override fun getUsername() = username

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true

}