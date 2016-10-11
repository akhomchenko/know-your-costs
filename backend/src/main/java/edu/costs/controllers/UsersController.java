package edu.costs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.costs.domain.User;
import edu.costs.repositories.UserRepository;

@RestController
@RequestMapping("/user")
public class UsersController extends CRUDController<User>{
	private UserRepository repo;

	@Autowired
	public UsersController(UserRepository repo) {
		this.repo = repo;
	}

	@Override
	protected JpaRepository<User, Long> getRepository() {
		return repo;
	}
}
