package edu.costs;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import edu.costs.domain.Group;
import edu.costs.domain.User;
import edu.costs.repositories.GroupRepository;
import edu.costs.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class GroupsControllerTest extends AbstractControllerTest{
	@Autowired private GroupRepository groupRepo;
	@Autowired private UserRepository userRepo;
	
	@Test
	public void AddUserTest() throws Exception {
		Group g = groupRepo.save(new Group());
		List<User> users = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			User u = new User();
			u.setName("User" + i);
			users.add(userRepo.save(u));
		}
		
		
		for (User u: users) {
			mockMvc.perform(MockMvcRequestBuilders.put("/groups/" + g.getId() + "/user/" + u.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		}
				
		assertEquals(3, g.getUsers().size());
	}
}
