package edu.costs.main;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.costs.data.User;

@RestController
@Transactional
public class HelloWorld {

	@PersistenceContext
	private EntityManager mgr;

	@RequestMapping("/")
	public User greeting() {
		User user = new User();
		user.setName("New user");
		mgr.persist(user);
		return user;
	}
}
