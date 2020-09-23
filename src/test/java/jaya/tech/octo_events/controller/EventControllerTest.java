package jaya.tech.octo_events.controller;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import jaya.tech.octo_events.model.Event;
import jaya.tech.octo_events.repository.EventRepository;
import jaya.tech.octo_events.utils.EntityBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

	private final Long id = 12345L;

	@MockBean
	private EventRepository eventRepository;

	@Autowired
	private MockMvc mvc;

	@Test
	void whenCallCreate_thenReturnHttpCreated() throws Exception {
		final Event event = EntityBuilder.getEvent(id);
		when(this.eventRepository.save(any(Event.class))).thenReturn(event);
		final ResultActions result = this.mvc.perform(
			post("/events")
			.content(EntityBuilder.VALID_JSON)
			.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isCreated())
		.andExpect(header().string("Location", endsWith("/events/" + id)));
		EventControllerTest.checkContent(result, id);
	}

	@Test
	void whenCallCreateInvalidEvent_thenReturnHttpBadRequest() throws Exception {
		final Event event = EntityBuilder.getEvent(id);
		when(this.eventRepository.save(any(Event.class))).thenReturn(event);
		this.mvc.perform(
			post("/events")
			.content("{\"issue\":{\"url\":\"https://api.github.com/repos/Codertocat/Hello-World/issues/1\",\"repository_url\":\"https://api.github.com/repos/Codertocat/Hello-World\",\"labels_url\":\"https://api.github.com/repos/Codertocat/Hello-World/issues/1/labels{/name}\",\"comments_url\":\"https://api.github.com/repos/Codertocat/Hello-World/issues/1/comments\",\"events_url\":\"https://api.github.com/repos/Codertocat/Hello-World/issues/1/events\",\"html_url\":\"https://github.com/Codertocat/Hello-World/issues/1\",\"id\":444500041,\"node_id\":\"MDU6SXNzdWU0NDQ1MDAwNDE=\",\"number\":1,\"title\":\"Spelling error in the README file\"}}")
			.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isBadRequest())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.errors").isNotEmpty())
		.andExpect(jsonPath("$.errors[0].field").value("action"))
		.andExpect(jsonPath("$.errors[0].error").value("must not be null"))
		.andReturn();
	}

	public static void checkContent(final ResultActions result, final Long id) throws Exception {
		result
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.action").isNotEmpty())
		.andExpect(jsonPath("$.issue").isNotEmpty())
		.andExpect(jsonPath("$.issue.id").isNumber())
		.andExpect(jsonPath("$.issue.number").isNumber())
		.andExpect(jsonPath("$.issue.number").isNumber())
		.andExpect(jsonPath("$.issue.title").isNotEmpty())
		.andExpect(jsonPath("$.issue.body").isNotEmpty())
		.andExpect(jsonPath("$.issue.created_at").isNotEmpty())
		.andExpect(jsonPath("$.issue.events").doesNotExist())
		.andReturn();
	}

}
