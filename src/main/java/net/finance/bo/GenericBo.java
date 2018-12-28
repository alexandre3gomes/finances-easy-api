package net.finance.bo;

import java.util.List;

public interface GenericBo<T> {

	T create(final T entity);

	void delete(final Integer id);

	T findById(final Integer id);

	List<T> listAll();

	T update(final T entity);

}
