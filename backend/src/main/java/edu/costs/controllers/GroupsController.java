package edu.costs.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.costs.domain.Group;
import edu.costs.domain.User;
import edu.costs.repositories.GroupRepository;
import edu.costs.repositories.UserRepository;

@RestController
@RequestMapping("/groups")
public class GroupsController extends CRUDController<Group>{
	private GroupRepository groupRepo;
	private UserRepository userRepo;

	@Autowired
	public GroupsController(GroupRepository groupRepo, UserRepository userRepo) {
		this.groupRepo = groupRepo;
		this.userRepo = userRepo;
	}

	@Override
	protected JpaRepository<Group, Long> getRepository() {
		return groupRepo;
	}
	
	@RequestMapping(path = "/{groupId}/user/{userId}", method = RequestMethod.PUT)
	@Transactional
	public void addUser(@PathVariable long groupId,@PathVariable long userId) {
		Group group = groupRepo.getOne(groupId);
		User user = userRepo.getOne(userId);
		if (user == null || group == null) {
			throw new IllegalArgumentException();
		}
		group.getUsers().add(user);
	}
}
