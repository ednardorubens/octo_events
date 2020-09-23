package jaya.tech.octo_events.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import jaya.tech.octo_events.utils.EntityBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class EventControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Test
	@Order(1)
	void whenCallCreate1_thenReturnHttpCreated() throws Exception {
		final ResultActions result = this.mvc.perform(
			post(EventController.EVENTS)
			.content(EntityBuilder.VALID_JSON)
			.contentType(MediaType.APPLICATION_JSON)
		);
		EventControllerTest.checkContent(result, 1L);
	}

	@Test
	@Order(2)
	void whenCallCreate2_thenReturnHttpCreated() throws Exception {
		final ResultActions result = this.mvc.perform(
			post(EventController.EVENTS)
			.content(EntityBuilder.VALID_JSON)
			.contentType(MediaType.APPLICATION_JSON)
		);
		EventControllerTest.checkContent(result, 2L);
	}

	@Test
	@Order(3)
	void whenCallCreate3_thenReturnHttpCreated() throws Exception {
		String modifiedJson = EntityBuilder.VALID_JSON;
		modifiedJson = modifiedJson.replace("2019-05-15T15:20:18Z", "2020-01-15T15:20:18Z");
		final ResultActions result = this.mvc.perform(
			post(EventController.EVENTS)
			.content(modifiedJson)
			.contentType(MediaType.APPLICATION_JSON)
		).andExpect(jsonPath("$.issue.updated_at").value("2020-01-15T15:20:18Z"));
		EventControllerTest.checkContent(result, 3L);
	}

	@Test
	@Order(4)
	void whenCallRead3_thenReturnHttpOk() throws Exception {
		final Long id = 3L;
		final ResultActions result = this.mvc.perform(
			get(EventController.EVENTS + "/" + id)
			.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk());
		EventControllerTest.checkContent(result, id);
	}

	@Test
	@Order(5)
	void whenCallRead_thenReturnHttpOk() throws Exception {
		final int number = 1;
		this.mvc.perform(
			get("/issues/" + number  + "/events")
			.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.content[0].id").value(1))
        .andExpect(jsonPath("$.content[0].action").isNotEmpty())
		.andExpect(jsonPath("$.content[0].issue").isNotEmpty())
		.andExpect(jsonPath("$.content[0].issue.id").isNumber())
		.andExpect(jsonPath("$.content[0].issue.number").isNumber())
		.andExpect(jsonPath("$.content[0].issue.number").isNumber())
		.andExpect(jsonPath("$.content[0].issue.title").isNotEmpty())
		.andExpect(jsonPath("$.content[0].issue.body").isNotEmpty())
		.andExpect(jsonPath("$.content[0].issue.created_at").isNotEmpty())
		.andExpect(jsonPath("$.content[0].issue.events").doesNotExist())
		.andReturn();
	}
}
