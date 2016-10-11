package edu.costs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.costs.domain.Group;
import edu.costs.repositories.GroupRepository;

@RestController
@RequestMapping("/group")
public class GroupsController extends CRUDController<Group>{
	private GroupRepository repo;

	@Autowired
	public GroupsController(GroupRepository repo) {
		this.repo = repo;
	}

	@Override
	protected JpaRepository<Group, Long> getRepository() {
		return repo;
	}
}
