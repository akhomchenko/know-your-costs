package edu.costs.controllers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Transactional
public abstract class CRUDController<T> {
	@PersistenceContext
	private EntityManager em;
	
	protected abstract JpaRepository<T, Long> getRepository();


	@RequestMapping(method = RequestMethod.PUT)
	public T create(@RequestBody T object) {
		if (em.contains(object)) {
			throw new IllegalArgumentException("Object already exists!");
		}
		return getRepository().saveAndFlush(object);
	}

	@RequestMapping(method = RequestMethod.GET)
	public T read(long id) {
		return getRepository().findOne(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public T update(@RequestBody T object) {
		if (!em.contains(object)) {
			throw new IllegalArgumentException("Object doesn't exist!");
		}
		return getRepository().saveAndFlush(object);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(long id) {
		getRepository().delete(id);
	}
}
