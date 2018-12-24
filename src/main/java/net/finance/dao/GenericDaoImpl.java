package net.finance.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GenericDaoImpl<T> implements GenericDao<T> {

	@PersistenceContext
	protected EntityManager em;

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public GenericDaoImpl() {
		final Type t = getClass().getGenericSuperclass();
		final ParameterizedType pt = (ParameterizedType) t;
		type = (Class<T>) pt.getActualTypeArguments()[0];
	}

	@Override
	public T create(final T entity) {
		em.persist(entity);
		return entity;
	}

	@Override
	public void delete(final Object id) {
		em.remove(em.getReference(type, id));
	}

	@Override
	public T find(final Object id) {
		return em.find(type, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> list() {
		return em.createQuery("FROM " + type.getName()).getResultList();
	}

	@Override
	public T update(final T entity) {
		return em.merge(entity);
	}

}
