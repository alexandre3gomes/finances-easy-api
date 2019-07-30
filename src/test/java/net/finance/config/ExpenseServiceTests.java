package net.finance.config;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import net.finance.entity.Category;
import net.finance.entity.Expense;
import net.finance.entity.User;
import net.finance.repository.ExpenseRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ExpenseServiceTests {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ExpenseRepository expenseRepository;

	@Test
	public void testListExpenses() {
		final Expense exp = new Expense();
		exp.setName("Teste");
		exp.setCategory(new Category(1));
		exp.setExpireAt(new Date());
		exp.setValue(new BigDecimal(50));
		final User user = new User();
		user.setId(1);
		user.setUsername("admin");
		exp.setUser(user);
		entityManager.persist(exp);
		final List<Expense> exps = expenseRepository.findAll();
		for (final Expense ex : exps) {
			System.out.println(ex.getName());
		}
	}

}
