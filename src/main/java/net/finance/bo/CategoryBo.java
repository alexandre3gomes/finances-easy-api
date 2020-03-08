package net.finance.bo;

import lombok.NonNull;
import net.finance.entity.Category;
import net.finance.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CategoryBo {

    @NonNull
    private final CategoryRepository categoryRep;

    @Autowired
    public CategoryBo(CategoryRepository categoryRep) {
        this.categoryRep = categoryRep;
    }

    public Category create(Category budget) {
        return categoryRep.save(budget);
    }

    public void delete(Integer id) {
        categoryRep.deleteById(id);
    }

    public Category get(Integer id) {
        return categoryRep.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Page<Category> list(PageRequest pageReq) {
        return categoryRep.findAll(pageReq);
    }

    public Category update(Category cat) {
        return categoryRep.save(cat);
    }

}
