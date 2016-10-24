package edu.costs.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.costs.domain.Identifiable;

@Transactional
public abstract class CRUDController<T extends Identifiable> {
	@PersistenceContext
	protected EntityManager em;
	
	protected abstract JpaRepository<T, Long> getRepository();

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<T> create(@RequestBody @Valid T object) {
		Long id = object.getId();
		if (id != null && getRepository().findOne(id) != null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(getRepository().saveAndFlush(object), HttpStatus.CREATED);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<T> read(@PathVariable long id) {
		T found = getRepository().findOne(id);
		if (found == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(found, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<T> readAll() {
		return getRepository().findAll();
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<T> update(@RequestBody @Valid T object) {
		Long id = object.getId();
		if (object.getId() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (getRepository().findOne(id) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<T>(getRepository().saveAndFlush(object), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<T> delete(@PathVariable long id) {
		if (getRepository().findOne(id) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		getRepository().delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
