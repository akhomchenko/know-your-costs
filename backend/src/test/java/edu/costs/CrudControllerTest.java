package edu.costs;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import edu.costs.domain.User;
import edu.costs.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class CrudControllerTest extends AbstractControllerTest {
	private static final String USERNAME = "Definitely existing user";
	private User temporary;
	@Autowired private UserRepository userRepo;
	
	@Before
	public void prepare() {
		temporary = new User();
		temporary.setName(USERNAME);
		temporary = userRepo.saveAndFlush(temporary);
	}
	
	@After
	public void cleanup() {
		if (userRepo.exists(temporary.getId())) {
			userRepo.delete(temporary.getId());
			userRepo.flush();
		}
	}
	
	@Test
	public void testCreate() throws Exception {
		User user = new User();
		user.setName("AAA");
		
		String json = mapper.writeValueAsString(user);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(json))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("AAA")))
			.andReturn();
		
		String resultContent = result.getResponse().getContentAsString();
		User resultUser = mapper.readValue(resultContent, User.class);
		Assert.assertNotNull(resultUser.getId());
		
		User fromDB = userRepo.findOne(resultUser.getId());
		Assert.assertNotNull(fromDB);
		Assert.assertEquals(user.getName(), fromDB.getName());
	}
	
	@Test
	public void testRead() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/" + temporary.getId()))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(temporary.getId().intValue())))
			.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(USERNAME)));
	}
	
	@Test
	public void testUpdate() throws Exception {
		String newName = "ChangedName";
		temporary.setName(newName);
		String json = mapper.writeValueAsString(temporary);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/users").contentType(MediaType.APPLICATION_JSON).content(json))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(temporary.getId().intValue())))
			.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(newName)));
	}
	
	@Test
	public void testCRUD() throws Exception {		
		mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + temporary.getId()))
			.andExpect(MockMvcResultMatchers.status().isOk());
		
		Assert.assertFalse(userRepo.exists(temporary.getId()));
	}
}
