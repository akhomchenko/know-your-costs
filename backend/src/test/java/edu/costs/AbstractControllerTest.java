package edu.costs;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(classes = Launcher.class)
@WebAppConfiguration
@Transactional
public class AbstractControllerTest {
	protected MockMvc mockMvc;

	@Autowired
	public void setMockMVC(WebApplicationContext ctx) {
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Autowired protected ObjectMapper mapper;
}
