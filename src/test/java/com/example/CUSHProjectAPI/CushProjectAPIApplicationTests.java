package com.example.CUSHProjectAPI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CushProjectAPIApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void _테스트() throws Exception {

		mvc.perform(get("/api/admin/adminBoardChart")
				.param("monthOption","12")
				.param("yearOption","2021")
		)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.sell").isArray())
				.andExpect(jsonPath("$.old").isArray())
				.andExpect(jsonPath("$.soldOut").isArray())
				.andDo(print());
	}

}
