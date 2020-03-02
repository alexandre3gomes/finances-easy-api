package net.finance.bo;

import lombok.NonNull;
import net.finance.entity.Savings;
import net.finance.repository.SavingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@Transactional
public class SavingsBo {

    @NonNull
    private final SavingsRepository savingsRepository;

    @Autowired
    public SavingsBo(SavingsRepository savingsRep) {
        savingsRepository = savingsRep;
    }

    public Savings create(Savings Savings) {
        return savingsRepository.save(Savings);
    }

    public void delete(Integer id) {
        savingsRepository.deleteById(id);
    }

    public Savings get(Integer id) {
        return savingsRepository.findById(id).get();
    }

    public Page<Savings> list(PageRequest pageReq) {
        return savingsRepository.findAll(pageReq);
    }

    public Savings update(Savings dev) {
        return savingsRepository.save(dev);
    }

    public BigDecimal getTotalSavings() {
        return savingsRepository.getSumSavings();
    }
}
