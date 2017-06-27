package com.example;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;

@ApplicationScoped
@Path("/")
public class MessageResource {
	@Inject
	MessageDao messageDao;

	@GET
	@Produces(APPLICATION_JSON)
	public List<Message> getMessages() {
		return messageDao.findAll();
	}

	@POST
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_FORM_URLENCODED)
	public Message postMessages(@FormParam("text") String text) {
		return messageDao.insert(new Message(text));
	}
}
