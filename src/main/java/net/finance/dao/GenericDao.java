package net.finance.dao;

import java.util.List;

public interface GenericDao<T> {

	T create(T t);

	void delete(Object id);

	T find(Object id);

	T update(T t);

	List<T> list();

}
